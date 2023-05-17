# [Jetpack Compose Theme Settings](https://developer.android.com/codelabs/jetpack-compose-theming?hl=ko&continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fjetpack-compose-for-android-developers-1%3Fhl%3Dko%23codelab-https%3A%2F%2Fdeveloper.android.com%2Fcodelabs%2Fjetpack-compose-theming#0)
이 프로젝트는 android-compose-codelabs의 [ThemingCodelabM2](https://github.com/googlecodelabs/android-compose-codelabs/tree/main/ThemingCodelabM2) 프로젝트를 바탕으로 Codelab에 맞춰 진행하였습니다.

---

## 1~2 프로젝트 소개
이 codelab에서는 Material Design 시스템을 구현하는 방법을 익힌다.
Jetpack Compose의 테마 설정 API를 사용하여 앱 요소들에 색상, 스타일, 도형을 정의하고
일반 모드와 다크 모드일 때 테마를 적용하는 방법도 배울 것이다.

---

## Material Theming
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
Background와 대비하여 컨텐츠를 구분하며 컨테이너나 카드 등의  

또한 Material은 `on` 색상을 정의한다.
이 색상은 이름이 지정된 색상 중 하나 위에 있는 콘텐츠에 사용된다.
예를 들어 `background`로 배경 색상을 변경한다면, `on background`는 배경 위에 표시되는 다른 요소(텍스트 등)의 색을 변경한다.

## 

---

<img width="470" alt="스크린샷 2023-05-17 오후 2 15 42" src="https://github.com/OhGyong/Jetpack-Compose-Android/assets/52282493/ca0ae7ff-904a-4d50-9663-bf54ef6903fb">