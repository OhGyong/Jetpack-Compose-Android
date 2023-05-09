# [State in Compose](https://developer.android.com/codelabs/jetpack-compose-state?hl=ko&continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fjetpack-compose-for-android-developers-1%3Fhl%3Dko%23codelab-https%3A%2F%2Fdeveloper.android.com%2Fcodelabs%2Fjetpack-compose-state#0)

이 프로젝트는 android-compose-codelabs의 [BasicStateCodelab](https://github.com/googlecodelabs/android-compose-codelabs/tree/main/BasicStateCodelab) 프로젝트를 바탕으로 Codelab에 맞춰 진행하였습니다.

---

## 1~2. 프로젝트 소개, 계획
Jecpack Compose에서 `State`를 사용하는 것과 관련된 핵심 개념을 배울 것이다.
State에 따라 UI에 표시되는 항목이 결정되는 방식, State가 변경될 때 다양한 API를 사용해서 UI를 업데이트하는 방법,
Composable 함수의 구조를최적화하는 방법, Compose 환경에서 ViewModel을 사용하는 방법을 익힐 것이다.

---

## 3. Compose의 State(State in Compose)
앱에서 State는 시간이 지남에 따라 변할 수 있는 값이다.
이것은 매우 광범위한 정의로서 Room 데이터베이스부터 클래스 변수까지 모든 항목이 포함된다.

앱에서 State의 예는 다음과 같다.
- 채팅 앱에서 가장 최근에 수신된 메시지
- 사용자의 프로필 사진
- 아이템 리스트의 스크롤 위치

---

## 4. Compose의 Event(Events in Compose)
State는 언제 업데이트 될까?
안드로이드 앱에서는 Event에 대한 응답으로 State가 업데이트된다.
Event는 애플리케이션 외부 또는 내부에서 생성되는 입력으로 다음과 같이 예를 들 수 있다.
- 버튼 누르기 등으로 UI와 상호작용하는 사용자
- 새로운 값을 전송하는 센서 또는 네트워크

즉 앱 State로 UI에 표시할 항목에 관한 설명을 제공하고, Event라는 메커니즘을 통해 State와 UI를 변경하는 것이다.
~~~ 
State는 존재하고 Event는 발생한다고 말함
~~~

Compose에서 State 관리는 State와 Event가 서로 상호작용하는 방식을 이해하는 것이 핵심이다.

---

## 5. Composable 함수의 메모리(Memory in a composable function)
Compose 앱은 Composable 함수를 호출해서 데이터를 UI로 변환한다.
Composable을 실행할 때 Compose에서 빌드한 UI에 관한 설명을 `Composition`이라고 한다.
State가 변경되면 Compose는 영향을 받는 Composable 함수를 새로운 State로 다시 실행하게되면서
`Recomposition`이라는 업데이트된 UI가 만들어진다.

또한 Compose는 데이터가 변경된 Component만 Recompose를 하고 영향을 받지 않는 Component는
건너뛰도록 개별 Composable에 필요한 데이터를 확인한다.

위 과정이 가능하려면 Compose가 추적할 State를 알아야한다.
그래야 업데이트를 받을 때 Recomposition을 예약할 수 있다.

Compose에는 특정 State를 읽는 Composable의 Recomposition을 예약하는 특별한 State 추적 시스템이 있다.
이를 통해 Comopose가 세분화되어 전체 UI가 아닌 변경해야 하는 Compsable 함수만 Recompose할 수 있다.
이 작업은 State에 대해서 '쓰기(상태 변경)' 뿐만 아니라 '읽기'도 추적하여 실행된다.

Compose는 `State` 및 `MutableState`를 사용하여 Compose에서 State를 관찰할 수 있도록 한다.
Compose는 State `value` 속성을 읽는 각 Composable을 추적하고 그 value가 변경되면 `Recomposition`을 트리거한다.
`mutableStateOf` 함수를 사용하여 Observable한 `MutableState`를 만들 수 있다.
이 함수는 초기값을 `State` 객체에 래핑된 매개변수로 수신한 다음, `value`의 값을 Observable 하게 만든다.

```kotlin
val count: MutableState<Int> = mutableStateOf(0)
```

하지만 위 코드로만 사용할 경우 Recomposition이 발생했을 때 count라는 변수가 다시 0으로 초기화되어
Recomposition 간에 값을 유지할 방법이 필요하다.

이를 위해서 Composable inline 함수인 `remember`가 존재한다.
remember로 계산된 값은 Initial Composition 중에 Composition에 저장되고
저장된 값은 Recomposition 간에도 유지되도록 한다.

일반적으로 `remember`와 `mutableStateOf`는 Composable 함수에서 함께 사용된다.

```kotlin
val count: MutableState<Int> = remember { mutableStateOf(0) }
```

코틀린의 속성 위임을 사용하여 아래와 같이 코드를 간소화 할 수 있다.

```kotlin
var count by remember {mutableStateOf(0)}
```

---

## 6. State 기반의 UI(State driven UI)
Compose는 선언형 UI 프레임워크이다.
State가 변경될 때 UI Component를 삭제하거나 visibility 속성을 변경하는 대신
특정 State의 조건에서 UI가 어떻게 존재하는지를 설명한다.
Recompose가 호출되고 UI가 업데이트된 결과, Composable이 결국 Composable을
시작하거나 종료할 수 있다.

이 접근 방식을 사용하면 View 시스템과 마찬가지로 View를 수동으로 업데이트하는 복잡성을 방지할 수 있다.
새 State에 따라 View를 업데이트하는 일이 자동으로 발생하므로 오류도 적게 발생한다.

[Android Studio의 Layout Inspector 도구](https://developer.android.com/studio/debug/layout-inspector?hl=ko)
를 사용하여 Compose에서 생성된 앱 레이아웃을 검사할 수 있다.
~~~
`Tools > Layout Inspector`에 Layout Inspector가 존재한다.<br>
참고로 API가 29 이상인 기기이어야 한다.
~~~

<br>

Button에 enable 속성을 주는 방법은 아래와 같다.
```kotlin
Button(onClick = { count++}, Modifier.padding(top = 8.dp), enabled = count<10)
```

---

## 7.Composition의 Remember(Remember in Composition)
`remember`는 Composition에 객체를 저장하고, remember가 호출되는 소스 위치가 Recomposition 중에
다시 호출되지 않으면 객체를 삭제한다.

```kotlin
Column(modifier = modifier.padding(16.dp)) {
    var count by remember { mutableStateOf(0) }

    if(count > 0) {
        var showTask by remember { mutableStateOf(true) }
        if(showTask) {
            WellnessTaskItem(
                taskName = "Have you taken your 15 minute walk today?",
                onClose = { showTask=false }
            )
        }
        Text(text = "You've had $count glasses.")
    }

    Row(Modifier.padding(top = 8.dp)) {
        Button(onClick = { count++}, Modifier.padding(top = 8.dp), enabled = count<10) {
            Text(text = "Add one")
        }
        Button(onClick = { count=0 }, Modifier.padding(start=8.dp)) {
            Text(text = "Clear water count")
        }
    }
}
```

위와 같은 코드에서 count 값이 0으로 변경될 경우 showTask는 호출되는 코드 위치가 호출되지 않기 때문에 삭제된다.
따라서 showTask의 이전 값은 저장되지 않고 초기화된다.

---

## 8. Compose에서 state 복원(Restore state in Compose)
화면의 회전, 다크 모드 전환, 언어 변경 등과 같은 구성 변경을 하면 Activity는 재실행되기 때문에 저장된 state가 삭제된다.
remember를 사용하면 Recomposition 간에 State를 유지하는 데 도움이 되지만 구성 변경 간에는 유지되지 않는다.
이를 위해서 remember 대신 `rememberSaveable`을 사용해야 한다.

rememberSaveable은 Bundle에 저장할 수 있는 모든 값을 자동으로 저장한다.
~~~
Bundle은 데이터를 key-value 쌍으로 저장하는 컨테이너.
~~~

앱의 State 및 요구사항에 따라 remember를 사용할지 rememberSaveable을 사용할지 고려해야 한다.

---

## 9. State hoisting
remember를 사용하여 객체를 저장하는 Composable에는 내부 State가 포함되어 있어 Composable을 `Stateful`로 만든다.
이는 호출자가 State를 제어할 필요가 없고 State를 직접 관리하지 않아도 State를 사용할 수 있는 경우에 유용하다.
그러나 내부 State를 갖는 Composable은 재사용 가능성이 적고 테스트하기가 더 어려운 경향이 있다.

반면 State를 보유하지 않는 Composable을 `Stateless` Composable이라고 한다.
State hoisting을 사용하면 Stateless Composable을 쉽게 만들 수 있다.

Compose에서 State hoisting은 Composable을 Stateless로 만들기 위해 State를 Composable의 호출자로 옮기는 패턴이다.
Jetpack Compose에서 State hoisting을 위한 일반적 패턴은 State 변수를 다음 두 개의 매개변수로 바꾸는 것이다.
- `value: T` - 표시할 현재 값
- `onValueChange: (T) -> Unit` - 값을 변경하도록 요청하는 이벤트(T는 제안된 새로운 값)

~~~
State가 내려가고 Event가 올라가는 패턴을 단방향 데이터 흐름(UDF)이라고 하며,
State hoisting은 이 아키텍처를 Compose에서 구현하는 방법이다.
~~~

Hoiested State는 몇 가지 속성을 갖는다.
- 단일 소스 저장소 : State를 복제하는 대신 옮겼기 때문에 소스 저장소가 하나만 있다.(버그 방지에 도움)
- 공유 가능함 : Hoisted State를 여러 Composable과 공유할 수 있다.
- 가로채기 가능함 : Stateless Composable의 호출자는 State를 변경하기 전에 Event를 무시할지 수정할지 결정할 수 있다.
- 분리됨 : Stateless Comoposable 함수의 State는 어디에든(ViewModel) 저장할 수 있다.

```kotlin
@Composable
fun StatefulCounter() {
    var waterCount by remember { mutableStateOf(0) }

    var juiceCount by remember { mutableStateOf(0) }

    StatelessCounter(waterCount, { waterCount++ })
    StatelessCounter(juiceCount, { juiceCount++ })
}
```

```kotlin
@Composable
fun StatefulCounter() {
   var count by remember { mutableStateOf(0) }

   StatelessCounter(count, { count++ })
   AnotherStatelessMethod(count, { count *= 2 })
}
```

위 코드는 재사용성과 여러 Composable 함수에 동일한 State를 제공할 수 있음의 예시이다.

State를 hoisting 할 때 State의 이동 위치를 쉽게 파악할 수 있는 세 가지 규칙이 있다.
1. State는 적어도 그 상태를 사용하는 모든 Composable의 가장 낮은 공통 상위 요소에 배치해야 한다.
2. State는 최소한 변경될 수 있는 가장 높은 수준으로 배치해야 한다.
3. 두 State가 동일한 이벤트에 대한 응답으로 변경되는 경우 두 State는 동일한 수준에 배치해야 한다.

---

## 10. 리스트(Work with lists)
