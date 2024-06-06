package ru.netology.nmedia

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likedByMe: Boolean = false,
    val likes: Long,
    val web: Long,
    val views: Long,
    val contentOld: String,
    val webByMe: Boolean = false,
    val viewsByMe: Boolean = false,
    val video:String?
)