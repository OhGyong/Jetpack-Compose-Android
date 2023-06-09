package com.study.basicscodelab

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.study.basicscodelab.ui.theme.BasicsCodelabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * Compose를 사용하면 Activity가 안드로이드 앱의 진입점으로 유지됨.
         * setContent를 사용하여 레이아웃을 정의하는데, 기존에 XML을 사용하는 대신 Composable 함수를 호출함.
         */
        setContent {
            // setContent 내에서 사용되는 앱 테마는 프로젝트 이름에 맞게 지정됨.
            BasicsCodelabTheme {
                // A surface container using the 'background' color from the theme
                Surface {
                    MyApp(modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }
}

@Composable
fun MyApp(modifier: Modifier = Modifier) {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    Surface(modifier) {
        if (shouldShowOnboarding) {
            OnboardingScreen(onContinueClicked = {shouldShowOnboarding = false})
        } else {
            Greetings()
        }
    }
}

@Preview
@Composable
fun MyAppPreview() {
    BasicsCodelabTheme {
        MyApp(Modifier.fillMaxSize())
    }
}

@Composable
private fun Greetings(
    modifier: Modifier = Modifier,
    names: List<String> = List(1000){"$it"}
) {
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(items = names){ name->
            CardBody(name)
        }
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
private fun GreetingsPreview() {
    BasicsCodelabTheme {
        Greetings()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardBody(name: String) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ){
        Greeting(name)
    }
}

@Composable
fun Greeting(name: String) {
    var expanded by remember {mutableStateOf(false)}

    Surface(color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(4.dp, 8.dp)) {
        Row(
            modifier = Modifier
                .padding(24.dp)
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(text = name, style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold
                ))
                if(expanded) {
                    Text(modifier = Modifier.padding(top = 10.dp), text = stringResource(id = R.string.expanded_content))
                }
            }
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = if(expanded) Filled.ExpandLess else Filled.ExpandMore,
                    contentDescription = if(expanded){
                        stringResource(id = R.string.show_less)
                    } else {
                        stringResource(id = R.string.show_more)
                    })
            }
        }
    }
}

@Preview(
    showBackground = true,
    name = "Text Preview",
    uiMode = UI_MODE_NIGHT_YES,
    widthDp = 320
)
@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    BasicsCodelabTheme {
        Greetings()
    }
}

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    onContinueClicked: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to the Basics Codelab!")
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicked
        ) {
            Text("Continue")
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    BasicsCodelabTheme {
        OnboardingScreen(onContinueClicked = {})
    }
}