[Jetpack Compose basics Codelab](https://developer.android.com/codelabs/jetpack-compose-basics?continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fjetpack-compose-for-android-developers-1%3Fhl%3Dko%23codelab-https%3A%2F%2Fdeveloper.android.com%2Fcodelabs%2Fjetpack-compose-basics#0)

## 1~2. Codelab을 시작하기 전에 Compose를 알아보자(Before you begin)

`Jetpack Compose`는 UI 개발을 간소화하기 위해 설계된 최신 개발 도구로, 
반응형 프로그래밍 모델을 코틀린 프로그래밍 언어의 간결성과 사용 편의성을 갖도록 결합했다.
이것은 완전히 선언적인 접근 방식으로, 데이터를 UI 계층 구조로 변환하는 일련의 함수를 호출하여 UI를 설명한다.
기본 데이터가 변경되면 프레임워크가 이런 함수를 자동으로 다시 실행하여 UI 계층 구조를 업데이트한다.

Compose 앱은 Composable 함수로 구성된다. Composable 함수는 `@Composable` 이라는 주석이 달린 일반 함수이며
다른 Composable 함수를 호출할 수 있다. 새로운 UI 구성요소를 만들기 위해서는 Composable 함수만 있으면 된다.
이때 주석인 `@`은 지속적으로 UI를 업데이트하고 유지관리하기 위해 함수에 특수 지원을 추가하도록 Compose에 알려주는
역할을 한다.
> 대개 Composable 함수를 줄여서 Composable이라고 함.

> - `Composition`은 UI를 구성하는 기본 단위를 말함.<br>
> - Composable 함수가 호출되면 해당 요소가 Compose UI 트리에 추가되는데,
    Compose UI 트리를 구성하는 개별적인 UI 요소들이 서로 결합하여 하나의 UI 화면을 만드는 과정을 `Composition`이라고 부름.
> - Compose UI 트리를 구성하는 각각의 Composable 함수들을 `Composition`으로 볼 수 있음.

> 개발자가 작성한 Composable 함수를 `Component`라고 함.

재사용이 가능한 Composable을 만들면 앱에 사용하는 UI 요소의 라이브러리를 쉽게 빌드할 수 있다.
또한 각 요소는 화면의 한 부분을 담당하며 독립적으로 수정할 수 있다.

<br>

새 Compose 프로젝트를 시작하려면 **Empty Compose Activity(Material3)** 을 선택하면 Compose 관련 설정을 해준다.

setContent 내에서 사용되는 앱 테마는 프로젝트 이름에 맞게 지정된다.
Theme.kt를 찾아보면 프로젝트 이름과 일치하는 테마가 설정되어 있는 것을 확인할 수 있다.
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

Composable 함수는 `@Composable` 주석이 달린 일반 함수이다.
이 주석을 달면 함수가 내부에서 다른 Composable 함수를 호출할 수 있다.

Compose를 사용하면 Activity가 Android 앱의 진입점으로 유지된다.
`setContent`를 사용하여 레이아웃을 정의하는데, 기존 명령형 UI에서 XML 파일을 사용하는 것 대신에 Composable 함수를 호출한다.
```kotlin
    setContent {
        BasicsCodelabTheme {
        // A surface container using the 'background' color from the theme
            Surface(
              modifier = Modifier.fillMaxSize(),
              color = MaterialTheme.colorScheme.background
            ) {
                Greeting("Android")
            }
        }
    }
```

안드로이드 스튜디오에서 구현한 코드의 미리보기를 확인하고 싶다면 매개변수가 없는 Composable 함수 또는 기본 매개변수를 포함하는 함수를
`@Preview` 주석으로 표시하고 프로젝트를 빌드하면 된다.
> 참고로 동일한 파일에 미리보기를 여러 개 만들고 이름을 지정할 수 있다.

---

## 4. UI 조정(Tweaking the UI)

레이아웃의 배경 색상을 변경하려면 Composable 함수를 `Surface`로 래핑해야 한다.
```kotlin
    Surface(color = MaterialTheme.colorScheme.primary) {
        Text (text = "Hello $name!")
    }
```
Surface와 MaterialTheme는 [Material Design](https://m3.material.io/)과 관련된 개념으로 Material Design은
사용자 인터페이스와 기기 환경을 만드는(꾸미는) 데 도움을 주기 위해 구글에서 만든 디자인 시스템이다.
> 참고로 material을 import 할 때 버전을 확인하자. (예제에서는 material3으로 했음)


위 코드에서 Text는 Surface에 적용된 컬러로 표시된다.
이때 배경뿐만 아니라 텍스트가 같이 변경된 것을 볼 수 있는데, Material은 앱에 공통으로 적용되는 기본값과 패턴을 제공하기 때문이다.

<br>

Surface와 Text 같은 Compose UI 요소는 `modifier` 매개변수를 선택적으로 허용한다.
modifier는 상위 요소 레이아웃 내에서 UI 요소가 배치되고 표시되고 동작하는 방식을 UI 요소에 알려준다.
```kotlin
    Surface(color = MaterialTheme.colorScheme.primary) {
        Text(text = "Hello $name!", modifier = Modifier.padding(24.dp))
    }
```
modifier에는 정렬, 애니메이션 처리, 배치, 클릭 가능 여부, 스크롤 가능 여부, 변환 등 사용할 수 있는 수십 가지의 정보를 갖고 있다.

---

## 5. Composable 재사용(Reusing composables)

UI에 추가되는 Compose 요소가 많을수록 생성되는 중첩 레벨이 많이지기 때문에 가독성에 영향을 줄 수 있다.
이런 이유로 재사용할 수 있는 Compose 요소를 만들면 앱에서 사용하는 UI 요소의 라이브러리를 쉽게 만들 수 있고,
독립적으로 수정이 가능하다.

함수는 기본적으로 modifier를 사용하지 않아도 empty modifier를 매개변수로 포함하는 것이 좋다.
함수 내에서 호출하는 첫 번째 Composable로 modifier를 전달하면 함수 외부에서 레이아웃 안내와 동작을 조정할 수 있게 된다.
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

Compose의 세 가지 기본 표준 레이아웃 요소는 `Column`, `Row`, `Box` 이다.

Column은 내부의 각 하위 요소를 세로로 배치하는 레이아웃이고,<br>
Row는 내부의 각 하위 요소를 가로로 배치하는 레이아웃이다.<br>
> (box는 이번 Codelab에서 안 다루는 듯.)

<br>

Composable 함수는 코틀린의 다른 함수처럼 `for`문과 같은 구문을 추가할 수 있다.
예를 들어 for문에 UI 요소를 작성하여 연속적으로 UI를 그릴 수 있다.
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

Button은 material3 패키지에서 제공하는 Composable 이다.
버튼 블록에 UI 요소를 작성하여 버튼을 꾸미고, 후행 람다를 사용하여 이벤트 처리를 할 수 있다.

> Material에서 제공하는 버튼의 종류로 Button, ElevateButton, FilledTonalButton 등 여러 개가 있으니
  콘텐츠의 방향성에 맞춰 선택하자.

`weight` modifier는 요소를 유연하게 만들기 위해 가중치가 없는 다른 요소를 효과적으로 밀어내어 요소의 사용 가능한 모든 공간을 채운다.
> 이 modifier는 `fillMaxWidth`와 중복될 수 있다.

---

## 7. Compose의 상태(State in Compose)

Compose 앱은 Composable 함수를 호출하여 데이터를 UI로 변환한다.
데이터가 변경되면 Compose는 Composable 함수를 다시 실행하여 새로운 데이터로 업데이트된 UI를 만든다.
이것을 **리컴포지션(recomposition)** 이라고 하는데, 
Compose는 데이터가 변경된 Compose 요소만 다시 구성하고
영향을 받지 않는 요소는 다시 구성하지 않고 건너뛰도록 개별 Composable에서 필요한 데이터를 확인한다.
즉, 리컴포지션을 하기 위해서는 값을 탐지하고 있어야 한다.

Compose에서 변화하는 값을 기억하기 위해서 `state`를 사용해야 한다.
그래서 `mutableStateOf` 함수를 사용해서 state 정보를 탐지하는데,
mutableStateOf를 할당하기만 해서는 state가 유지되지 않는다.

리컴포지션 간에 state를 유지하기 위해서 `remember`까지 사용해야 한다.
```kotlin
    @Composable
    fun Greeting() {
        val expanded = remember { mutableStateOf(false) }
        ...
    }
```

---

## 8. State hoisting

Composable 함수에서 여러 함수가 읽거나 수정하는 state는 공통의 상위 항목에 위치해야 한다.
이 프로세스를 **State hoisting** 이라고 한다.
> *hoisting*은 들어 올리다, 끌어올리다 라는 뜻

state를 hoisting 할 수 있게 만들면 state가 중복되지 않고 버그가 발생하는 것을 방지할 수 있으며
Composable을 재사용할 수 있고 훨씬 테스트하기 쉬워진다.
> Composable의 상위 요소에서 제어할 필요가 없는 state는 hoisting되지 않도록 하자.

```kotlin
    var shouldShowOnboarding by remember { mutableStateOf(true) }
```
위 코드에서 `=` 대신 `by` 키워드를 사용했는데, 이렇게 사용하면 매번 `.value`를 쓸 필요가 없다.(속성 위임 개념)

Compose에서는 visibility 속성을 GONE으로 두는 등의 처리를 하여 UI 요소를 숨기지 않고,
Compose 항목에 UI 요소를 추가하지 않는 것으로 UI 트리에서 제외할 수 있다.

State를 다른 Composable 함수에서 사용해야 할 때 직접 전달하지 않고 콜백과 같은 이벤트로 전달하면
Composable의 재사용 가능성을 높이고 다른 Composable이 State를 변경하지 않도록 보호할 수 있다.

---

## 9.Creating a performant lazy list

스크롤이 가능한 열을 표시하기 위해서 `LazyColumn`을 사용한다. (행은 LazyRow)
LazyColumn은 RecyclerView와 동일하지만 하위 요소를 재활용하지 않는다.
하지만 스크롤을 할 때 새로운 Composable을 방출하는데, Composable을 방출하는 것은
Android Views를 인스턴스화하는 것보다 비용이 적게 들기 때문에 계속 성능을 유지할 수 있다.
> LazyColumn은 화면에 보이는 항목만 렌더링하기 때문에 리스트의 크기가 커도 성능이 떨어지지 않는다고 함.

```kotlin
    @Composable
    private fun Greetings(
        modifier: Modifier = Modifier,
        names: List<String> = List(1000) { "$it" }
    ) {
        LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
            items(items = names) { name ->
                Greeting(name = name)
            }
        }
    }
```

---

## 10. 상태 유지(Persisting state)

위에서 `remember`를 사용해서 state를 유지한다고 했는데 화면이 재구성 될 때는(화면 회전 등) 
Composable도 다시 시작되면서 모든 state가 손실되는 문제가 발생한다.
그 이유는 remember 함수는 Composable이 Composition에 유지되는 동안에만 작동하기 때문이다.

이럴 때 remember 함수를 사용하는 대신 `rememberSaveable`를 사용하면 된다.
rembmerSaveable은 state를 보존하여 앱이 재실행되거나 프로세스가 중단되어도 마지막으로 저장된 값을 사용할 수 있다.

---

## 11. 요소에 애니메이션 적용(Animating your list)

Compose에서는 여러 가지 방법으로 UI에 애니메이션을 지정할 수 있다.
간단한 애니메이션을 위한 상위 수준의 API에서 전체 제어 및 복잡한 전환을 위한 하위 수준의 메서드까지 다양한 방법이 있다.

<br>

```kotlin
    val extraPadding by animateDpAsState(
        if (expanded) 48.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
```

> 참고로 padding 값이 음수가 되면 앱이 비정상 종료가 될 수 있으니 조심해야 함.

그 중에서 `animateDpAsState` Composable은 애니메이션이 완료될 때까지
애니메이션에 의해 객체의 value가 계속 업데이트되는 state 객체를 반환한다.

animateDpAsState의 선택적 매개변수인 `animationSpec`은 애니메이션을 맞춤설정할 수 있다.
`spring` 기반의 애니메이션을 `animationSpec` 매개변수로 사용하여 Bouncy 효과를 주는 애니메이션을 추가할 수 있다.
> spring은 시간과 관련된 매개변수를 사용하지 않음.

`animate~AsState`를 사용하여 만든 애니메이션은 중단될 수 있다.
즉, 중간에 목표 값이 변경되면 애니메이션을 다시 시작하고 새 값을 가리킨다.
> spring 기반의 애니메이션은 중단이 되었을 때 자연스럽게 보이는 특징이 있음.

---

## 12. 앱의 스타일 지정 및 테마 설정(Styling and theming your app)

`MaterialTheme`은 [Material 디자인 사양](https://m3.material.io/)의 스타일 지정 원칙을 반영한 Composable 함수이다.

```kotlin
@Composable
    fun BasicsCodelabTheme(
        darkTheme: Boolean = isSystemInDarkTheme(),
        // Dynamic color is available on Android 12+
        dynamicColor: Boolean = true,
        content: @Composable () -> Unit
    ) {
        // ...
    
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
```

스타일 지정 정보는 `content`의 내부에 있는 구성요소가 부모에서 자식으로 흐른다.
즉 부모 컴포넌트에서 정의한 스타일 정보는 하위 컴포넌트가 자신을 스타일링하는데 사용된다.

위에서 작성한 BasicsCodelabTheme를 사용하는 Composable 함수는 그 하위 Composition에 똑같이 적용되며
필요에 따라 수정을 할 수 있다.

<br>

MaterialTheme의 세 가지 속성 `colorScheme`, `typography`, `shapes`를 사용해서 스타일링을 할 수 있다.
- **colorScheme**<br>
  Material Design의 색상 팔레트의 일부를 나타내며 사용자 지정 색상을 정의하는데 사용 됨.<br>
  Primary, Secondary, Surface, Background 등의 색상에 사용됨.
- **typography**<br>
  Material Design의 텍스트 스타일을 정의함.
- **shape**<br>
  Material Design의 라운드와 같은 모양을 정의함.

예를 들어 Text() Composable 함수에 `MaterialTheme.typography`를 사용해서 텍스트 스타일을 변경할 수 있다.
```kotlin
    Text(text = name, style = MaterialTheme.typography.headlineMedium)
```

<br>

미리보기(Preview)에서 `uiMode = UI_MODE_NIGHT_YES`를 사용하면 다크 모드 미리보기가 가능하다.
```kotlin
    @Preview(
        showBackground = true,
        widthDp = 320,
        uiMode = UI_MODE_NIGHT_YES,
        name = "Dark"
    )
```

<br>

`ui/theme` 폴더에서 Color.kt 파일이 존재하고, 이곳에 새로운 색상을 정의해서 사용할 수 있다.

---

## 13. 설정 완료(Finishing touches!)

```kotlin
implementation "androidx.compose.material:material-icons-extended:$compose_version"
```
를 등록하면 `materail-icons-extended`를 사용할 수 있다. (아이콘 사용 가능)

```kotlin
    IconButton(onClick = { expanded = !expanded }) { 
        Icon(
            imageVector = if(expanded) Filled.ExpandLess else Filled.ExpandMore,
            contentDescription = if(expanded){
           stringResource(id = R.string.show_less)
        } else {
            stringResource(id = R.string.show_more)
        })
    }
```

<br>

string.xml에 등록한 문자열은 `stringResource(R.stirng.~)`을 사용해서 얻을 수 있다.

---

![basics codelab](https://user-images.githubusercontent.com/52282493/233921079-f6d323a0-c367-49dd-b5a8-7cbede178c1b.gif)