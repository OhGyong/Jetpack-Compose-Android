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

## 5. 색상 사용
위에서 자체 테마를 만들어서 앱의 color, typography, shape를 설정하는 방법을 배웠다.
모든 Material Components는 기본적으로 이런 맞춤 설정을 사용하는데,
Components 별로 매개변수에 다른 값을 지정하여 대체 색상을 설정할 수 있다.

```kotlin
Surface(color = Color.LightGray) {
  Text(
    text = "Use Color",
    textColor = Color(0xffff00ff)
  )
}
```
Compose는 `Color` 클래스를 제공하며, 이를 `object` 등에 보관하여 사용할 수 있다.

<br>

```kotlin
Surface(color = MaterialTheme.colors.primary)
```
더 유연한 접근 방법은 Theme에서 색상을 가져오는 것이다.
위 코드에서는 color 속성을 MaterialThme에 설정된 color 값을 사용한다.
앱의 디자인과 분위기에 맞는 color를 사용할 수 있는 것이락 볼 수 있다.

<br>

```kotlin
val derivedColor = MaterialTheme.colors.onSurface.copy(alpha = 0.1f)
```
또한 `copy` 메서드를 이용하면 하드 코딩하지 않고 컬러 정보를 얻을 수 있으며,
`alpha` 속성을 부여하여 투명도를 적용할 수 있다.

### Surface와 contentColor
```kotlin
Surface(
  color: Color = MaterialTheme.colors.surface,
  contentColor: Color = contentColorFor(color),
  // ...

TopAppBar(
  backgroundColor: Color = MaterialTheme.colors.primarySurface,
  contentColor: Color = contentColorFor(backgroundColor),
  // ...
```
Composable은 한 쌍의 color와 contentColor를 지원한다.
Composable의 컬러는 color로 설정하고 그 안의 컨텐츠는(Text, Icon 등) contentColor를 사용한다.
`contenteColorFor` 메서드는 지정한 색상에 적절한 `on` 색상을 가져온다.

요소의 색상을 설정할 때는 `Surface`를 사용하는 것이 좋다.
적절한 컨텐츠 색상인 `CompositionLocal` 값을 설정하기 때문이다.
> CompositionLocal 뿐만 아니라 LocalContentColor도 현재 배경과 대비되는 색상을 가져온다.

### ContentAlpha
중요도를 전달하고 시각적 계층 구조를 제공하기 위해서 컨텐츠를 강조하는 경우가 있다.
Material Design에서는 다양한 수준의 불투명도를 사용하여 다양한 중요도 수준을 전달하도록 한다.

Jetpack Compose에서는 `LocalContentAlpha`를 사용하여 이를 구현할 수 있다.
`CompositionLocal` 값을 제공하여 계층 구조의 ContentAlpha를 지정할 수 있다.
Material에서는 ContentAlpha 객체에 의해 모델링된 일부 표준 값으로 high, medium, disabled이 있다.
> 참고로 MeterialTheme는 LocalContentAlpha의 기본값을 ConentAlpha.high으로 한다.

<br>

그런데 기존 Material에서 Material3로 넘어가면서 ContentAlpha와 LocalContentAlpha는 없어졌다.
대신 fontWeight에 값을 적용하는 것으로 대체한다.
```kotlin
// 기존 Material
CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
    Text(...)
}
CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.disabled) {
    Icon(...)
    Text(...)
}
```

```kotlin
// Material3
Text(
    modifier = modifier.padding(horizontal = 16.dp),
    text = text,
    style = MaterialTheme.typography.bodySmall,
    fontWeight = FontWeight.Normal
)
```
기존 Material의 ContentAlpha 값은 Material3에서 아래 처럼 대체된다.
- ContentAlpha.high -> FontWeight.Medium 또는 FontWeight.Black
- ContentAlpha.medium -> FontWeight.Thin 또는 FontWeight.Normal
- ContentAlpha.disabled -> onSurface.copy(alpha = 0.38f)

### TopAppBar
Material3에서는 기존의 backgroundColor가 사라지고 TopAppBarColors가 생겼다.([참고](https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary#TopAppBar(kotlin.Function0,androidx.compose.ui.Modifier,kotlin.Function0,kotlin.Function1,androidx.compose.foundation.layout.WindowInsets,androidx.compose.material3.TopAppBarColors,androidx.compose.material3.TopAppBarScrollBehavior)))
```kotlin
@Composable
fun AppBar() {
    TopAppBar(
        navigationIcon = {
            Icon(
                imageVector = Icons.Rounded.Home,
                contentDescription = null,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        },
        title = {
            Text(text = stringResource(id = R.string.app_name))
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    )
}
```
`largeTopAppBarColors`는 TopAppBar의 크기가 큰 경우의 색상으로 네비게이션 아이콘 또는 액션 아이콘 등이 있을 때 적합하다.

---

## 6. 텍스트 사용
단순 글을 보여줄 때는 `Text` Composable을 사용하고 `TextField` 또는 `OutlinedTextField`를 텍스트 입력에 사용한다.
`TextStyle`을 사용해서 텍스트에 단일 스타일을 적용할 수 있으며 `AnnotatedString`을 사용하여 텍스트에 여러 스타일을 적용할 수 있다.

<br>

```kotlin
@Composable
fun Button(
    // many other parameters
    content: @Composable RowScope.() -> Unit
) {
  ...
  ProvideTextStyle(MaterialTheme.typography.button) { //set the "current" text style
    ...
    content()
  }
}

@Composable
fun Text(
    // many, many parameters
    style: TextStyle = LocalTextStyle.current // get the value set by ProvideTextStyle
) { ...
```
`ProvideITextStyle` Composable을(CompositionLocal) 사용하여 현재 TextStyle을 설정할 수 있으며,
자식 Composable에게 텍스트 스타일을 제공할 수 있다.
그리고 `LocalTextStyle.current`를 통해 ProvideTextStyle로 설정한 현재 TextStyle을 사용할 수 있다.

### 테마의 텍스트 스타일
color와 마찬가지로 애플리케이션에 일관되면서도 유지 관리를 위해 현재 테마에서 TextStyle을 가져와 쓰는 것이 가장 좋다.
MaterialTheme의 typography를 통해 개발자가 정의한 스타일을 적용하여 사용하면 된다.

```kotlin
Text(
    text = "Hello World",
    style = MaterialTheme.typography.body1.copy(
        background = MaterialTheme.colors.secondary
    ),
    fontSize = 22.sp // explicit size overrides the size in the style
)
```
만약 맞춤 설정이 필요하면 `copy`를 통해 속성을 재정의하거나 스타일 지정 매개변수를(font) 사용한다.

### AnnotatedString
일부 텍스트에 여러 스타일을 적용해야 하는 경우 마크업을 적용하는 `AnnotatedString` 클래스를 사용한다.
AnnotatedString을 사용하면 `SpanStyle`을 텍스트 범위에 추가할 수 있다.

```kotlin
val tagStyle = MaterialTheme.typography.overline.toSpanStyle().copy(
   background = MaterialTheme.colors.primary.copy(alpha = 0.1f)
)
```
기존 Material에서는 overline 속성을 사용할 수 있었으나 Material3 부터는 overline을 지원하지 않는다.
따라서 아래와 같이 작성해야 한다.
```kotlin
val tagStyle = TextStyle(
    background = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
).toSpanStyle()
```

<br>

```kotlin
val text = buildAnnotatedString {
    append(post.metadata.date)
    append(" • ")
    append("${post.metadata.readTimeMinutes}min")
    append(" • ")
    post.tag.forEach { tag->
        withStyle(tagStyle) {
            append(" ${tag.uppercase(Locale.ROOT)} ")
        }
        append(" ")
    }
}
```
`buildAnnotatedString`로 텍스트에 여러 개의 스타일을 혼합하는 등의 작업을 수행할 수 있다.
buildAnnotatedString 블록에 `append`를 사용하여 텍스트를 추가하고 `withStyle`을 통해
원하는 텍스트에 스타일을 적용할 수 있다.

---

## 7. 도형 사용
color, typography와 마찬가지로 Shpae도 MaterialTheme에 적용할 수 있다.

```kotlin
@Composable
fun FilledTextField(
  // other parameters
  shape: Shape = MaterialTheme.shapes.small.copy(
      bottomStart = ZeroCornerSize, // overrides small theme style
      bottomEnd = ZeroCornerSize // overrides small theme style
  )
) {
```
만약 테두리를 변경하고 싶은 경우 shape 속성의 원하는 부분에 CornerSize를 조절하면 된다.

<br>

```kotlin
Image(
    painter = painterResource(id = post.imageThumbId),
    contentDescription = null,
    contentScale = ContentScale.Crop,
    modifier = Modifier
        .width(60.dp)
        .height(60.dp)
        .padding(10.dp)
        .clip(shape = CutCornerShape(topStart = 8.dp))
)
```
`Modifier.clip`은 요소의 영역을 조절하는데 사용된다.
`CutCornerShape`로 요소의 일부를 잘라내고 clip의 shape에 적용한다.

---
수정 전
<img width="470" alt="스크린샷 2023-05-17 오후 2 15 42" src="https://github.com/OhGyong/Jetpack-Compose-Android/assets/52282493/ca0ae7ff-904a-4d50-9663-bf54ef6903fb">

<br>

수정 후

![light_theming](https://github.com/OhGyong/Jetpack-Compose-Android/assets/52282493/eb5070c1-de7a-4704-99af-cb39da088319)
![dark_theming](https://github.com/OhGyong/Jetpack-Compose-Android/assets/52282493/0dfdfb47-6fe6-433e-ae43-5b0c0789f012)
