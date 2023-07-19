@file:OptIn(ExperimentalMaterial3Api::class)

package com.study.basicanimationcodelab.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.splineBasedDecay
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.horizontalDrag
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.study.basicanimationcodelab.R
import com.study.basicanimationcodelab.ui.theme.Amber600
import com.study.basicanimationcodelab.ui.theme.BasicAnimationCodelabTheme
import com.study.basicanimationcodelab.ui.theme.Green300
import com.study.basicanimationcodelab.ui.theme.Green800
import com.study.basicanimationcodelab.ui.theme.Purple100
import com.study.basicanimationcodelab.ui.theme.Purple700
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

private enum class TabPage {
    Home, Work
}

@Composable
fun Home() {
    val allTasks = stringArrayResource(id = R.array.tasks)
    val allTopics = stringArrayResource(id = R.array.topics).toList()

    var tabPage by remember { mutableStateOf(TabPage.Home) }
    var weatherLoading by remember { mutableStateOf(false) }
    val tasks = remember { mutableStateListOf(*allTasks) } // 전개 연산자를 통해 개별 인자를 전달 해야함.
    var expandedTopic by remember { mutableStateOf<String?>(null) }
    var editMessageShown by remember { mutableStateOf(false) }

    suspend fun loadWeather() {
        if(!weatherLoading) {
            weatherLoading = true
            delay(2000L)
            weatherLoading = false
        }
    }

    suspend fun showEditMessage() {
        if (!editMessageShown) {
            editMessageShown = true
            delay(3000L)
            editMessageShown = false
        }
    }

    LaunchedEffect(Unit) {
        loadWeather()
    }

    val lazyListState = rememberLazyListState()

    val backgroundColor by animateColorAsState(if (tabPage == TabPage.Home) Purple100 else Green300)

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            Column {
                HomeTabBar(
                    backgroundColor = backgroundColor,
                    tabPage = tabPage,
                    onTabSelected = { tabPage = it }
                )
                EditMessage(editMessageShown)
            }
        },
        containerColor = backgroundColor,
        floatingActionButton = {
            HomeFloatingActionButton(
                extended = lazyListState.isScrollingUp(),
                onClick = {
                    coroutineScope.launch {
                        showEditMessage()
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 32.dp),
            state = lazyListState,
            modifier = Modifier.padding(paddingValues)
        ) {
            // Weather
            item { HomeHeader(title = stringResource(id = R.string.weather)) }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shadowElevation = 2.dp
                ) {
                    if(weatherLoading) {
                        LoadingRow()
                    } else {
                        WeatherRow(
                            onRefresh = {
                                coroutineScope.launch {
                                    loadWeather()
                                }
                            }
                        )
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(32.dp))}

            // Topics
            item { HomeHeader(title = stringResource(id = R.string.topics)) }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            items(allTopics) { topic ->
                TopicRow(
                    topic = topic,
                    expanded = expandedTopic == topic,
                    onClick = {
                        expandedTopic = if(expandedTopic == topic) null else topic
                    }
                )
            }
            item { Spacer(modifier = Modifier.height(32.dp))}

            // Tasks
            item { HomeHeader(title = stringResource(id = R.string.tasks))}
            item { Spacer(modifier = Modifier.height(16.dp)) }
            if(tasks.isEmpty()) {
                item {
                    TextButton(onClick = {
                        tasks.clear()
                        tasks.addAll(allTasks)
                    }) {
                        Text(stringResource(id = R.string.add_tasks))
                    }
                }
            }
            items(count = tasks.size) {
                val task = tasks.getOrNull(it)
                if(task != null) {
                    key(task) {
                        TaskRow(
                            task = task,
                            onRemove = {tasks.remove(task)}
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EditMessage(shown: Boolean) {
    AnimatedVisibility(
        visible = shown,
        enter = slideInVertically(
            // Enters by sliding down from offset -fullHeight to 0.
            initialOffsetY = { fullHeight -> -fullHeight },
            animationSpec = tween(durationMillis = 150, easing = LinearOutSlowInEasing)
        ),
        exit = slideOutVertically(
            // Exits by sliding up from offset 0 to -fullHeight.
            targetOffsetY = { fullHeight -> -fullHeight },
            animationSpec = tween(durationMillis = 250, easing = FastOutLinearInEasing)
        )
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.secondary,
            shadowElevation = 4.dp
        ) {
            Text(
                text = stringResource(id = R.string.edit_message),
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun TaskRow(task: String, onRemove: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .swipeToDismiss(onRemove),
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = task,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun TopicRowSpacer(visible: Boolean) {
    AnimatedVisibility(visible = visible) {
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopicRow(topic: String, expanded: Boolean, onClick: () -> Unit) {
    TopicRowSpacer(visible = expanded)

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 2.dp,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .animateContentSize()
        ) {
            Row {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = topic,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            if(expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(id = R.string.lorem_ipsum),
                    textAlign = TextAlign.Justify
                )
            }
        }
    }
    TopicRowSpacer(visible = expanded)
}

@Composable
fun WeatherRow(onRefresh: () -> Unit ) {
    Row(
        modifier = Modifier
            .heightIn(min = 64.dp)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Amber600)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = stringResource(id = R.string.temperature), fontSize = 24.sp)
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = onRefresh) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = stringResource(id = R.string.refresh)
            )
        }

    }
}

@Composable
fun LoadingRow() {
    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1000
                0.7f at 500
            },
            repeatMode = RepeatMode.Reverse
        )
    )
    Row(
        modifier = Modifier
            .heightIn(min = 64.dp)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color.LightGray.copy(alpha = alpha))
        )
        Spacer(modifier = Modifier.width(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
                .background(Color.LightGray.copy(alpha = alpha))
        )
    }
}

@Composable
fun HomeHeader(title: String) {
    Text(
        text = title,
        modifier = Modifier.semantics { heading() },
        style = MaterialTheme.typography.headlineSmall
    )
}

/**
 * LazyListState의 확장 함수로 isScrollingUp을 정의
 */
@Composable
private fun LazyListState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) { mutableStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableStateOf(firstVisibleItemScrollOffset)}
    return remember(this) {
        derivedStateOf {
            if(previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}

@Composable
fun HomeFloatingActionButton(
    extended: Boolean,
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.secondary,
        contentColor = Color.Black

    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = null
            )

            AnimatedVisibility(visible = extended) {
                Text(
                    text = stringResource(id = R.string.edit),
                    modifier = Modifier.padding(start = 8.dp, top = 3.dp)
                )
            }
        }
    }
}

@Composable
private fun HomeTabBar(
    backgroundColor: Color,
    tabPage: TabPage,
    onTabSelected: (tabPage: TabPage) -> Unit
) {
    TabRow(
        selectedTabIndex = tabPage.ordinal,
        containerColor = backgroundColor,
        indicator = { tabPositions ->
            HomeTabIndicator(tabPositions, tabPage)
        }
    ) {
        HomeTab(
            icon = Icons.Default.Home,
            title = stringResource(R.string.home),
            onClick = { onTabSelected(TabPage.Home) }
        )
        HomeTab(
            icon = Icons.Default.AccountBox,
            title = stringResource(R.string.work),
            onClick = { onTabSelected(TabPage.Work) }
        )
    }
}

@Composable
private fun HomeTab(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.Black
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = title)
    }
}

@Composable
private fun HomeTabIndicator(
    tabPositions: List<TabPosition>,
    tabPage: TabPage
) {
    val transition = updateTransition(tabPage, label = "Tab indicator")
    val indicatorLeft by transition.animateDp(
        transitionSpec = {
            if (TabPage.Home isTransitioningTo TabPage.Work) {
                // Indicator moves to the right.
                // The left edge moves slower than the right edge.
                spring(stiffness = Spring.StiffnessVeryLow)
            } else {
                // Indicator moves to the left.
                // The left edge moves faster than the right edge.
                spring(stiffness = Spring.StiffnessMedium)
            }
        },
        label = "Indicator left"
    ) { page ->
        tabPositions[page.ordinal].left
    }
    val indicatorRight by transition.animateDp(
        transitionSpec = {
            if (TabPage.Home isTransitioningTo TabPage.Work) {
                // Indicator moves to the right
                // The right edge moves faster than the left edge.
                spring(stiffness = Spring.StiffnessMedium)
            } else {
                // Indicator moves to the left.
                // The right edge moves slower than the left edge.
                spring(stiffness = Spring.StiffnessVeryLow)
            }
        },
        label = "Indicator right"
    ) { page ->
        tabPositions[page.ordinal].right
    }
    val color by transition.animateColor(label = "Border color") { page ->
        if(page == TabPage.Home) Purple700 else Green800
    }

    Box(
        Modifier
            .fillMaxSize()
            .offset(x = indicatorLeft)
            .wrapContentSize(align = Alignment.BottomStart)
            .width(indicatorRight - indicatorLeft)
            .fillMaxSize()
            .padding(4.dp)
            .border(
                BorderStroke(2.dp, color),
                RoundedCornerShape(4.dp)
            )
    )
}

private fun Modifier.swipeToDismiss(
    onDismissed: () -> Unit
): Modifier = composed {
    // 애니메이션의 오프셋(가로 값을 측정하기 위해 사용)
    val offsetX = remember { Animatable(0f) }

    // 사용자의 포인터 입력 이벤트를 수신하고 처리하는 콜백 함수
    pointerInput(Unit) {
        // Decay 애니메이션 생성(스크롤이나 드래그 애니메이션에 사용됨)
        val decay = splineBasedDecay<Float>(this)

        // 무한 루프와 awaitPointerEventScope를 위한 coroutineScope 생성
        coroutineScope {
            while (true) {
                // 사용자의 움직임 속도를 추적하는 데 사용되는 클래스(여기서는 스와이프에 소모된 속도를 계산하기 위해 쓰임)
                val velocityTracker = VelocityTracker()

                // 사용자의 입력 이벤트를 동기적으로 기다렸다가 응답할 수 있는 정지 함수
                // (UI 요소의 가시성에 따라서도 호출됨)
                awaitPointerEventScope {
                    // 첫 번째 입력 이벤트의 Pointer id 저장
                    val pointerId = awaitFirstDown().id

                    // 사용자의 터치 또는 드래그 동작에 응답하여 요소의 가로 방향으로 드래그 이동을 감지하고 처리
                    // 드래그가 끝날때 까지 정지
                    horizontalDrag(pointerId) { change ->
                        // 기존 offset 값에 사용자의 입력 이벤트에서 이전 위치와 현재 위치의 가로 변화량을 합친 값
                        val horizontalDragOffset = offsetX.value + change.positionChange().x

                        // 수신 받은 드래그 이벤트의 위치를 애니메이션 값에 동기화
                        launch {
                            offsetX.snapTo(horizontalDragOffset)
                        }
                        println("horizontalDragOffset   " + horizontalDragOffset)
                        // VelocityTracker에 현재 시간과 위치 정보를 전달 받아 위치 정보를 추가
                        velocityTracker.addPosition(change.uptimeMillis, change.position)

                        // positionChange()는 사용자의 드래그 이벤트에서 이전 위치와 현재 위치의 변화량.
                        // 드래그가 감지되면 consume()으로 이벤트 수행.(여러 입력 이벤트의 동작을 막음)
                        if (change.positionChange() != Offset.Zero) change.consume()
                    }
                }
                // 사용자의 드래그 이벤트가 종료된 뒤의 수평 방향 드래그 속도
                val velocity = velocityTracker.calculateVelocity().x

                // 현재 가로 위치와 드래그 속도를 기반으로 애니메이션이 정착할 최종 위치
                val targetOffsetX = decay.calculateTargetValue(offsetX.value, velocity)

                // 드래그 동작 범위를 해당 요소의 가장 왼쪽에서 가장 오른쪽 끝까지로 제한
                offsetX.updateBounds(
                    lowerBound = -size.width.toFloat(),
                    upperBound = size.width.toFloat()
                )

                launch {
                    // 애니메이션의 최종 위치가 해당 요소의 너비보다 작으면
                    // animateTo로 offsetX의 값을 애니메이션으로 변경(초기 상태로 되돌림)
                    if (targetOffsetX.absoluteValue <= size.width) {
                        // animateTo를 사용하여 값을 다시 0f로 애니메이션 처리하여 요소의 위치 조정
                        // initialVelocity는 초기 애니메이션 속도라는데 값을 변경해도 별 차이를 못느꼈음..
                        offsetX.animateTo(targetValue = 0f, initialVelocity = velocity)
                    }
                    // 애니메이션의 최종 위치가 해당 요소의 너비보다 크면 애니메이션 감속 애니메이션을 실행 후 아이템 제거
                    else {
                        offsetX.animateDecay(velocity, decay)
                        onDismissed() // 애니메이션이 완료되면 콜백 호출
                    }
                }
            }
        }
    }
        // IntOffset으로 가로와 세로 위치를 지정 후 offset으로 UI 요소에 위치를 변경하며 적용.
        // remember로 offsetX의 상태를 저장을 했기 때문에 offsetX의 값이 변경되면
        // .offset 블록이 호출되어 UI 요소가 업데이트되어 애니메이션 동작을 확인할 수 있게 됨
        .offset { IntOffset(offsetX.value.roundToInt(), 0) }
}

@Preview
@Composable
fun HomePreview() {
    BasicAnimationCodelabTheme {
        Home()
    }
}

@Preview
@Composable
private fun PreviewHomeTabBar() {
    HomeTabBar(backgroundColor = Purple100, tabPage = TabPage.Home, onTabSelected = {})
}