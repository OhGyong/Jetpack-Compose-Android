@file:OptIn(ExperimentalMaterial3Api::class)

package com.study.basicanimationcodelab.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import com.study.basicanimationcodelab.R
import com.study.basicanimationcodelab.ui.theme.Green300
import com.study.basicanimationcodelab.ui.theme.Green800
import com.study.basicanimationcodelab.ui.theme.Purple100
import com.study.basicanimationcodelab.ui.theme.Purple700
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private enum class TabPage {
    Home, Work
}

@Composable
fun Home() {
    val allTasks = stringArrayResource(id = R.array.tasks)
    val allTopics = stringArrayResource(id = R.array.topics)

    var tabPage by remember { mutableStateOf(TabPage.Home) }
    var weatherLoading by remember { mutableStateOf(false) }
    val tasks = remember { mutableStateListOf(*allTasks) } // 리스트로 받아야 해서

    var expandedTopic by remember { mutableStateOf<String?>(null) }
    var editMessageShown by remember { mutableStateOf(false) }

    suspend fun loadWeather() {
        if(!editMessageShown) {
            editMessageShown = true
            delay(3000L)
            editMessageShown = false
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

    // todo : 1
    val backgroundColor =  if(tabPage == TabPage.Home) Purple100 else Green300

    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            HomeTabBar(
                backgroundColor = backgroundColor,
                tabPage = tabPage,
                onTabSelected = { tabPage = it }
            )
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

        }
    }
}




@Composable
private fun LazyListState.isScrollingUp(): Boolean {
    /**
     * todo
     * 1. remember의 매개변수에 this를 전달하는 이유
     * 2. firstVisibleItemIndex?
     * 3. firstVisibleItemScrollOffset?
     * 4. derivedStateOf?
     */
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
    FloatingActionButton(onClick = onClick) {

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

    }
}

@Composable
private fun HomeTabIndicator(
    tabPositions: List<TabPosition>,
    tabPage: TabPage
) {
    // todo 4
    val indicatorLeft = tabPositions[tabPage.ordinal].left
    val indicatorRight = tabPositions[tabPage.ordinal].right
    val color = if(tabPage == TabPage.Home) Purple700 else Green800
    Box {
        Modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.BottomStart)
            .offset(x = indicatorLeft)
            .width(indicatorRight - indicatorLeft)
            .padding(4.dp)
            .border(
                BorderStroke(2.dp, color),
                RoundedCornerShape(4.dp)
            )
    }
}
