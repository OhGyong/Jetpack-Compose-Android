package com.study.basiclayoutscodelab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.study.basiclayoutscodelab.ui.theme.BasicLayoutsCodelabTheme
import com.study.basiclayoutscodelab.ui.theme.typography

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicLayoutsCodelabTheme {
                Surface(
                    color = MaterialTheme.colors.background,
                    modifier = Modifier.fillMaxSize()
                ) {
                    val modifier = Modifier
                    MainView(modifier)
                }
            }
        }
    }
}

@Composable
fun MainView(modifier: Modifier) {
    Column(
        modifier = modifier
            .padding(top = 16.dp, start = 8.dp, end = 8.dp)
    ) {
        SearchBar(modifier = modifier)
        AlignYourBodyElement(modifier)
        FavoriteCollectionCard(modifier)
    }

}

@Composable
fun SearchBar(modifier: Modifier) {
    TextField(
        value = "",
        onValueChange = {},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface
        ),
        placeholder = {
            Text(stringResource(R.string.placeholder_search))
        },
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
    )
}

@Composable
fun AlignYourBodyElement(modifier: Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.ab1_inversions),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(88.dp)
                .clip(CircleShape)
        )
        Text(
            text = stringResource(id = R.string.ab1_inversions),
            style = typography.h3,
            modifier = modifier
                .paddingFromBaseline(top = 24.dp, bottom = 8.dp)
        )
    }
}

@Composable
fun FavoriteCollectionCard(modifier: Modifier) {
    Surface(
        shape = MaterialTheme.shapes.small,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.width(192.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.fc2_nature_meditations),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = modifier.size(56.dp)
            )
            Text(
                text = stringResource(id = R.string.fc2_nature_meditations),
                style = typography.h3,
                modifier = modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@Preview(
    widthDp = 360,
    heightDp = 640,
    showBackground = true,
    backgroundColor = 0xFFF0EAE2
)
@Composable
fun MainViewPreview() {
    MaterialTheme {
        MainView(Modifier)
    }
}

@Preview
@Composable
fun SearchBarPreview() {
    MaterialTheme {
        SearchBar(modifier = Modifier)
    }
}

@Preview
@Composable
fun AlignYourBodyElementPreview() {
    MaterialTheme {
        AlignYourBodyElement(modifier = Modifier)
    }
}