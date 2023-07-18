# [Animating element in Jetpack Compose](https://developer.android.com/codelabs/jetpack-compose-animation?continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fjetpack-compose-for-android-developers-2%23codelab-https%3A%2F%2Fdeveloper.android.com%2Fcodelabs%2Fjetpack-compose-animation#0)
이 프로젝트는 android-compose-codelabs의 [AnimationCodelab](https://github.com/googlecodelabs/android-compose-codelabs/tree/main/AnimationCodelab) 프로젝트를 바탕으로 Codelab에 맞춰 진행하였습니다.

---

- `SnapshotStateList`<br>
일반적인 `mutableStateListOf` 함수는 `MutableList`를 반환하지만, Compose 라이브러리의 mutableStateListOf는
`SnapshotStateList`를 반환한다. SnapshotStateList는 Compose의 state 관리에 특화된 리스트 구현체로 Compose가
해당 값을 추적하고, 변경 사항을 감지하여 UI를 업데이트하는 데 사용된다.

<br>

- `전개 연산자('*')`<br>
```kotlin
    val allTasks = stringArrayResource(id = R.array.tasks)
    val tasks = remember { mutableStateListOf(*allTasks) }
```
위 코드에서 전개 연산자 `*`을 사용한 이유는 mutableStateListOf의 반환 값인 SnapshotStateList에 String 타입을
반환하기 위해서이다.
> SnapshotStateListOf를 초기화하려면 전개 연산자를 통해 개별 인자를 넘겨줘야 한다고 함.

<br>

- `LaunchedEffect`<br>
Composable 내에서 안전하게 suspend 함수를 실행하게 하는 Composable 함수이다. LaunchedEffect가 Composition이 되면
key(매개변수)로 전달된 코드 블록으로 코루틴이 실행된다. 또한 Composition이 종료되면  코루틴도 취소된다.

전달된 key 값은 일반 변수, state, property 등이 될 수 있고, 변경을 감지하여 코루틴을 실행한다.
만약 key 값이 다른 값으로 Recomposition되면 기존 코루틴은 취소되고 새 코루틴으로 실행된다.
> Activity를 사용할 때 생명 주기를 고려해서 `lifeCycleScope`로 코루틴을 실행했고, Compose를 사용할 때는
  `LaunchedEffect`를 사용해서 생명 주기를 고려한 코루틴을 실행한다.

<br>

- `rememberCoroutineScope`<br>
Compose에서 코루틴 스코프를 기억하는 기능을 제공하는 함수이다.
화면이 재구성 되더라도 코루틴 스코프가 유지되기 때문에 실행중인 코루틴이 취소되지 않는다. 
코루틴 스코프를 기억하는 것을 이용해서 다른 Composable에서 코루틴 생명 주기를 수동으로 관리해야 할 때
사용할 수 있다.

```kotlin
var editMessageShown by remember { mutableStateOf(false) }

suspend fun showEditMessage() {
  if (!editMessageShown) {
    editMessageShown = true
    delay(3000L)
    editMessageShown = false
  }
}

val coroutineScope = rememberCoroutineScope()

Scaffold(
  floatingActionButton = {
    HomeFloatingActionButton(
      // 이벤트 발생 시 코루틴 실행
      onClick = {
        coroutineScope.launch {
          showEditMessage()
        }
      }
    )
  }
)
```
위 코드로 예를 들면 사용자 이벤트가 발생할 때 코루틴을 실행하며, Composable의 생명 주기에 맞춰 관리할 수 있다.

<br>

- `TabRow`<br>
TabRow는 Material3에서 제공하는 탭 그룹을 표시하기 위한 Composable 함수이다.
전체 행을 따라 균등한 간격으로 탭을 배치하여 각 탭이 동일한 공간을 차지하도록 한다.
  - `selectedTabIndex`<br>
  선택된 탭의 인덱스 값이다.
  - `indicator`<br>
  탭의 UI를 커스텀할 수 있는 람다 함수이다.
  `tabPositions` 라는 값으로 현재 탭의 위치 정보를 알 수 있으며, 해당 위치에 선택된 탭을 표시할 수 있다.
  
    <br>
    두 개의 탭 항목이 있을 때 tabPositions의 값은 다음과 같다.<br>
    
    > [TabPosition(left=0.0.dp, right=180.0.dp, width=180.0.dp), TabPosition(left=180.0.dp, right=360.0.dp, width=180.0.dp)]

<br>

- `Enum.ordinal`<br>
ordinal은 열거형(enum)에서 각 항목의 순서를 나타내는 정수 값이다.
Enum class로 생성된 열거형 상수에서 첫 번째 상수부터 0부터 시작하여 순차적으로 증가한다.
```kotlin
private enum class TabPage {
    Home, Work
}

TabRow(
  selectedTabIndex = tabPage.ordinal,
  containerColor = backgroundColor,
  indicator = { tabPositions ->
    HomeTabIndicator(tabPositions, tabPage)
  }
)
```

<br>

- `FloatingActionButton`<br>
FloatingActionButton(FAB)는 일반적으로 앱 화면에서 사용자가 특정 작업을 수행할 수 있는 부동의 액션 버튼이다.
Scaffold에서 제공하는 floatingActionButton에 FloatingActionButton을 전달하여 설정한다.

<br>

- `LazyListState`<br>
  - `LazyListState.firstVisibleItemIndex`<br>
  현재 화면에 보이는 첫 번째 항목의 인텍스
  - `LazyListState.firstVisibleItemScrollOffset`<br>
  현재 화면에 보이는 첫 번째 항목의 오프셋

<br>

- `derivedStateOf` <br>
주로 다른 state 객체에서 특정 state가 계산되거나 파생되는 경우에 사용되는 함수이다.
이 함수를 사용하면 계산에서 사용되는 state 중 하나가 변경될 때만 계산이 실행된다.
<br>
계산이 복잡하거나 비용이 많이 드는 연산을 수행하는 경우에 유용하다.
derivedStateOf는 Compose의 성능을 향상시키고, 불필요한 재계산을 방지하는데 도움을 준다.
```kotlin
@Composable
private fun LazyListState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) { mutableStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableStateOf(firstVisibleItemScrollOffset)}
    return remember(this) {
        derivedStateOf {
            if(previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}
```

<br>

- `semantics`<br>
Composition은 UI를 설명하는 Composable로 구성된 트리 구조로 `Semantic Tree`라는 병렬 트리가 존재한다.
이 트리는 접근성 서비스와 테스트 프레임워크에서 사용자가 이해하기 쉽도록 UI를 설명한다.
semantics는 Compose에서 접근성을 설정하기 위한 Modifier이다.
```kotlin
@Composable
fun HomeHeader(title: String) {
    Text(
        text = title,
        modifier = Modifier.semantics { heading() },
        style = MaterialTheme.typography.headlineSmall
    )
}
```
위 코드에서 Modifier.semantics {heading()}은 해당 Text가 heading(제목?) 역할을 한다는 것을 나타낸다.
> 해당 코드가 없어도 Text 표시에는 문제가 없음.

- `getOrNull, key`<br>
`getOrNull`은 코틀린 표준 라이브러리에 포함된 함수로 list, array, map 과 같은 컬렉션에서 특정 인텍스 또는 키에 해당하는 요소를 가져온다.
주어진 인덱스나 키에 해당하는 값이 존재하면 해당 요소를 반환하고 유효하지 않거나 범위를 벗어나느 경우 null을 반환한다.
<br>
<br>
`key`는 Composable 요소의 고유한 키를 지정하는 데 사용된다.
key를 사용하면 Composition의 변경을 추적하고 업데이트를 최적화한다.
key 함수에 전달된 매개변수 값에 따라 Compose가 이전 값과 비교하여 변경된 작업에 대해서만 업데이트를 수행한다.
```kotlin
items(count = tasks.size) {
  val task = tasks.getOrNull(it)
  if(task != null) {
    key(task) {
      TaskRow(
        task = task,
        onRemove = {tasks.remove(task)}
      )
    }
  }
}
```

- `AnimatedVisibility`<br>
AnimatedVisibility는 콘텐츠 표시와 사라짐을 애니메이션화 한다.<br>
각 속성은 [여기](https://developer.android.com/jetpack/compose/animation?hl=ko#animatedvisibility)를 참고.
```kotlin
AnimatedVisibility(visible = visible) {
  Spacer(modifier = Modifier.height(8.dp))
}
```

- `splineBasedDecay`<br>
Compose Animation에서 사용되는 한 종류의 Decay 애니메이션이다.
Decay 애니메이션은 원래 값에서 시작하여 감쇠되는 속도로 애니메이션을 수행하는 것을 의미한다.

---

## 3. Animating a simple value change
`animate*AsState`는 Compose에서 가장 간단한 Animation API 중 하나이다.
이 API는 State가 변경될 때 애니메이션을 적용하고 싶을 때 사용한다.

```kotlin
// 적용 전
val backgroundColor = if (tabPage == TabPage.Home) Purple100 else Green300

// 적용 후
val backgroundColor by animateColorAsState(if (tabPage == TabPage.Home) Purple100 else Green300)
```
여기서 tabPage는 State 객체로 지원되는 값으로, 이 값에 따라 색상을 변경한다.
`animate*AsState` API 중 하나인 `animateColorAsState`을 사용하여 간단하게 애니메이션을 적용할 수 있다.

---

## 4. Animating visibility

`AnimatedVisibility`는 지정된 Boolean 값이 변경될 때마다 애니메이션을 실행한다.
애니메이션은 기본적으로 페이드 인/아웃으로 동작한다.<br>
```kotlin
AnimatedVisibility(
  visible = shown,
  enter = slideInVertically(
    // Enters by sliding down from offset -fullHeight to 0.
    initialOffsetY = { fullHeight -> -fullHeight },
    animationSpec = tween(durationMillis = 150, easing = LinearOutSlowInEasing)
  ),
  exit = slideOutVertically(
    // Exits by sliding up from offset 0 to -fullHeight.
    targetOffsetY = { fullHeight -> -fullHeight },
    animationSpec = tween(durationMillis = 250, easing = FastOutLinearInEasing)
  )
) {
  // ...
}
}
```
`EnterTransition`, `ExitTransition` 등 enter와 exit에 값을 제공하여 애니메이션을 맞춤설정할 수 있다.

`initialOffsetY`은 초기 위치를 반환하는 람다로,
항목의 전체 높이를 사용하도록 기본 동작을 조정하여 애니메이션이 올바르게 적용되도록 한다.

`animationSpec`은 시간이 지남에 따라 애니메이션 값을 어떻게 변경할지 지정할 수 있다.
`twin`은 애니메이션의 지속 시간(durationMills)과 타이밍(easing)를 설정하는데 사용된다.
`FastOutLinearInEasing`은 빠른 시작과 일정한 속도를 가지는 타이밍 함수를 나타낸다.

---

## 5. Animating content size change
`animateContentSize`는 Modifier 함수로, Column이나 Row 등과 같은 컨테이너 요소의 크기가 변경될 때
애니메이션 효과를 적용한다. animationSpec으로 애니메이션 맞춤설정을 할 수 있다.

```kotlin
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(16.dp)
      .animateContentSize()
  )
```

---

## 6. Animating multiple values
`Transition` API는 Transition의 모든 애니메이션이 완료된 시점을 추적할 수 있어 더 복잡한 애니메이션을 만들게 한다.
위에서 사용했던 개별 `animate*AsState`를 사용하는 경우에는 불가능한 일이다.

Transition은 `updateTransition` 함수를 사용하여 만들 수 있다. 
Transition을 만들었다면 Transition의 `animate*` 확잠 함수를 사용하여 각 애니메이션 값을 만들 수 있다.<br>
(확장 함수 예 : `animateDp`, `animateColor`)

```kotlin
  val transition = updateTransition(tabPage, label = "Tab indicator")
  val indicatorLeft by transition.animateDp(label = "Indicator left") { page ->
     tabPositions[page.ordinal].left
  }
  val indicatorRight by transition.animateDp(label = "Indicator right") { page ->
     tabPositions[page.ordinal].right
  }
  val color by transition.animateColor(label = "Border color") { page ->
     if (page == TabPage.Home) Purple700 else Green800
  }
```

<br>

또한 animationSpec 처럼 Transition도 `transitionSpec`을 사용하여 애니메이션 맞춤 설정을 할 수 있다.
```kotlin
  val indicatorLeft by transition.animateDp(
      transitionSpec = {
          if (TabPage.Home isTransitioningTo TabPage.Work) {
              // Indicator moves to the right.
              // The left edge moves slower than the right edge.
              spring(stiffness = Spring.StiffnessVeryLow)
          } else {
              // Indicator moves to the left.
              // The left edge moves faster than the right edge.
              spring(stiffness = Spring.StiffnessMedium)
          }
      },
      label = "Indicator left"
  ) { page ->
      tabPositions[page.ordinal].left
  }
```
`isTransitioningTo`는 현재 상태가 특정 상태로 전환 중인지 여부를 나타내는데 사용된다.
즉 위 코드에서는 'TabPage.Home'이 'TabPage.Work'로 전환 중인지를 확안한다.

`spring` 함수는 감쇠, 탄성, 강도 등을 조절하는 데 사용된다.

---

## 7. Repeating animations
```kotlin
  val infiniteTransition = rememberInfiniteTransition()
  val alpha by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 1f,
    animationSpec = infiniteRepeatable(
      animation = keyframes {
        durationMillis = 1000
        0.7f at 500
      },
      repeatMode = RepeatMode.Reverse
    )
  )
```
반복적으로 애니메이션을 처리하기 위해서 `InfiniteTransition`을 사용할 수 있다.
`Transition`처럼 여러 값에 애니메이션을 적용할 수 있으면서도 InfiniteTransition은 값에 무기한으로 애니메이션을 적용한다.

InfiniteTransition을 만들려면 `rememberInfiniteTransition` 함수를 사용해야 한다.
그 뒤 animate* 확장 함수 중 하나를 사용하여 선언하면 된다.

위 코드에서는 초기와 최종 값을 각각 0f와 1f로 세팅하고, `InfiniteRepeatableSpec`을 사용하기 위해 
`infiniteRepeatable` 함수를 사용한다.

`keyFrames`는 밀리초 단위에서 진행 중인 값을 변경할 수 있는 animationSpec이다.
위 코드의 겅우 500ms 내에 값이 0에서 0.7까지 빠르게 진행되고 0.7에서 1.0까지 천천히 속도를 줄이며 끝을 향해 진행된다.

기본적으로 repeatMode는 RepeatMode.Restart다.
이 경우에는 초기 값에서 최종 값으로 전환 된 이후에 다시 초기 값 부터 시작된다.
RepeatMode.Reverse로 설정하면 최종 값으로 전환 된 이후 최종 값에서 초기값으로 진행된다.

---

## 8. Gesture animation
리스트에서 스와이프 입력 방식을 통해 아이템을 제거하는데 플링 애니메이션을 적용하는 방법을 알아본다.

우선 터치 요소를 스와이프 할 수 있도록 하는 `Modifier.swipeToDissMiss`를 만든다.
아이템 요소가 화면의 가장자리로 플링되면 요소가 삭제될 수 있도록 onDismissed 콜백을 호출한다.

사용자가 리스트의 아이템에 손가락을 대면 x,y 좌표가 있는 터치 이벤트가 생성되고, 손가락을 오른쪽이나 왼쪽으로 이동하면 x 좌표도 이동한다.
선택된 아이템은 사용자의 동작에 따라 이동해야 하므로 터치 이벤트의 위치와 속도에 따라 아이템의 위치가 업데이트된다. 

```kotlin
private fun Modifier.swipeToDismiss(
    onDismissed: () -> Unit
): Modifier = composed {
    // todo 1
    val offsetX = remember { Animatable(0f) }
  
    // todo 2
    pointerInput(Unit) {
      
      // todo 3
      val decay = splineBasedDecay<Float>(this)
      
      // todo 4
      coroutineScope {
        while (true) {
            
          // todo 5 
          val velocityTracker = VelocityTracker()
          
          // todo 6
          awaitPointerEventScope {
            
            // todo 7
            val pointerId = awaitFirstDown().id
            
            // todo 8
            horizontalDrag(pointerId) { change ->
              val horizontalDragOffset = offsetX.value + change.positionChange().x
              launch {
                offsetX.snapTo(horizontalDragOffset)
              }
              velocityTracker.addPosition(change.uptimeMillis, change.position)
              if (change.positionChange() != Offset.Zero) change.consume()
            }
          }
          
          // todo 9
          offsetX.stop()
          val velocity = velocityTracker.calculateVelocity().x
          val targetOffsetX = decay.calculateTargetValue(offsetX.value, velocity)
          offsetX.updateBounds(
            lowerBound = -size.width.toFloat(),
            upperBound = size.width.toFloat()
          )
          
          // todo 10
          launch {
            if (targetOffsetX.absoluteValue <= size.width) {
              offsetX.animateTo(targetValue = 0f, initialVelocity = velocity)
            }
            else {
              offsetX.animateDecay(velocity, decay)
              onDismissed()
            }
          }
        }
      }
    }
        // todo 2
        .offset { IntOffset(offsetX.value.roundToInt(), 0) }
}
```

#### todo 1
사용자의 입력 이벤트에 따라 애니메이션의 offset 값을 업데이트하기 위해 Animatable 객체를 remember로 저장한다.

<br>

#### todo 2
`pointerInput()`은 사용자의 포인터 입력 이벤트를 수신하고 처리하는 콜백 함수이다.
해당 콜백에서 사용자의 입력에 따른 좌표 값과 애니메이션의 오프셋 값을 계산한다.

콜백에서 구한 애니메이션의 좌표 값은 `offset`을 사용하여 요소의 위치를 배치한다.
요소의 위치를 배치하는 방법은 offset 함수의 파라미터인 `IntOffset`으로 가로와 세로 위치를 지정한다.

offset이 콜백 함수가 아니여도 IntOffset에 사용되는 offsetX 값은 remember로 상태로 저장됐기 때문에
offsetX의 값이 변경되면 offset 함수가 호출되면서  UI 요소가 업데이트되어 애니메이션 동작을 확인할 수 있게 된다.

<br>

#### todo 3
`splineBasedDecay`는 Compose 애니메이션에서 사용되는 한 종류인 Decay 애니메이션이다.
Decay 애니메이션은 원래 값에서 시작하여 감쇠되는 속도로 애니메이션을 수행하는 것을 의미하는데,
이런 동작성으로 주로 스크롤 또는 드래그와 관련된 애니메이션에 사용된다.

splineBasedDecay을 사용해서 Decay 애니메이션 객체를 만들고 애니메이션 오프셋 값을 구해서 사용할 것이다.

<br>

#### todo 4
터치 이벤트의 좌표 및 속도와 애니메이션의 오프셋은 코루틴을 실행하여 비동기로 계산하고,
이 값을 계속 계산할 수 있도록 무한 루프를 생성한다.

<br>

#### todo 5
`VelocityTracker`는 사용자의 터치 이벤트를 추적하여 속도를 계산하는 데 사용된다.
사용자의 입력 이벤트가 발생했을 때 시간과 위치 정보를 전달하면 이를 바탕으로 속도를 계산한다.
여기서는 스와이프에 소모된 속도를 계산하기 위해서 사용된다.

<br>

#### todo 6
`awaitPointEventScope`는 사용자의 입력 이벤트를 비동기적으로 처리할 수 있는 정지 함수로 pointerInput 블록 내에서만 호출되어야 한다.

`awaitPointEventScope.currentEvent.type`을 로깅해보면 사용자의 입력이 없어도 Move가 찍히고 클릭을 했을 때는 Release 상태만 찍힌다.
그 이유는 Move의 경우 UI 요소의 위치가 변경되면 해당 요소의 Pointer의 상태가 Move가 되기 때문이다. 
그리고 Release의 경우 요소를 클릭했을 때 Down, 땠을 때는 Release 상태가 되는데
비동기적으로 동작하는 awaitPointEventScope는 입력 이벤트가 완료된 시점에 호출되기 때문에 Release 상태만 확인이 가능하다.([PointerEventType 링크](https://developer.android.com/reference/kotlin/androidx/compose/ui/input/pointer/PointerEventType))

<br>

#### todo 7
`awaitFirstDown().id`를 통해서 첫 번째 입력 이벤트의 id를 사용하여 Pointer를 구분하게 한다.
