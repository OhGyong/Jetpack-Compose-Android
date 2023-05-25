@file:OptIn(ExperimentalMaterial3Api::class)

package com.study.basicthemingcodelab

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.study.basicthemingcodelab.data.Post
import com.study.basicthemingcodelab.data.PostRepo
import java.util.Locale

@Composable
fun Home() {
    val postFeature = remember {PostRepo.getPostFeature()}
    val postList = remember {PostRepo.getPostList()}
    MaterialTheme {
        Scaffold(
            topBar = {AppBar()}
        ) { paddingValues ->
            LazyColumn(contentPadding = paddingValues) {
                item {HomeSection("Top Story")}
                item {HomeFeatured(postFeature)}
                item {HomeSection("Posts")}
                item { Surface(modifier = Modifier.padding(top = 16.dp)){} }
                items(postList) { post->
                    PostItem(post)
                    Divider(Modifier.padding(horizontal = 16.dp))
                }
                item { Surface(modifier = Modifier.padding(bottom = 16.dp)){} }
            }
        }
    }
}

@Composable
fun AppBar() {
    TopAppBar(
        navigationIcon = {
            Icon(
                imageVector = Icons.Rounded.Home,
                contentDescription = null,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        },
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.titleMedium
            )
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    )
}

@Composable
fun HomeSection(
    sectionString: String,
    modifier: Modifier = Modifier
) {
    Surface (
        modifier = modifier,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
        contentColor = MaterialTheme.colorScheme.primary
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            text = sectionString,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun HomeFeatured(
    post: Post,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(16.dp),
        border = BorderStroke(width = 0.1.dp, color = Color.Gray)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            Image(
                painter = painterResource(id = post.imageId),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 180.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = modifier.padding(horizontal = 16.dp),
                text = post.title,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                modifier = modifier.padding(horizontal = 16.dp),
                text = post.metadata.name,
                style = MaterialTheme.typography.bodyLarge
            )
            PostFeatureMetaData(post, modifier)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun PostFeatureMetaData(
    post: Post,
    modifier: Modifier = Modifier
) {
    val tagStyle = TextStyle(
        background = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
    ).toSpanStyle()

    val text = buildAnnotatedString {
        append(post.metadata.date)
        append(" • ")
        append("${post.metadata.readTimeMinutes}min")
        append(" • ")
        post.tag.forEach { tag->
            withStyle(tagStyle) {
                append(" ${tag.uppercase(Locale.ROOT)} ")
            }
            append(" ")
        }
    }
    Text(
        modifier = modifier.padding(horizontal = 16.dp),
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.Thin
    )
}

@Composable
fun PostItem(
    post: Post,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Image(
            painter = painterResource(id = post.imageThumbId),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(60.dp)
                .height(60.dp)
                .padding(10.dp)
                .clip(shape = CutCornerShape(topStart = 8.dp))
        )
        Column(modifier = modifier.padding(top = 10.dp)) {
            Text(
                text = post.title,
                style = MaterialTheme.typography.bodyMedium
            )
            val text = buildAnnotatedString {
                append(post.metadata.date)
                append(" ")
                append(post.metadata.readTimeMinutes.toString())
                append(" ")
                post.tag.forEach { tag->
                    append(tag)
                    append(" ")
                }
            }
            Text(
                text = text,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}