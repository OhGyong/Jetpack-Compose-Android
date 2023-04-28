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