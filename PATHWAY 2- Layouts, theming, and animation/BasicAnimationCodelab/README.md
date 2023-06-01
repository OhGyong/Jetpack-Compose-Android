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