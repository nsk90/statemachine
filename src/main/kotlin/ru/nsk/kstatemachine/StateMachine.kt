package ru.nsk.kstatemachine

import ru.nsk.kstatemachine.StateMachine.IgnoredEventHandler
import ru.nsk.kstatemachine.StateMachine.PendingEventHandler
import java.util.concurrent.CopyOnWriteArraySet

@DslMarker
annotation class StateMachineDslMarker

@StateMachineDslMarker
class StateMachine(val name: String?) {
    private val states = mutableSetOf<State>()
    private lateinit var currentState: State
    private val listeners = CopyOnWriteArraySet<Listener>()
    var logger = Logger {}
    var ignoredEventHandler = IgnoredEventHandler { _, _, _ -> }
    var pendingEventHandler = PendingEventHandler { pendingEvent, _ ->
        error(
            "$this can not process pending $pendingEvent as event processing is already running. " +
                    "Do not call processEvent() from notification listeners."
        )
    }

    /** Help to check that [processEvent] is not called from state machine notification method*/
    private var isProcessingEvent = false

    fun <S : State> addState(state: S, init: (State.() -> Unit)? = null): S {
        if (init != null) state.init()
        states += state
        return state
    }

    fun <L : Listener> addListener(listener: L): L {
        require(listeners.add(listener)) { "$listener is aready added" }
        return listener
    }

    fun removeListener(listener: Listener) {
        listeners.remove(listener)
    }

    /**
     * Now initial state is mandatory, but if we add parallel states it will not be mandatory
     */
    fun setInitialState(state: State) {
        require(states.contains(state)) { "$state is not part of $this machine, use addState() first" }
        currentState = state
    }

    fun processEvent(event: Event, argument: Any? = null) {
        if (isProcessingEvent)
            pendingEventHandler.onPendingEvent(event, argument)
        isProcessingEvent = true
        try {
            val fromState = currentState
            val transition = fromState.findTransition(event)

            if (transition != null) {
                val transitionParams = TransitionParams(transition, event, argument)

                val direction = transition.produceTargetStateDirection()
                val targetState = if (direction is TargetState) direction.targetState else null

                if (direction !is NoTransition) {
                    log("$this triggering $transition from $fromState")
                    transition.notify { onTriggered(transitionParams) }

                    notify { onTransition(transition.sourceState, targetState, event, argument) }
                }

                targetState?.let { _ ->
                    log("$this exiting $fromState")
                    fromState.notify { onExit(transitionParams) }

                    setCurrentState(targetState, transitionParams)
                }
            } else {
                log("$this ignored $event as transition from $fromState, was not found")
                ignoredEventHandler.onIgnoredEvent(fromState, event, argument)
            }
        } finally {
            isProcessingEvent = false
        }
    }

    private fun log(message: String) = logger.log(message)

    private fun setCurrentState(state: State, transitionParams: TransitionParams<*>) {
        currentState = state
        log("$this entering $state")
        state.notify { onEntry(transitionParams) }
    }

    internal fun start() = setCurrentState(
        currentState,
        TransitionParams(Transition(StartEvent.javaClass, currentState, currentState, "Starting"), StartEvent)
    )

    override fun toString() = "${javaClass.simpleName}(name=$name)"

    private fun <E : Event> State.findTransition(event: E): Transition<E>? {
        val triggeringTransitions = transitions.filter { it.isTriggeringEvent(event) }
        check(triggeringTransitions.size <= 1) { "Multiple transitions match $event $triggeringTransitions in $this" }
        return triggeringTransitions.firstOrNull() as Transition<E>?
    }

    private fun notify(block: Listener.() -> Unit) = listeners.forEach { it.apply(block) }

    interface Listener {
        /**
         * This method is called when transition is performed.
         * There might be may transitions from one state to another,
         * this method might be used to listen to all transitions in one place
         * instead of listening for each transition separately.
         */
        fun onTransition(sourceState: State, targetState: State?, event: Event, argument: Any?) {}
    }

    /**
     * State machine uses this interface to support internal logging on different platforms
     */
    fun interface Logger {
        fun log(message: String)
    }

    fun interface IgnoredEventHandler {
        fun onIgnoredEvent(currentState: State, event: Event, argument: Any?)
    }

    fun interface PendingEventHandler {
        fun onPendingEvent(pendingEvent: Event, argument: Any?)
    }

    /**
     * Initial event which is processed on state machine start
     */
    object StartEvent : Event
}

fun StateMachine.onTransition(block: (sourceState: State, targetState: State?, event: Event, argument: Any?) -> Unit) {
    addListener(object : StateMachine.Listener {
        override fun onTransition(sourceState: State, targetState: State?, event: Event, argument: Any?) =
            block(sourceState, targetState, event, argument)
    })
}

fun StateMachine.state(name: String? = null, init: (State.() -> Unit)? = null) = addState(State(name), init)

/**
 * Factory method for creating [StateMachine]
 * @param logger state machine will use this function to log its internal state. It may be used in debugging purpose.
 */
fun createStateMachine(
    name: String? = null,
    init: StateMachine.() -> Unit
) = StateMachine(name).apply {
    init()
    start()
}

@StateMachineDslMarker
data class TransitionParams<E : Event>(
    val transition: Transition<E>,
    val event: E,
    /**
     * This parameter may be used to pass arbitrary data with the event,
     * so there is no need to define [Event] subclasses every time.
     * Subclassing should be preferred if the event always contains data of some type.
     */
    val argument: Any? = null,
)