# KStateMachine

![Build and test with Gradle](https://github.com/KStateMachine/kstatemachine/workflows/Build%20and%20test%20with%20Gradle/badge.svg)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=nsk90_kstatemachine&metric=alert_status)](https://sonarcloud.io/dashboard?id=nsk90_kstatemachine)
[![codecov](https://codecov.io/gh/nsk90/kstatemachine/branch/master/graph/badge.svg?token=IR2JR43FOZ)](https://codecov.io/gh/nsk90/kstatemachine)
[![Maven Central Version](https://img.shields.io/maven-central/v/io.github.nsk90/kstatemachine?logo=sonatype)](https://central.sonatype.com/artifact/io.github.nsk90/kstatemachine)
[![JitPack](https://img.shields.io/jitpack/version/io.github.nsk90/kstatemachine?style=flat&logo=jitpack&color=brgreen)](https://jitpack.io/#nsk90/kstatemachine)
[![multiplatform support](https://img.shields.io/badge/multiplatform-jvm%20%7C%20android%20%7C%20ios%20%7C%20js%20%7C%20wasm-brightgreen)](https://kstatemachine.github.io/kstatemachine/#multiplatform)

[![Open Collective](https://img.shields.io/badge/open%20collective-kstatemachine-lightblue?logo=opencollective&style=flat)](https://opencollective.com/kstatemachine)
[![JetBrains support](https://img.shields.io/badge/JetBrains-support-black?style=flat&logo=jetbrains)](https://jb.gg/OpenSourceSupport)
[![Mentioned in Awesome Kotlin](https://awesome.re/mentioned-badge.svg)](https://github.com/KotlinBy/awesome-kotlin)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-kstatemachine-green.svg?style=flat)](https://android-arsenal.com/details/1/8276)
[![Share on X](https://img.shields.io/badge/twitter-share-white?logo=x&style=flat)](https://twitter.com/intent/tweet?text=I%20like%20KStateMachine%20library%20%0A%0Ahttps%3A%2F%2Fgithub.com%2Fkstatemachine%2Fkstatemachine&hashtags=kstatemachine,kotlin,opensource)
[![Share on Reddit](https://img.shields.io/badge/reddit-share-red?logo=reddit&style=flat)](https://www.reddit.com/submit?url=https%3A%2F%2Fgithub.com%2Fkstatemachine%2Fkstatemachine&title=I%20like%20KStateMachine%20library)
[![Slack](https://img.shields.io/badge/slack-kstatemachine-purple?logo=slack&style=flat)](https://kotlinlang.slack.com/archives/C07DVAEKLM8)

![KStateMachine](./docs/kstatemachine-logo.png)

**[Documentation](https://kstatemachine.github.io/kstatemachine) |
[KDoc](https://kstatemachine.github.io/kstatemachine/kdoc/index.html) |
[Sponsors](#-sponsors) |
[Quick start](#-quick-start-sample) |
[Samples](#-samples) |
[Install](#-install) |
[Contribution](#-contribution) |
[Support](#-support) |
[Roadmap](#-roadmap) |
[License](#-license) |
[Discussions](https://github.com/kstatemachine/kstatemachine/discussions)**

**KStateMachine** is a powerful **Kotlin Multiplatform** library with clean DSL syntax for creating
complex [state machines](https://en.wikipedia.org/wiki/Finite-state_machine)
and [statecharts](https://www.sciencedirect.com/science/article/pii/0167642387900359/pdf) driven by 
**Kotlin Coroutines**.

## 🌏 Overview

### 📦 Integration features

* **[Kotlin DSL](https://kotlinlang.org/docs/type-safe-builders.html#scope-control-dslmarker) syntax** -
  declarative and clear state machine structure. Using without DSL is also possible.
* **[Kotlin Coroutines](https://kstatemachine.github.io/kstatemachine/pages/multithreading.html#kotlin-coroutines)
  support** -
  call suspendable functions within the library.
  You can fully use KStateMachine without Kotlin Coroutines dependency if necessary.
* **[Kotlin Multiplatform](https://kstatemachine.github.io/kstatemachine/pages/multiplatform.html) support**
* **Zero dependency** - it is written in pure Kotlin, main library artifact does not depend on any third party libraries
  or Android SDK.

### ⚙️ State management features

* **[Event based](https://kstatemachine.github.io/kstatemachine/pages/events.html)** - [transitions](https://kstatemachine.github.io/kstatemachine/pages/transitions/transitions.html) are performed by
  processing
  incoming events
* **[Reactive](https://kstatemachine.github.io/kstatemachine/pages/states/states.html#listen-states)** - listen for
  machine, states,
  [state groups](https://kstatemachine.github.io/kstatemachine/pages/states/states.html#listen-group-of-states) and
  transitions
* **[Guarded](https://kstatemachine.github.io/kstatemachine/pages/transitions/transitions.html#guarded-transitions)
  and [Conditional transitions](https://kstatemachine.github.io/kstatemachine/pages/transitions/transitions.html#conditional-transitions)** - dynamic
  target
  state which is calculated in a moment of event processing depending on application business logic
* **[Nested states](https://kstatemachine.github.io/kstatemachine/pages/states/states.html#nested-states)** - build
  hierarchical state machines
  (statecharts)
  with [cross-level transitions](https://kstatemachine.github.io/kstatemachine/pages/transitions/transitions.html#cross-level-transitions)
  support
* **[Composed (nested) state machines](
  https://kstatemachine.github.io/kstatemachine/pages/states/states.html#composed-nested-state-machines
  )** - use state machines as atomic child states
* **[Pseudo states](https://kstatemachine.github.io/kstatemachine/pages/states/pseudo_states.html)** for additional
  logic in machine
  behaviour
* **[Typesafe transitions](https://kstatemachine.github.io/kstatemachine/pages/transitions/typesafe_transitions.html)** - pass data in
  typesafe way
  from event to state
* **[Parallel states](https://kstatemachine.github.io/kstatemachine/pages/states.html#parallel-states)** - avoid a
  combinatorial
  explosion of
  states
* **[Undo transitions](https://kstatemachine.github.io/kstatemachine/pages/transitions/transitions.html#undo-transitions)** - navigate back to previous
state (like stack based FSMs do)

* **[Optional argument](https://kstatemachine.github.io/kstatemachine/pages/events.html#event-argument)** passing for
  events and
  transitions
* **[Export](https://kstatemachine.github.io/kstatemachine/pages/export.html)** state machine structure
  to [PlantUML](https://plantuml.com/) and [Mermaid](https://mermaid.js.org/) diagrams
* **[Persist (serialize)](https://kstatemachine.github.io/kstatemachine/pages/persistence.html)** state machine's
  active
  configuration and restore it later. Built-in `kotlinx.serialization` support.
* **[Testable](https://kstatemachine.github.io/kstatemachine/pages/testing.html)** - run state machine from specified
  state and enable internal logging
* **[Well tested](https://github.com/kstatemachine/kstatemachine/tree/master/tests/src/commonTest/kotlin/ru/nsk/kstatemachine)** - all features are covered
by tests

## 📄 Documentation

> [!IMPORTANT]
> * See full documentation on [website](https://kstatemachine.github.io/kstatemachine)
> * [KDoc for classes](https://kstatemachine.github.io/kstatemachine/kdoc/index.html)

## ❤️ Sponsors

I highly appreciate that you donate or become a sponsor to support the project.
If you find this project useful you can support it by:

* Pushing the ⭐ star-button
* Using ❤️github-sponsors button to see supported donation methods

## 🚀 Quick start sample

### 🚦Finishing traffic light

```mermaid
stateDiagram-v2
    direction TB

    classDef red fill:#f00,color:white,font-weight:bold,stroke-width:2px,stroke:black
    classDef yellow fill:yellow,color:black,font-weight:bold,stroke-width:2px,stroke:black
    classDef green fill:green,color:white,font-weight:bold,stroke-width:2px,stroke:black

    [*] --> RedState
    RedState --> YellowState: SwitchEvent
    YellowState --> GreenState: SwitchEvent
    GreenState --> [*]

    class RedState red
    class YellowState yellow
    class GreenState green
```

```kotlin
// define your Events
object SwitchEvent : Event

// define your States as classes or objects
sealed class States : DefaultState() {
    object RedState : States()
    object YellowState : States()

    // machine finishes when enters [FinalState]
    object GreenState : States(), FinalState
}

fun main() = runBlocking {
    // create state machine and configure its structure in a setup block
    val machine = createStateMachine(scope = this) {
        addInitialState(RedState) {
            // add state listeners
            onEntry {
                println("Enter red")
                // you can call suspendable code if necessary
                delay(10)
            }
            onExit { println("Exit red") }

            // setup transition
            transition<SwitchEvent> {
                targetState = YellowState
                // add transition listener
                onTriggered { println("Transition triggered") }
            }
        }

        addState(YellowState) {
            transition<SwitchEvent>(targetState = GreenState)
        }

        addFinalState(GreenState)

        onFinished { println("Finished") }
    }
    // you can observe state machine changes using [Flow] along with simple listeners
    val statesFlow = machine.activeStatesFlow()

    // you can process events after state machine has been started even from listener callbacks
    machine.processEvent(SwitchEvent) // machine goes to [YellowState]
    machine.processEvent(SwitchEvent) // machine goes to [GreenState]
}
```

## 🧪 Samples

* [Android 2D shooter game sample](https://github.com/kstatemachine/android-kstatemachine-sample)

  The library itself does not depend on Android.

  <p align="center">
      <img src="https://github.com/kstatemachine/android-kstatemachine-sample/blob/main/images/android-app-sample.gif"
          alt="Android sample app" width="30%" height="30%"/>
  </p>
* [Compose 2D shooter game sample](https://github.com/KStateMachine/compose-kstatemachine-sample)
* [Finished state sample](./samples/src/commonMain/kotlin/ru/nsk/samples/FinishedStateSample.kt)
* [Transition on FinishedEvent sample](./samples/src/commonMain/kotlin/ru/nsk/samples/FinishedEventSample.kt)
* [FinishedEvent using with DataState sample](./samples/src/commonMain/kotlin/ru/nsk/samples/FinishedEventDataStateSample.kt)
* [Undo transition sample](./samples/src/commonMain/kotlin/ru/nsk/samples/UndoTransitionSample.kt)
* [PlantUML nested states export sample](./samples/src/commonMain/kotlin/ru/nsk/samples/PlantUmlExportSample.kt)
* [Mermaid nested states export sample](./samples/src/commonMain/kotlin/ru/nsk/samples/MermaidExportSample.kt)
* [PlantUML with UmlMetaInfo export sample](./samples/src/commonMain/kotlin/ru/nsk/samples/PlantUmlExportWithUmlMetaInfoSample.kt)
* [PlantUML with ExportMetaInfo unsafe export sample](https://github.com/KStateMachine/kstatemachine/tree/master/samples/src/commonMain/kotlin/ru/nsk/samples/PlantUmlUnsafeExportWithExportMetaInfoSample.kt)
* [Inherit transitions by grouping states sample](./samples/src/commonMain/kotlin/ru/nsk/samples/InheritTransitionsSample.kt)
* [Minimal sealed classes sample](./samples/src/commonMain/kotlin/ru/nsk/samples/MinimalSealedClassesSample.kt)
* [Usage without Kotlin Coroutines sample](./samples/src/commonMain/kotlin/ru/nsk/samples/StdLibMinimalSealedClassesSample.kt)
* [Minimal syntax sample](./samples/src/commonMain/kotlin/ru/nsk/samples/MinimalSyntaxSample.kt)
* [Guarded transition sample](./samples/src/commonMain/kotlin/ru/nsk/samples/GuardedTransitionSample.kt)
* [Cross-level transition sample](./samples/src/commonMain/kotlin/ru/nsk/samples/CrossLevelTransitionSample.kt)
* [Typesafe transition sample](./samples/src/commonMain/kotlin/ru/nsk/samples/TypesafeTransitionSample.kt)
* [Event recording sample](./samples/src/commonMain/kotlin/ru/nsk/samples/SerializationEventRecordingSample.kt)
* [Complex syntax sample](./samples/src/commonMain/kotlin/ru/nsk/samples/ComplexSyntaxSample.kt)
  shows many syntax variants and library possibilities, so it looks messy



## State Machine Visualization

To visualize your state machine, follow these steps:

1. Export your state machine data using the `exportStateMachineData` function.
2. Save the exported data to a JSON file (e.g., `stateMachineData.json`).
3. Open `visualization/index.html` in a web browser.
4. Replace the sample data in `app.js` with your exported data.

The interactive diagram will update to display your state machine's structure and transitions.

## 💾 Install

KStateMachine is available on `Maven Central` and `JitPack` repositories.

See [install section in the docs](https://kstatemachine.github.io/kstatemachine/pages/install.html) for details.

### Maven Central

```kotlin
dependencies {
    // multiplatform artifacts, where <Tag> is a library version.
    implementation("io.github.nsk90:kstatemachine:<Tag>")
    implementation("io.github.nsk90:kstatemachine-coroutines:<Tag>")
    implementation("io.github.nsk90:kstatemachine-serialization:<Tag>")
}
```

## 🏗️ Build

Run `./gradlew build` or build with `Intellij IDEA`.

## 🤝 Contribution

The library is in development phase. You are welcome to propose useful features and contribute to the project.
See [CONTRIBUTING](./CONTRIBUTING.md) file.

## 🙋 Support

I am open to answer you questions and feature requests. Fill free to use any of communication channels to
give your feedback.

* [Slack channel](https://kotlinlang.slack.com/archives/C07DVAEKLM8) or
  [GitHub discussions](https://github.com/kstatemachine/kstatemachine/discussions) - best for questions and discussions
* [GitHub issues](https://github.com/KStateMachine/kstatemachine/issues) - best for bugs and feature requests

If you use some other platforms to ask questions or mention the library, I recommend adding a <ins>link</ins> to this
GitHub project or using `#kstatemachine` tag.

## 🗺️ Roadmap

* Make publication about using state machines along with `MVI`
* Create `Intellij IDEA Plugin` for state machine visualization and edition

## 🏅 Thanks to supporters

[![Stargazers repo roster for @kstatemachine/kstatemachine](https://reporoster.com/stars/dark/kstatemachine/kstatemachine)](https://github.com/kstatemachine/kstatemachine/stargazers)
[![Forkers repo roster for @kstatemachine/kstatemachine](https://reporoster.com/forks/dark/kstatemachine/kstatemachine)](https://github.com/kstatemachine/kstatemachine/network/members)

## 🖋️ License

Licensed under permissive [Boost Software License](./LICENSE)
