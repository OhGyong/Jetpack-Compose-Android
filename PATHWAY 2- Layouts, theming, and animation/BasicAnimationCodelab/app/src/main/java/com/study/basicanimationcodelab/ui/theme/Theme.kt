package com.study.basicanimationcodelab.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

@Composable
fun BasicAnimationCodelabTheme(
    content: @Composable () -> Unit
) {
    val colors = lightColorScheme(
        primary = Purple500,
        secondary = Teal200
    )
    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}