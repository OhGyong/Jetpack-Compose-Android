# PATHWAY 1- Compose essentials
[PATHWAY 1 사이트](https://developer.android.com/courses/pathways/jetpack-compose-for-android-developers-1)

---

## 1. Jetpack Compose starter tutorial
Jetpack Compose는 기본 Android UI를 빌드하기 위한 최신 도구 키트이다.
더 적은 코드, 제공되는 다양한 툴, 코틀린으로 통합된 직관성과 API로 안드로이드에서 UI 개발을 간소화하고 가속화한다.
<br/>

이 튜토리얼에서는 선언적 함수를 사용하여 간단한 UI 구성 요소를 빌드하는 법을 배운다.
XML 레이아웃을 사용하지 않고 Composable 함수를 호출하여 원하는 요소를 정의하면 Compose 컴파일러가 나머지 작업을 수행한다.

1. Jetpack Compose는 Composable 함수를 기반으로 구축되었다. 이 함수를 사용하면 UI 구성 프로세스(요소 초기화, 부모 연결)에 초점을 맞추지 않고 앱의 모양을 설명하고 데이터 종속성을 제공하여 프로그래밍 방식으로 앱의 UI를 정의할 수 있다.
    Composable 함수를 만들려면 `@Composable`을 함수 이름 앞에 추가하면 된다.
2. UI 요소는 서로 관계를 맺으며 계층을 이루게 된다. Compose에서는 Composable 함수끼리 서로 호출하면서 UI 계층 구조를 구축한다.
3. Compose는 Material 디자인 원칙을 지원하도록 제작되었다. 많은 UI 요소가 Material 디자인을 사용하여 앱의 스타일을 지정한다.
4. 앱의 모든 곳에 리스트와 애니메이션이 존재한다. Compose는 리스트를 쉽게 만들고 애니메이션을 재밌게 추가할 수 있다.

---

## 2. Compose overview

---

## 3. Thinking in Compose
Jetpack Compose는 Android를 위한 현대적인 선언형 UI 도구 키트이다.
Compose는 frontend 뷰를 명령형으로 변형하지 않고도 UI를 렌더링할 수 있게 하는 선언형 API를 제공하여 앱 UI를 더 쉽게 작성하고 유지관리 할 수 있도록 지원한다.

<br/>

### The declarative programming paradigm
지금까지 안드로이드 뷰 계층 구조는 UI 위젯의 트리로 표시할 수 있었다.
사용자 상호작용 등의 이유로 앱의 상태가 변경되면, 현재 데이터를 표시하기 위해 UI 계층 구조를 업데이트 해야 했다.
UI를 업데이트하는 가장 일반적인 방법으로는 `findViewById()`와 같은 함수를 사용하여 뷰 트리를 탐색하고 set 또는 add와 같은 메서드를 호출하여 노드를 변경(위젯의 내부 상태 변경)하는 것이다.

뷰를 수동으로 조작하면 오류가 발생할 가능성이 커진다.
데이터를 여러 위치에서 렌더링하면 데이터를 표시하는 뷰 중 하나를 업데이트하는 것을 잊기 쉽게 된다.
또한 두 업데이트가 예기치 않은 방식으로 충돌할 경우 예상치 못한 상태를 야기할 수 있다.

지난 몇 년에 걸쳐 업계 전반에서 선언형 UI 모델로 전환하기 시작했으며, 이에 따라 사용자 인터페이스 빌드 및 업데이트와 관련된 엔지니어링이 크게 간소화되었다.
이 기법은 처음부터 화면 전체를 개념적으로 재생성한 후 필요한 변경 사항만 적용하는 방식으로 작동한다.
이러한 접근 방식은 Stateful 뷰 계층 구조를 수동으로 업데이트할 때의 복잡성을 방지할 수 있다.
다시 말해서 Compose는 선언형 UI 프레임워크이다.

화면 전체를 재생성하는 데 있어 한 가지 문제는 시간, 배터리 사용량, 성능 등 많은 비용이 든다는 것이다.
이 비용을 줄이기 위해 Compose는 특정 시점에 UI의 어떤 부분을 다시 그려야 하는지를 지능적으로 선택한다.

---

### A simple composable function
Compose를 사용하면 데이터를 받아서 UI 요소를 내보내는 Composable 함수 집합을 정의하여 사용자 인터페이스를 빌드할 수 있다.<br/>
- `@Composable`으로 Composable 함수를 구성한다. 이 주석을 통해 함수가 데이터를 UI로 변환하기 위한 함수라는 것을 Compose 컴파일러에 알린다.
- 함수는 매개변수를 받을 수 있으며 매개변수를 통해 UI를 형성할 수 있다.
- 함수는 아무것도 반환하지 않는다. UI를 내보내는 Compose 함수는 UI 위젯을 구성하는 대신 원하는 화면 상태를 설명하므로 아무것도 반환할 필요가 없다.
- 멱등성을 띄며 빠르고 부작용이 없다.

---

### The declarative paradigm shift
안드로이드에서는 XML 레이아웃 파일(명령형 객체 지향 UI 도구 키트)의 확장을 통해 위젯의 트리를 인스턴스화함으로써 UI를 초기화한다.
그리고 각 위젯은 내부 상태를 유지하고 앱 로직이 위젯과 상호작용할 수 있도록 gette와 setter 메서드가 노출된다.

반면 Compose의 선언형 접근 방식에서 위젯은 비교적 Stateless 상태이며 getter와 setter 함수를 노출하지 않고(=위젯은 객체로 노출되지 않음) 동일한 Composable 함수를 다른 인수로 호출하여 UI를 업데이트한다.
이 방식은 앱 아키텍처 가이드에서 설명된 대로 ViewModel과 같은 아키텍처 패턴에 상태를 쉽게 제공할 수 있다.
Composable은 식별 가능한 데이터가 업데이트될 때마다 현재 애플리케이션 상태를 UI로 변환한다.

---

### Dynamic content
Composable 함수는 XML이 아닌 코틀린으로 작성되기 때문에 다른 코틀린 코드와 마찬가지로 동적일 수 있다.

---

### Recomposition
명령형 UI 모델에서 위젯을 변경하려면 위젯에서 setter를 호출하여 내부 상태를 변경한다.
Compose에서는 새 데이터를 사용하여 구성 가능한 함수를 다시 호출한다.
이렇게 하면 함수가 재구성되며, 위젯이 새로운 데이터로 다시 그려진다.

---

## 4. Write your first Compose app
![basics codelab](https://user-images.githubusercontent.com/52282493/233921079-f6d323a0-c367-49dd-b5a8-7cbede178c1b.gif)
- Compose 정의
- Compose로 UI 빌드하기
- Composable 함수의 status 관리
- RecyclerView 같은 리스트 뷰 만들기
- 애니메이션 추가하기
- 앱 스타일과 테마 지정하기