package com.study.basicscodelab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
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
private fun MyApp(
    modifier: Modifier = Modifier,
    names: List<String> = listOf("World", "Compose")

) {
    Column(modifier = modifier.padding(vertical = 4.dp)) {
        for(name in names) {
            Greeting(name = name)
        }
    }
}

@Composable
fun Greeting(name: String) {
    val expanded = remember {mutableStateOf(false)}
    val extraPadding = if(expanded.value) 48.dp else 0.dp

    Surface(color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(4.dp, 8.dp)) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(
                modifier = Modifier.weight(1f).padding(bottom = extraPadding)
            ) {
                Text(text = "Hello, ")
                Text(text = name)
            }
            ElevatedButton(onClick = {
                expanded.value = !expanded.value
            }) {
                Text(if (expanded.value) "Show less" else "Show more")
            }
        }
    }
}

@Preview(showBackground = true, name = "Text Preview", widthDp = 320)
@Composable
fun DefaultPreview() {
    BasicsCodelabTheme {
        MyApp()
    }
}