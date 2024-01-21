package ru.nsk.kstatemachine.visitors

import ru.nsk.kstatemachine.*
import ru.nsk.kstatemachine.TransitionDirectionProducerPolicy.CollectTargetStatesPolicy

/**
 * Export state machine to Plant UML language format.
 * @see <a href="https://plantuml.com/ru/state-diagram">Plant UML state diagram</a>
 *
 * Conditional transitions currently are not supported.
 */
internal class ExportPlantUmlVisitor(private val showEventLabels: Boolean) : CoVisitor {
    private val builder = StringBuilder()
    private var indent = 0
    private val crossLevelTransitions = mutableListOf<String>()

    fun export() = builder.toString()

    override suspend fun visit(machine: StateMachine) {
        line("@startuml")
        line("hide empty description")

        processStateBody(machine)
        crossLevelTransitions.forEach { line(it) }

        line("@enduml")
    }

    override suspend fun visit(state: IState) {
        if (state.states.isEmpty()) {
            when (state) {
                is HistoryState, is UndoState -> return
                is RedirectPseudoState -> line("state ${state.graphName()} $CHOICE")
                else -> line("state ${state.graphName()}")
            }
        } else {
            if (state !is StateMachine) { // ignore composed machines
                line("state ${state.graphName()} {")
                ++indent
                processStateBody(state)
                --indent
                line("}")
            } else {
                line("state ${state.graphName()}")
            }
        }
    }

    /**
     * PlantUML cannot show correctly cross-level transitions to nested states.
     * It requires to see all states declarations first to provide correct rendering,
     * so we have to store them to print after state declaration.
     */
    override suspend fun <E : Event> visit(transition: Transition<E>) {
        transition as InternalTransition<E>

        val sourceState = transition.sourceState.graphName()

        @Suppress("UNCHECKED_CAST")
        val targetStates = transition.produceTargetStateDirection(CollectTargetStatesPolicy()).targetStates
                as Set<InternalState>
        val targetState = targetStates.firstOrNull() ?: return // fixme iterate over all

        val graphName = if (targetState is HistoryState) {
            val prefix = targetState.requireInternalParent().graphName()
            when (targetState.historyType) {
                HistoryType.SHALLOW -> "$prefix$SHALLOW_HISTORY"
                HistoryType.DEEP -> "$prefix$DEEP_HISTORY"
            }
        } else {
            targetState.graphName()
        }

        val transitionString = "$sourceState --> $graphName${transitionLabel(transition)}"

        if (transition.sourceState.isNeighbor(targetState))
            line(transitionString)
        else
            crossLevelTransitions.add(transitionString)
    }

    private suspend fun processStateBody(state: IState) {
        val states = state.states.toList()
        // visit child states
        for (s in states.indices) {
            visit(states[s])
            if (s != states.lastIndex && state.childMode == ChildMode.PARALLEL)
                line(PARALLEL)
        }

        // add initial transition
        line("")
        val initialState = state.initialState
        if (initialState != null)
            line("$STAR --> ${initialState.graphName()}")

        // visit transitions
        states.flatMap { it.transitions }.forEach { visit(it) }

        // add finish transitions
        states.filterIsInstance<IFinalState>()
            .forEach { line("${it.graphName()} --> $STAR") }
    }

    private fun line(text: String) = builder.appendLine(SINGLE_INDENT.repeat(indent) + text)

    private fun transitionLabel(transition: Transition<*>): String {
        val entries = listOfNotNull(
            transition.name,
            transition.eventMatcher.eventClass.simpleName.takeIf { showEventLabels },
        )
        return label(entries.joinToString())
    }

    private companion object {
        const val STAR = "[*]"
        const val SINGLE_INDENT = "    "
        const val PARALLEL = "--"
        const val SHALLOW_HISTORY = "[H]"
        const val DEEP_HISTORY = "[H*]"
        const val CHOICE = "<<choice>>"
        fun IState.graphName(): String {
            val name = name?.replace(" ", "_") ?: "State${hashCode()}"
            return if (this !is StateMachine) name else "${name}_StateMachine"
        }

        fun label(text: String?) = if (!text.isNullOrBlank()) " : $text" else ""
    }
}

suspend fun StateMachine.exportToPlantUml(showEventLabels: Boolean = false) =
    with(ExportPlantUmlVisitor(showEventLabels)) {
        accept(this)
        export()
    }

fun StateMachine.exportToPlantUmlBlocking(showEventLabels: Boolean = false) = coroutineAbstraction.runBlocking {
    with(ExportPlantUmlVisitor(showEventLabels)) {
        accept(this)
        export()
    }
}