package com.study.basicthemingcodelab.data

import com.study.basicthemingcodelab.R

data class Post(
    val id: Long,
    val title: String,
    val imageId: Int,
    val imageThumbId: Int,
    val metadata: Metadata,
    val tag: Set<String>
)

data class Metadata(
    val name: String,
    val date: String,
    val readTimeMinutes: Int
)

object PostRepo {
    fun getPostFeature() = postList.random()
    fun getPostList() : List<Post> = postList
}

private val post1 = Post(
    id =1,
    title ="Write your first Compose app",
    imageId = R.drawable.post_image_1,
    imageThumbId = R.drawable.post_image_thumb_1,
    metadata = Metadata(
        name ="Kim",
        date = "August 02",
        readTimeMinutes = 1
    ),
    tag = setOf("Jetpack Compose", "Composable")
)

private val post2 = Post(
    id = 2,
    title = "Implement a real-world design",
    imageId = R.drawable.post_image_2,
    imageThumbId = R.drawable.post_image_thumb_2,
    metadata = Metadata(
        name ="Park",
        date = "July 30",
        readTimeMinutes = 3
    ),
    tag = setOf("Modifier", "Design")
)

private val post3 = Post(
    id = 3,
    title = "Migrate from the View system",
    imageId = R.drawable.post_image_3,
    imageThumbId = R.drawable.post_image_thumb_3,
    metadata = Metadata(
        name = "Lee",
        date = "July 09",
        readTimeMinutes = 1
    ),
    tag = setOf("Migration", "Views")
)

private val postList: List<Post> = listOf(post1, post2, post3)
