# [Jetpack Compose Theme Settings](https://developer.android.com/codelabs/jetpack-compose-theming?hl=ko&continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fjetpack-compose-for-android-developers-1%3Fhl%3Dko%23codelab-https%3A%2F%2Fdeveloper.android.com%2Fcodelabs%2Fjetpack-compose-theming#0)
이 프로젝트는 android-compose-codelabs의 [ThemingCodelabM2](https://github.com/googlecodelabs/android-compose-codelabs/tree/main/ThemingCodelabM2) 프로젝트를 바탕으로 Codelab에 맞춰 진행하였습니다.

---

## 1~2 프로젝트 소개
이 codelab에서는 Material Design 시스템을 구현하는 방법을 익힌다.
Jetpack Compose의 테마 설정 API를 사용하여 앱 요소들에 색상, 스타일, 도형을 정의하고
일반 모드와 다크 모드일 때 테마를 적용하는 방법도 배울 것이다.

---

## 3. Material Theming
Jetpack Compose는 디지털 인터페이스를 만들기 위한 포괄적인 디자인 시스템인 [Material Design](https://m2.material.io/design/introduction/)을 제공한다.
[Material Design Components](https://m3.material.io/components)는 Material Design을 커스텀 할 수 있는
[Material Theming](https://m2.material.io/design/material-theming/implementing-your-theme.html#using-material-theming)을
기반으로 빌드된다.
Material Theming은 `Color`, `Typography`, `Shape` 속성으로 구성된다.

### Color
Material Design은 여러 색상을 정의한다.
- `Primary`<br>
주요 컨텐츠의 색상으로 앱의 타이틀이나 액센트 요소에 사용된다.
- `Secondary`<br>
보조 요소에 사용되는 색상으로 버튼이나 하이라이트같은 Primary와 대비되는 영역에 더 어둡거나 밝은 변형을 제공한다.
- `Background`<br>
화면의 배경 색상으로 일반적으로 컨텐츠 영역을 둘러싼다.
- `Surface`<br>
Background와 대비하여 컨텐츠를 구분하는 색상이다.

또한 `On Primary`, `On Secondary`, `On Background`, `On Surface`와 같이 Material은 `on` 색상을 정의한다.
위에서 언급한 색상 위에 표시되는 텍스트나 아이콘 등의 컨텐츠에 사용되는 색상으로 대비를 주어 가시성을 높인다.

### Typography
테마별로 Typography 스타일을 변경할 수는 없지만 Material의 Typography를 사용하면 애플리케이션 내에서의 일관성을 높일 수 있다.

### Shape
Material은 Shape를 체계적으로 사용하여 브랜드를 전달할 수 있도록 지원한다.
소형, 중형, 대형이라는 3가지 카테고리를 정의하고, 각각 모서리 스타일(둥글게 또는 자르기)과 크기를 맞춤설정 할 수 있다.

---

## 4. 테마 정의
```kotlin
@Composable
fun MaterialTheme(
    colors: Colors,
    typography: Typography,
    shapes: Shapes,
    content: @Composable () -> Unit
) { ...
```
`MaterialTheme` Composable은 Jetpack Compose에서 테마 설정을 구현하는 핵심 요소이다.
MaterialTheme는 Compose 계층 구조에 배치하면 그 안의 모든 요소의 color, secondary, surface를 맞춤 설정할 수 있다.
아래와 같이 MaterialTheme를 정의할 수 있다.

<br>

```kotlin
@Composable
fun JetnewsTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        content = content
    )
}
```
MaterialTheme를 래핑하는 자체 Composable을 만들면 여러 화면 또는 `@Preview` 등 여러 위치에서 쉽게 사용할 수 있게 된다.

### Color
```kotlin
// Color.kt
val Red700 = Color(0xffdd0d3c)
val Red800 = Color(0xffd00036)
val Red900 = Color(0xffc20029)
```

```kotlin
// Theme.kt
private val LightColors = lightColorScheme(
    primary = Red700,
    onPrimary = Color.White,
    secondary = Red700,
    onSecondary = Color.White,
    error = Red800
)
```
대게 theme 패키지에 Color.kt, Theme.kt, Type.kt가 존재하고 테마, 색상, 스타일 등을 맞춤 설정한다.
Color.kt나 Type.kt에서 정의한 내용을 Theme.kt로 불러와서 테마에 적용한다.

lightColorScheme는 라이트 모드에 대한 색상 값을 포함하고 있다.
> material3부터 기존의 lightColor이 없어지고 lightColorScheme가 생겼다.(darkColor도 마찬가지)

### Typography
Material3로 업데이트되면서 기존 Material에서 제공하는 Typography 클래스에 비해 직관성을 높였다.
기존에는 h1, body1과 같은 이름을 썼다면 Material3에서는 bodyLarge, titleLarge와 같은 이름을 사용한다.

```kotlin
// 기존 Material
val JetnewsTypography = Typography(
    h4 = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.W600,
        fontSize = 30.sp
    ),
    h5 = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.W600,
        fontSize = 24.sp
    ),
    // ...
)

// Material3
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    // ...
)
```
Type.kt 또는 Typography.kt에 스타일을 정의하여 Theme에 적용할 수 있다.

### Shape
```kotlin
val JetnewsShapes = Shapes(
    small = CutCornerShape(topStart = 8.dp),
    medium = CutCornerShape(topStart = 24.dp),
    large = RoundedCornerShape(8.dp)
)
```
Compose는 도형 테마를 정의하는 데 사용할 수 있는 `RoundedCornerShape` 및 `CutCornerShape` 클래스를 제공한다.
theme 패키지에 Shape.kt 파일을 만들고 위 코드처럼 도형을 정의한다.

### DarkTheme
DarkTheme일 때 맞춤 Theme를 설정하는 방법은 현재 기기가 다크 모드인지만 확인하면 된다.

```kotlin
@Composable
fun JetnewsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if(darkTheme) DarkColors else LightColors,
        content = content,
        typography = JetnewsTypography,
        shapes = JetnewsShapes
    )
}
```
LightColors를 설정했을 때 처럼 다크 모드일 때 표현할 color를 정의하고
`isSystemInDarkTheme()`를 사용하여 현재 다크 모드인지 확인하여 해당하는 color를 적용하면 된다.


---

<img width="470" alt="스크린샷 2023-05-17 오후 2 15 42" src="https://github.com/OhGyong/Jetpack-Compose-Android/assets/52282493/ca0ae7ff-904a-4d50-9663-bf54ef6903fb">