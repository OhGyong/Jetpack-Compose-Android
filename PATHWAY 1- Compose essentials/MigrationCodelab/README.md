[Migrating to Jetpack Compose](https://developer.android.com/codelabs/jetpack-compose-migration?continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fcompose%3Fhl%3Dko#0)

이 프로젝트는 [sunflower](https://github.com/android/sunflower/tree/views)를 바탕으로 Codelab에 맞춰 진행되었습니다. 

## 1~3. Codelab 목표 및 설(Introduction, Migration strategy)
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

