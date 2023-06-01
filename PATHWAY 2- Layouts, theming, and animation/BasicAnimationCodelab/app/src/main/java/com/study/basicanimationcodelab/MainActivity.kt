package com.study.basicanimationcodelab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.study.basicanimationcodelab.ui.home.Home
import com.study.basicanimationcodelab.ui.theme.BasicAnimationCodelabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicAnimationCodelabTheme {
                Home()
            }
        }
    }
}