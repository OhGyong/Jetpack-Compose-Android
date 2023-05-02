[Migrating to Jetpack Compose](https://developer.android.com/codelabs/jetpack-compose-migration?continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fcompose%3Fhl%3Dko#0)

이 프로젝트는 [sunflower](https://github.com/android/sunflower/tree/views)를 바탕으로 Codelab에 맞춰 진행되었습니다. 

## 1~3. Codelab 목표 및 설(Introduction, Migration strategy, Getting set up)
Jetpack Compose는 처음부터 뷰 상호 운용성을 고려하여 설계되었다.
Compose로 이전하려면 앱이 Compose로 완전히 이전될 때까지
Compose와 View가 코드베이스에 공존하는 **증분 이전**을 사용하는 것이 좋다.

권장되는 [이전 전략](https://developer.android.com/jetpack/compose/interop/migration-strategy?hl=ko)은 아래와 같다.

### 1. Compose를 사용하여 새로운 기능 구현
Compose를 사용하여 새로운 기능을 구현하는 것은 Compose를 채택하는 가장 좋은 방법이다.
이 방법으로 추가된 새로운 기능은 Compose의 이점을 활용할 수 있다.

구현할 기능이 화면 전체에 영향을 주는 경우 Compose로 화면 전체를 구현하는 것이 좋고,
구현할 기능이 일부 화면인 경우 Compose와 View가 동일한 화면에 공존하도록 구현한다.

### 2. 기능을 구현하면서 재사용 가능한 요소를 식별하고 공통 UI Components 라이브러리 만들기
Compose를 사용하여 기능을 구현하다보면 결국 Components 라이브러리를 빌드하게 된다.
때문에 단일 정보 소스를 지니는 공유 Components 형태로 만들어서 앱 전체에서 재사용을 촉진할 수 있는 라이브러리로 만드는 것이 좋다.
> Compose는 블록을 조립하듯이 UI를 빌드하는데, 개발자가 Composable 함수를 조합하여 구현한 것을 `Components 라이브러리`라고 할 수 있음.

### 3. 기존 기능을 한 번에 한 화면씩 대체
Compose를 사용하여 새로운 기능을 구현하는 것 외에도 기존 기능을 점진적으로 이전하는 것이 좋다.
접근하는 방식은 개발자가 선택할 수 있지만 적합한 옵션은 아래와 같다.
1. **간단한 화면**<br>
   일부 UI 요소와 시작 화면, 확인 화면, 설정 화면 같은 역동성이 있는 앱의 간단한 화면 등 코드 몇 줄로 작성할 수 있는
   부분은  Compose로 이전하는게 더 적합하다.
2. ** View와 Compose가 혼합된 화면**
   이미 Compose가 섞여있는 화면은 요소를 조금씩 Compose로 이전하면 좋다.

<br>

이 Codelab에서는 이미 구현된 식물 세부정보 화면은 Compose로 점진적으로 이전할 것이다.

---

## 4. Compose in Sunflower
프로젝트에서 Compose를 사용할 수 있도록 build.gradle(app)에서 다음과 같이 설정을 해준다.
```kotlin
android {
    //...
    kotlinOptions {
        jvmTarget = '1.8'
    }
    buildFeatures {
        //...
        compose true
    }
    composeOptions {
        kotlinCompilerExtensionVersion '1.3.2'
    }
}

dependencies {
    //...
    // Compose
    def composeBom = platform('androidx.compose:compose-bom:2022.10.00')
    implementation(composeBom)
    androidTestImplementation(composeBom)

    implementation "androidx.compose.runtime:runtime"
    implementation "androidx.compose.ui:ui"
    implementation "androidx.compose.foundation:foundation"
    implementation "androidx.compose.foundation:foundation-layout"
    implementation "androidx.compose.material:material"
    implementation "androidx.compose.runtime:runtime-livedata"
    implementation "androidx.compose.ui:ui-tooling"
    implementation "com.google.accompanist:accompanist-themeadapter-material:0.28.0"
    //...
}
```

---

## 5. Compose 시작
Compose에서 UI를 렌더링하려면 Activity 또는 Fragment가 필요하다.
Sunflower에서는 모든 화면이 Fragment를 사용하기 때문에 `setContent` 메서드를 사용하여 Compose UI 콘텐츠를
호스팅할 수 있는 Android View인 `ComposeView`를 사용한다.
> 기존 XML 파일에서 Compose로 구현할 부분과 겹치는 요소들을 삭제하고, ComposeView를 추가하자.

```xml
<androidx.compose.ui.platform.ComposeView
        android:id="@+id/compose_view" 
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
```

```kotlin
override fun onCreateView(...): View? {
   val binding = DataBindingUtil.inflate<FragmentPlantDetailBinding>(
      inflater, R.layout.fragment_plant_detail, container, false
   ).apply {
      // ...
      composeView.setContent {
         MaterialTheme {
            PlantDetailDescription() // Composable이 정의되어있는 파일
         }
      }
   }
   // ...
}    
```

---

## 6. XML의 내용을 Composable로 만들기(Creating a Composable out of XML)

```xml
<TextView
   
   android:id="@+id/plant_detail_name"
   android:layout_width="0dp"
   android:layout_height="wrap_content"
   android:layout_marginStart="8dp"
   android:layout_marginEnd="8dp"
   android:gravity="center_horizontal"
   android:text="@{viewModel.plant.name}"
   android:textAppearance="?attr/textAppearanceHeadline5"
   ... />             
```

```kotlin
@Composable
private fun PlantName(name: String) {
    Text(
        text = name,
        style = MaterialTheme.typography.h5,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}
```

- `MaterialTheme.typography.h5`는 XMl 코드의 `android:textAppearance="?attr/textAppearanceHeadline5`
   와 유사하다.
- Modifier의 `fillMaxWidth()`는 사용 가능한 최대 너비를 차지하도록 하여 XML 코드의 `android:layout_width="0dp"`를
  대체한다.
- Modifier의 `padding()`을 사용하여 XML 코드의 margin을 부여했다. 참고로 Compose는 dimensionRecource(id)를 통해
  dimens.xml의 파일 값을 쉽게 가져올 수 있다.
- Modifier의 `wrapContentWidth()`는 텍스트가 가로로 화면 가운데 표시되도록 한다.
  XML 코드의 `android:gravity="center_horizontal"`와 유사하다.

---

## 7. ViewModel 및 LiveData
Compose는 ViewModel 및 LiveData 통합 기능을 지원한다.

### ViewModel
ViewModel의 인스턴스를 Composable에 매개변수로 전달하여 사용하면 된다.

주의할 점은 화면 수준의 Composable에서만 ViewModel을 참조하도록 하고,
하위 요소는 ViewModel을 참조하는 것이 아닌 데이터만 전달할 수 있도록 한다.
```kotlin
@Composable
fun PlantDetailDescription(plantDetailViewModel: PlantDetailViewModel) {
    //...
}
```

### LiveData
Composable에서 매개변수로 ViewModel의 인스턴스에 접근했다면, LiveData 필드에 액세스하여 데이터를 가져온다.
LiveData를 관찰하려면 `LiveData.observeAsState()`를 사용한다.

observeAsState()는 LiveData 관찰을 시작하고 값을 State 객체로 표현한다. LiveData에 새로운 값이 탐지될 떄마다
반환된 State가 업데이트되어 State.value를 사용하는 모든 부분이 재구성된다.

그리고 LiveData에서 관찰된 값은 null일 수 있기 때문에 null 검사로 래핑해야 한다.
null 검사 등 재사용성을 높이기 위해 LiveData 소비를 분할하고 여러 하위 Composable에서 접근하도록 한다.
```kotlin
@Composable
fun PlantDetailDescription(plantDetailViewModel: PlantDetailViewModel) {
    val plant by plantDetailViewModel.plant.observeAsState()
    /**
     * - LiveData에서 관찰한 값은 null 일 수 있기 때문에 null 검사로 래핑.
     * - null 검사 등 재사용성을 높이기 위해 LiveData 소비를 분할하여 여러 하위 Composable에서 접근하게 함.
     */
    plant?.let {
        PlantDetailContent(plant = it)
    }
}

@Composable
fun PlantDetailContent(plant: Plant) {
    PlantName(name = plant.name)
}
```

> 참고로 Compose에서는 `androidx.compose.runtime.getValue`를 제공하여 속성 위임(`by`)이 가능하다.

---

## 8. 나머지 XML 코드 부분 이전하기(More XML code migration)

```xml
<TextView
    android:id="@+id/plant_watering_header"
    android:layout_width="0dp"
    android:layout_height="wrap_content"
    android:layout_marginStart="@dimen/margin_small"
    android:layout_marginTop="@dimen/margin_normal"
    android:layout_marginEnd="@dimen/margin_small"
    android:gravity="center_horizontal"
    android:text="@string/watering_needs_prefix"
    android:textColor="?attr/colorAccent"
    android:textStyle="bold"
    app:layout_constraintEnd_toEndOf="parent"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintTop_toBottomOf="@id/plant_detail_name" />

<TextView
    android:id="@+id/plant_watering"
    android:layout_width="0dp"
    android:layout_height="wrap_content"
    android:layout_marginStart="@dimen/margin_small"
    android:layout_marginEnd="@dimen/margin_small"
    android:gravity="center_horizontal"
    app:layout_constraintEnd_toEndOf="parent"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintTop_toBottomOf="@id/plant_watering_header"
    app:wateringText="@{viewModel.plant.wateringInterval}"
    tools:text="every 7 days" />
```

기존 XML에서 plant_detail_name 밑에 두 개의 TextView가 세로로 정렬되어 있다.
이를 Composable로 구현하면 아래와 같다.
```kotlin
@Composable
fun PlantDetailContent(plant: Plant) {
    Surface {
        Column(Modifier.padding(dimensionResource(id = R.dimen.margin_normal))) {
            PlantName(name = plant.name)
            PlantWatering(wateringInterval = plant.wateringInterval)
        }
    }
}

@Composable
private fun PlantName(name: String) {
    Text(
        text = name,
        style = MaterialTheme.typography.h5,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.margin_small))
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}

@Composable
private fun PlantWatering(wateringInterval: Int) {
    Column(Modifier.fillMaxWidth()) {
        val centerWithPaddingModifier = Modifier
            .padding(horizontal = dimensionResource(id = R.dimen.margin_small))
            .align(Alignment.CenterHorizontally)

        val normalPadding = dimensionResource(id = R.dimen.margin_normal)

        Text(
            text = stringResource(id = R.string.watering_needs_prefix),
            color = MaterialTheme.colors.primaryVariant,
            fontWeight = FontWeight.Bold,
            modifier = centerWithPaddingModifier.padding(top = normalPadding)
        )

        val wateringIntervalText = pluralStringResource(
            id = R.plurals.watering_needs_suffix, count = wateringInterval, wateringInterval
        )

        Text(
            text = wateringIntervalText,
            modifier = centerWithPaddingModifier.padding(bottom = normalPadding)
        )
    }
}
```

두 개의 TextView가 세로로 정렬되어 있기 때문에 `Column()`으로 래핑하고 공통으로 쓰이는 padding 속성들을 변수로 만들어서 사용하였다.

---

## 9. Compose에서 Html 적용하기(Views in Compose code)
XML로 뷰를 그릴 때 TextView에 `app:renderHtml`이라는 속성으로 HTML 문자열을 렌더링할 수 있게 한다.
그러나 Compose는 `Spanned` 클래스와 HTML을 렌더링 할 수 있는 속성을 지원하지 않는다.

그래서 Compose는 `AndroidView`의 API를 사용하여 programmatically하게 TextView를 만들어야 한다.
AndroidView를 사용하면 Factory 람다에 View를 구성할 수 있다.
또한 View가 확장되었을 때 및 후속 재구성 시 호출될 때 `update` 람다를 제공한다.

```kotlin
@Composable
private fun PlantDescription(description: String) {
    val htmlDescription = remember(description) {
        HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_COMPACT)
    }

    AndroidView(
        factory = { context->
            TextView(context).apply {
                movementMethod = LinkMovementMethod.getInstance()
            }
        },
        update = {
            it.text = htmlDescription
        }
    )
}
```

---

## 10. ViewCompositionStrategy
Compose는 `ComposeView`가 창에서 분리될 때마다 `Composition`을 삭제한다.
다음 두 가지 이유로 Fragment에서 ComposeView가 사용될 때 바람직하지 못하다.
- Composition은 Compose UI View 유형을 위한 Fragment 생명 주기에 따라 state를 저장해야 함.
- 화면 전환이 발생할 때 기본 ComposeView가 분리된 상태가 되는데, 이런 전환 중에도 Compose UI 요소는 계속 표시됨.
> 추가적으로 설명하자면 Fragment에서 화면 전환이 되었을 때, 기존 ComposeView는 분리되어 새로운 ComposeView가 생성된다.
> 이 경우 이전에 그렸던 Compose UI 요소들이 모두 사라지고, 새로운 ComposeView에 새롭게 그려지게 되는 문제가 있다.

이 문제를 해결하려면 Fragment의 생명 주기를 따르도록 `ViewCompositionStrategy`의 `setViewCompositionStrategy`를 호출한다.
특히 `DisposeOnViewTreeLifecycleDestroyed`를 사용하면 Fragment의 `LifecycleOwner`가 소멸될 때 `Composition`을 삭제한다.
> onStop 또는 onDestroy와 같은 생명 주기 이벤트가 발생했을 때View의 Compose 구성 요소가 폐기되도록 함.
 
 ```kotlin
override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
): View {
    val binding = DataBindingUtil.inflate<FragmentPlantDetailBinding>(
        inflater,  R.layout.fragment_plant_detail, container, false
    ).apply {
        composeView.apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )
            setContent {
                MaterialTheme {
                    PlantDetailDescription(plantDetailViewModel)
                }
            }
        }
        // ...
    }
    // ...
}
```

---

## 11. Compose 요소마다 테마 재사용하기(Interop theming)
Compose에서 View system MDC(Material Design Components) 테마를 재사용하려면
[Accompanist Material 테마 어댑터](https://github.com/google/accompanist/tree/main/themeadapter-material)
라이브러리를 사용하면 된다.
`MdcTheme` 함수는 호스트 Context의 MDC 테마를 자동으로 읽고 사용자를 대신하여 밝은 테마와 어두운 테마 모두를 위해
`MaterialTheme`로 전달한다.

```kotlin
implementation "com.google.accompanist:accompanist-themeadapter-material:$rootProject.accompanistVersion"
```
위의 코드를 build.gradle(app)에 등록하여 MdcTheme를 사용할 수 있도록 한다.

```kotlin
setContent {
    MdcTheme {
        PlantDetailDescription(plantDetailViewModel)
    }
}
```
MaterialTheme를 MdcTheme로 변경한다.

---

## Compose에서의 Test(Testing)
Activity 또는 Fragment에서 Compose를 사용한다면 `ActivityScenarioRule`을 사용하는 대신
`ComposeTestRule`과 `ActivityScenarioRule`을 통합한 `createAndroidComposeRule`을 사용해야 한다.

`ActivityScenarioRule`은 `createAndroidComposeRule`로 변경하고, 테스트를 구성하는데 Activity 규칙이필요한 경우
`createAndroidComposeRule`의 `activityRule` 속성을 사용한다.

View가 화면에 표시되는지 확인하기 위해서 `Compose assertions`를 사용한다.

```kotlin
@RunWith(AndroidJUnit4::class)
class PlantDetailFragmentTest {

    @Rule
    @JvmField
    val composeTestRule = createAndroidComposeRule<GardenActivity>()

    // ...
    
    @Before
    fun jumpToPlantDetailFragment() {
        // ...
        composeTestRule.activityRule.scenario.onActivity {
            // ...
        }
    }

    @Test
    fun testPlantName() {
        composeTestRule.onNodeWithText("Apple").assertIsDisplayed()
    }

    // ...
}
```