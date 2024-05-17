package ru.netology.nmedia

class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likedByMe: Boolean = false,
    var likes: Long,
    var web: Long,
    var views: Long
)