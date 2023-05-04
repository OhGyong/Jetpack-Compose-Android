# [Basic layouts in Compose](https://developer.android.com/codelabs/jetpack-compose-layouts?hl=ko&continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fcompose%3Fhl%3Dko%23codelab-https%3A%2F%2Fdeveloper.android.com%2Fcodelabs%2Fjetpack-compose-layouts#0)

이 프로젝트는 android-compose-codelabs의 [BasicLayoutsCodelab](https://github.com/googlecodelabs/android-compose-codelabs/tree/main/BasicLayoutsCodelab) 프로젝트를 바탕으로 Codelab에 맞춰 진행하였습니다.

## 1~3. 프로젝트 소개, 설정, 계획
UI 도구 키트인 Compose를 사용하면 앱 디자인을 쉽게 구현할 수 있다.
개발자가 UI의 디자인을 기술하면 Compose가 화면에 그리는 작업을 처리한다.
이 codelab에서는 앞에서 배운 것 보다 현실적이고 복잡한 레이아웃을 구현해볼 것이다.

앱을 개발하기 위한 디자인을 받았을 때 먼저 디자인의 구조를 명확하게 파악하는 것이 좋다.
> 디자인을 분석하여 UI를 여러 개의 재사용 가능한 부분으로 나누는 등의 고민을 해보자.

---

## 4. 검색창 만들기(Search bar - Modifiers)
Compose Material 라이브러리의 `TextField`라는 Composable을 사용하면 검색창을 만들 수 있다.

```kotlin
TextField(
    value = "",
    onValueChange = {},
    leadingIcon = {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null
        )
    },
    colors = TextFieldDefaults.textFieldColors(
        backgroundColor = MaterialTheme.colors.surface
    ),
    placeholder = {
        Text(stringResource(R.string.placeholder_search))
    },
    modifier = modifier
        .fillMaxWidth()
        .heightIn(min = 56.dp)
)
```
- TextField 내부에 아이콘을 삽입하려면 `leadingIcon`을 사용한다.
  contentDescription을 null로 설정한 이유는 `placeholder`에서 이미 TextField에 대한 설명을 하고 있기 때문이다.
- TextField의 배경색을 MaterialTheme의 `surface`와 같은 색으로 하려면 `TextFieldDefaults.textFieldColors`
  를 사용하여 특정 색상을 재정의한다.
- `heightIn`은 최소 높이를 지정한다. 
  최소 높이를 지정한 것이기 때문에 시스템의 글꼴과 같은 설정을 확대하면 높이가 늘어날 수 있다.(권장되는 방식이라 함.)
- `fillMaxWidth`은 상위 요소의 전체 가로 공간을 차지하도록 지정한다.
> TextField는 XMl의 EditText와 유사하다.

---

## 5. Image, Alignment에 대해서(Align your body - Alignment)
이미지를 삽입하기 위해서 `Image` Composable을 사용한다.
```kotlin
Image(
    painter = painterResource(R.drawable.ab1_inversions),
    contentDescription = null,
    contentScale = ContentScale.Crop,
    modifier = Modifier
        .size(88.dp)
        .clip(CircleShape)
)
```
- `paintResource`는 Compose에서 이미지를 로드한다.
- `contentScale`은 이미지의 크기와 컨테이너 사이의 크기를 조절한다.
- `Modifier.shape`은 fillMaxWidth와 heightIn처럼 Composable의 크기를 조정한다.
- `Modifier.clip`은 Composable의 모양을 조정한다.

<br>

상위 컨테이너에서 하위 Composable을 정렬하려면 상위 컨테이너에서 `alignment`를 설정한다.
```kotlin
Column(
  horizontalAlignment = Alignment.CenterHorizontally,
  modifier = modifier
) {
  Image(
    //..
  )
  Text(
    //..
  )
}   
```

정렬 속성은 아래와 같다.

- Coloumn
  - Start
  - CenterHorizontally
  - End
- Row
  - Top
  - CenterVertically
  - Bottom
- Box
  - TopStart
  - TopCenter
  - TopEnd
  - CenterStart
  - Center
  - CenterEnd
  - BottomStart
  - BottomCenter
  - BottomEnd

---

## 6. Material Surface 활용하기(Favorite collection card - Material Surface)
화면의 배경과 화면 내부의 컨테이너 배경이 다른 색으로 적용되어 있는 경우가 있다.
이럴 때는 `Surface` Composable을 사용해서 테마를 조정해 볼 수 있다.
```kotlin
Surface(
  shape = MaterialTheme.shapes.small,
  modifier = modifier
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier.width(192.dp)
  ) {
    Image(
      painter = painterResource(drawable),
      contentDescription = null,
      contentScale = ContentScale.Crop,
      modifier = Modifier.size(56.dp)
    )
    Text(
      text = stringResource(text),
      style = MaterialTheme.typography.h3,
      modifier = Modifier.padding(horizontal = 16.dp)
    )
  }
}    
```

---

## 7. Row 정렬하기(Align your body row - Arrangements)
Compose에서는 `LazyRow` Composable을 사용해서 행으로 스크롤 가능한 UI를 구현할 수 있다.
LazyRow를 그대로 사용하면 내부 아이템 간의 간격으로 UI가 답답해 보일 수 있다.

간격을 조절하려면 `arrangements`에 대해 알아야 한다.
컨테이너의 `main-axis`에 하위 Composable을 배치하는 방식은 아래와 같다.
- Equal Weigh
- Space Between
- Space Around
- Space Evenly
- End(Row에서) / Top(Column에서)
- Center
- Start(Row에서) / Bottom(Column에서)

위 배치 외에 `Arrangement.spacedBy()`를 사용하면 각 하위 Composable 사이에 고정된 공간을 추가할 수 있다.
```kotlin
LazyRow(
  horizontalArrangement = Arrangement.spacedBy(8.dp),
  contentPadding = PaddingValues(horizontal = 16.dp),
  modifier = modifier
) {
    // ...
  }
}    
```

- `contentPadding`은 컨테이너 내부의 항목에 동일한 패딩을 유지하되 상위 목록의 경계 내에서 콘텐츠를 자르지 않고 스크롤 할 수 있도록
  padding을 부여할 수 있다.

---

## 8. LazyHorizontalGrid 사용하기(Favorite collections grid-Lazy grids)
LazyRow에 Column을 갖도록 하여 Grid 형태의 UI를 구현할 수 있지만, `LazyHorizontalGrid`를 사용하면 
쉽고 효과적으로 구현할 수 있다.
```kotlin
LazyHorizontalGrid(
  rows = GridCells.Fixed(2),
  contentPadding = PaddingValues(horizontal = 16.dp),
  horizontalArrangement = Arrangement.spacedBy(8.dp),
  verticalArrangement = Arrangement.spacedBy(8.dp),
  modifier = modifier.height(120.dp)
) {
  items(favoriteCollectionsData) { item ->
    FavoriteCollectionCard(
      drawable = item.drawable,
      text = item.text,
      modifier = Modifier.height(56.dp)
    )
  }
}    
```

- `GridCells.Fixed`를 사용하여 그리드의 row 개수를 고정하여 모양을 조정할 수 있다.