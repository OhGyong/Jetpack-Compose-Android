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
Jetpack Compose는 안드로이드의 최신 네이티브 UI 도구 키트이다.
Compose는 직관적이고 강력하면서도 처음부터 개발을 가속화하도록 설계됐다.
기존 XML보다 훨씬 적은 코드로 UI를 구현할 수 있다.

왜 Compose가 필요할까?<br>
기존 10년간 Views로 UI를 개발해왔는데 그 당시와 기술이 많이 달라졌다.
기기의 성능은 향상됐고 앱에 대한 기대감도 커졌다.
또한 앱의 UI도 훨씬 동적이고 표현도 풍부해졌다.

물론 Views로도 훌륭한 앱을 개발할 수 있다.
하지만 최신 아키텍처를 기반으로 하고 kotlin을 활용하는 현대적인 최신 도구 키트를 사용하자는 의견이 많았다.

그렇다면 Compose가 UI를 빠르고 쉽게 구축할 수 있는 이유는 무엇일까?<br>
위에서 Compose는 선언적 UI 도쿠 키트라고 언급했다.
두 가지 개념을 하나씩 살펴보자.

첫번째로 선언적인 부분이다.<br>
- **Declarative**<br>
요즘 앱들은 데이터가 동적이고 실시간으로 업데이트된다.
원래 Android Views를 사용하면 XML에 UI를 선언해야 한다.
데이터가 바뀌면 UI도 업데이트해야 한다.
이를 위해서 View를 조회하고 속성을 설정해야하는 변형이 필요하다.
애플리케이션 상태가 바뀔 때마다 데이터베이스나 네트워크 호출이 로드되거나 사용자 상호작용이 끝나면
이 새로운 정보로 UI를 업데이트해서 데이터를 동기화해야한다.
View마다 상태가 다르고 각각 업데이트해야 하므로 그 과정이 복잡하다.
모델과 UI의 상태를 동기화하는 작업에서 버그가 엄청나게 발생할 수 있다.
이런 부분은 개발자가 책임지고 모든걸 업데이트해야 한다.
앱과 UI가 복잡해지면서 오류가 생기기 쉬워졌다.
<br><br>
Compose 같은 선언적 UI는 이것과는 다른 방식을 사용한다.
상태(State)를 UI로 변경한다.
UI는 변경할 수 없고, 한 번 생성하면 업데이트가 불가능하다.
앱의 상태가 바뀌면 새로운 상태를 새로운 표현으로 변환한다.
이 방식은 UI 전체를 다시 생성하기 때문에 동기화 문제를 완전히 해결한다.
Compose는 매우 지능적이고 효율적이어서 변경되지 않은 요소에 대한 작업은 건너뛴다.
하지만 개념적으로는 특정 상태에 맞춰 UI를 새로 생성하는 것과 같다.
코드는 특정 상태에 대한 UI 형태를 설명할 뿐 생성 방법을 지정하지 않는다.
<br><br>
Compose는 Composable이라는 @가 달린 함수로 값을 반환하는 대신 UI를 전달한다.
그래서 UI 구성 요소를 빠르고 쉽게 생성할 수 있다.
이때 재사용 가능한 요소로 구성된 라이브러리로 UI를 나누는 것이 좋다.
<br><br>
상태가 바뀌거나 메시지 목록이 바뀌었을 때 Composable 함수를 실행하면 새 UI가 생성된다.
이것을 Recomposing이라고 한다.
<br><br>
ViewModel은 어떻게 활용할 수 있을까?<br>
콜 스택을 처리하는 동안 ViewModel이 메시지의 LiveData를 노출한다.
observeAsState를 통해 데이터를 관찰할 수 있고 새 데이터가 입력될 때마다 Recomposable된다.
직접 감시 객체를 설정할 필요가 없다.
Compose 컴파일러는 어느 Composable이 상태를 읽는지 추적하고 상태가 바뀌면 자동으로 다시 실행한다.
컴파일러가 지능적이여서 입력이 변경된 컴포저블만 다시 실행하고 나머지는 건너뛰면서 매우 효율적인 동작을 보여준다.
<br><br>
각 Composable은 변경할 수 없다.
다시 말하면 Composable을 참조하거나 나중에 쿼리하거나 내용을 업데이트 할 수 없다.
정보를 입력할 때는 모두 매개변수로 Composable에 전달해야 한다.
그렇다고 Composable이 고정돼 있다는 것은 아니다.
버튼과 같은 콜백을 이용해서 상태를 변경하면 Composable을 다시 실행하게 된다.
<br><br>
Composable 함수는 remember 함수를 사용하면 이전의 실행에서 얻은 값을 기억할 수 있다.
Compose의 핵심은 특정 상태에서 UI의 형태를 완전히 설명하고
상태가 바뀌면 프레임워크에서 UI를 업데이트 해야한다.
<br><br>
Compose는 여러 가지 애플리케이션 아키텍처와 호환되지만 단방향 데이터 플로를 따르는 아키텍처와 잘 맞는다.
ViewModel이 화면 상태의 단일 스트림을 노출하면 Compose UI에서 관찰하고 각 구성 요소의 매개변수로 전달한다.
각 구성 요소는 필요한 상태만 수신하므로 데이터를 바꿀 때만 업데이트하면 된다.
ViewState 객체의 단일 스트림을 생성하면 상태 변경을 한 곳에서 처리하는데 도움이 된다.
전체적 화면 상태를 추론하고 오류를 낮추기 쉽다.
이러한 패턴은 입력에 따라 완전히 제어되기 때문에 테스트를 하기도 쉽다.

- **UI Toolkit**<br>
Composable은 다양한 UI 구성 요소 도구 키트를 제공한다.
Jetpack Compose는 머티리얼 디자인 구성 요소와 테마 시스템을 구현한다.
애플리케이션을 어셈블하는데 필요한 구성 요소도 제공한다.
모든 구성 요소는 기본적으로 머티리얼 스타일링을 따르고 머티리얼 테마를 구현하기 때문에
모든 구성 요소를 브랜드에 맞게 체계적으로 맞춤 설정할 수 있다.
<br><br>
Compose는 간단하지만 행과 열을 기반으로 하는 강력한 레이아웃 시스템을 제공한다.
하지만 View 시스템과 달리 Compose 레이아웃 모델은 려러 척도를 전달할 수 없어서 중첩된 레이아웃에 적합하다.
<br><br>
가장 기대가 큰 개선 사항은 새로운 애니메이션 시스템이다.
훨씬 간단하고 효과적으로 UI에 모션을 적용할 수 있다.
Compose에 MotionLayout을 가져오는 작업도 진행중이다.
<br><br>
Compose에서는 테스트와 접근성이 1급 객체이다.
UI에 병렬 트리를 생성하는 시맨틱 시스템을 기반으로 한다.
접근성 서비스에 더 많은 정보를 제공하거나 UI 요소를 매칭해서 어설션하는데 도움이 된다.
<br><br>
Compose는 테스트 기능을 극대화하는 전용 테스트 아티팩트를 제공하고
독립적으로 Composable을 테스트하는 간편한 API를 제공한다.
<br><br>
Compose는 코틀린으로만 개발됐고, 우수한 언어 기능을 활용해 강력하고 간결하면서도 직관적인 API를 구축했다.
코루틴을 사용하면 간단한 비동기식 API를 작성할 수 있다.
제스처를 애니메이션으로 핸드오프하는 것처럼 비동기식 이벤트를 결합한 코드를 간단하게 작성할 수 있다.
구조적 동시성을 통해 이런 취소와 정리를 제공한다.
<br><br>
코틀린은 툴링으로 구성된 강력한 에코시스템이 있다.
UI 구성 요소를 새로운 함수로 추출해서 간단하게 재사용할 수 있다.

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
[Basics Codelab](https://github.com/OhGyong/Jetpack-Compose-Android/tree/main/PATHWAY%201-%20Compose%20essentials/BasicsCodelab)
- Compose 정의
- Compose로 UI 빌드하기
- Composable 함수의 status 관리
- RecyclerView 같은 리스트 뷰 만들기
- 애니메이션 추가하기
- 앱 스타일과 테마 지정하기

![basics codelab](https://user-images.githubusercontent.com/52282493/233921079-f6d323a0-c367-49dd-b5a8-7cbede178c1b.gif)

---

## 5. Basic layouts in Compose
[BasicLayouts Codelab](https://github.com/OhGyong/Jetpack-Compose-Android/tree/main/PATHWAY%201-%20Compose%20essentials/BasicLayoutsCodelab)
- Modifier를 사용하여 Composable을 채우는 법
- Column 및 LazyRow와 같은 표준 레이아웃 Compose 요소로 하위 Composable을 배치하는 방법
- 정렬과 배치로 상위 Composable 내에서 하위 Composable 위치를 변경하는 법
- Scaffold 및 Bottom Navigation과 같은 Material Composable을 사용하여 포괄적인 레이아웃을 만드는 방법
- slot API를 사용하여 유연한 Composable을 빌드하는 방법

![ezgif com-gif-maker](https://github.com/OhGyong/Jetpack-Compose-Android/assets/52282493/89ac4e4f-5f0c-4561-9e27-21707b0e7413)