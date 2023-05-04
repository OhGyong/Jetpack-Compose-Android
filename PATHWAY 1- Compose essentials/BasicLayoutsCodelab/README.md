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
TextField 내부에 아이콘을 삽입하려면 `leadingIcon`을 사용한다.
contentDescription을 null로 설정한 이유는 `placeholder`에서 이미 TextField에 대한 설명을 하고 있기 때문이다.

TextField의 배경색을 MaterialTheme의 `surface`와 같은 색으로 하려면 `TextFieldDefaults.textFieldColors`
를 사용하여 특정 색상을 재정의한다.
> TextField는 XMl의 EditText와 유사하다.

`heightIn`은 최소 높이를 지정한다.
최소 높이를 지정한 것이기 때문에 시스템의 글꼴과 같은 설정을 확대하면 높이가 늘어날 수 있다.(권장되는 방식이라 함.)

`fillMaxWidth`은 상위 요소의 전체 가로 공간을 차지하도록 지정한다.