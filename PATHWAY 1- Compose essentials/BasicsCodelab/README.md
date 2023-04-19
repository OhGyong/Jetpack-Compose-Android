[Jetpack Compose basics Codelab](https://developer.android.com/codelabs/jetpack-compose-basics?continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fjetpack-compose-for-android-developers-1%3Fhl%3Dko%23codelab-https%3A%2F%2Fdeveloper.android.com%2Fcodelabs%2Fjetpack-compose-basics#0)

setContent 내에서 사용되는 앱 테마는 프로젝트 이름에 맞게 지정됨.
Theme.kt를 찾아보면 프로젝트 이름과 일치하는 테마가 설정되어 있음.<br>
```kotlin
    setContent {
        // setContent 내에서 사용되는 앱 테마는 프로젝트 이름에 맞게 지정됨.
        BasicsCodelabTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Greeting("Android")
            }
        }
    }
```

---

## 3. Compose 시작하기(Getting started with Compose)

Composable 함수는 `@Composable` 주석이 달린 일반 함수.<br>
이 주석을 달면 함수가 내부에서 다른 Composable 함수를 호출할 수 있음.<br>

Compose를 사용하면 Activity가 Android 앱의 진입점으로 유지됨.<br/>
`setContent`를 사용하여 레이아웃을 정의하는데, 기존 명령형 UI에서 XML 파일을 사용하는 것 대신에
Composable 함수를 호출함.

안드로이드 스튜디오 미리보기를 사용하려면 매개변수가 없는 Composable 함수 또는 기본 매개변수를 포함하는 함수를
`@Preview` 주석으로 표시하고 프로젝트를 빌드하면 됨. 참고로 동일한 파일에 미리보기를 여러개 만들고 이름을 지정할 수 있다.

---

## 4. UI 조정(Tweaking the UI)

레이아웃의 배경 색상을 변경하려면 Composable 함수를 `Surface`로 래핑해야 함.<br>
```kotlin
    Surface(color = MaterialTheme.colorScheme.primary) {
        Text (text = "Hello $name!")
    }
```
Surface와 MaterialTheme는 [Material Design](https://m3.material.io/)과 관련된 개념으로 Material Design은
사용자 인터페이스와 환경을 만드는 데 도움을 주기 위해 구글에서 만든 디자인 시스템임.<br>
> 참고로 material을 import할 때 버전 확인 잘하자. 예제에서는 material3으로 했음

color를 MaterialTheme를 사용해서 변경했는데 배경뿐과 텍스트가 같이 변경된 것을 볼 수 있는데
Material 구성요소는 앱에 넣고자 하는 공통 기능을 처리하기 때문.

<br>

Surface와 Text 같은 Compose UI 요소는 `modifier` 매개변수를 선택적으로 허용함.
modifier는 상위 요소 레이아웃 내에서 UI 요소가 배치되고 표시되고 동작하는 방식을 UI 요소에 알림.<br>
```kotlin
    Surface(color = MaterialTheme.colorScheme.primary) {
        Text(text = "Hello $name!", modifier = Modifier.padding(24.dp))
    }
```
modifier에는 정렬, 애니메이션 처리, 배치, 클릭 가능 여부, 스크롤 가능 여부, 변환 등 사용할 수있는 수십 가지의 정보를 갖고 있음.

---

## 5. Composable 재사용(Reusing composables)

UI에 추가하는 구성요소가 많을수록 생성되는 중첩 레벨이 많이지기 때문에 가독성에 영향을 줄 수 있음.
이런 이유로 재사용할 수 있는 구성요소를 만들면 앱에서 사용하는 UI 요소의 라이브러리를 쉽게 만들 수 있음.

함수는 기본적으로 modifier를 사용하지 않아도 empty modifier를 매개변수로 포함하는 것이 좋음.
함수 내에서 호출하는 첫 번째 Composable로 modifier를 전달하면 함수 외부에서 레이아웃 안내와 동작을 조정할 수 있게됨.
```kotlin
    @Composable
    private fun MyApp(modifier: Modifier = Modifier) {
        Surface(
            modifier = modifier,
            color = MaterialTheme.colorScheme.background
        ) {
            Greeting("Android")
        }
    }
```

--- 

## 6. 열과 행 만들기(Creating columns and rows)

Compose의 세 가지 기본 표준 레이아웃 요소는 `Column`, `Row`, `Box` 임.

Column은 내부의 각 하위 요소를 세로로 배치함.<br>
Row는 내부의 각 하위 요소를 가로로 배치함.<br>
(box는 설명 없음)

<br>

Composable 함수는 코틀린의 다른 함수처럼 `for`문과 같은 구문을 추가할 수 있음.
for문에 UI 요소를 작성하여 UI를 제작할 수 있음.
```kotlin
@Composable
    fun MyApp(
        modifier: Modifier = Modifier,
        names: List<String> = listOf("World", "Compose")
    ) {
        Column(modifier) {
            for (name in names) {
                Greeting(name = name)
            }
        }
    }
```

<br>

Button은 material3 패키지에서 제공하는 Composable 임.
후행 람다는 괄호 밖으로 이동할 수 있어서 모든 콘텐츠를 버튼의 하위 요소로 추가 가능.

`weight` 수정자는 요소를 유연하게 만들기 이해 가중치가 없는 다른 요소를 효과적으로 밀어내어 요소의 사용 가능한 모든 공간을 챙움.
이 modifier는 `fillMaxWidth`와 중복되기도 함.

---

## 7. Compose의 상태(State in Compose)

Compose에서 값을 `state`라고 함.<br>

Compose 앱은 Composable 함수를 호출하여 데이터를 UI로 변환함.
데이터가 변경되면 Compose는 새 데이터로 이러한 함수를 다시 실행하여 업데이트된 UI를 만듬.
이것을 **리컴포지션(recomposition)** 이라고 함.
또한, Compose는 데이터가 변경된 구성요소만 다시 구성하고 영향을 받지 않는 구성요소는 다시 구성하지 않고 건너뛰도록 개별 Composable에서 필요한 데이터를 확인함.

리컴포지션을 하기 위해서는 값을 탐지하고 있어야 함.
`mutableStateOf` 함수를 사용해서 state 정보를 저장하는데 mutableStateOf를 할당하기만 해서는 state가 유지되지 않음.
state를 기억하기 위해서 값을 유지하는데 사용되는 `rember`까지 사용해야 함.
```kotlin
    @Composable
    fun Greeting() {
        val expanded = remember { mutableStateOf(false) }
        ...
    }
```

