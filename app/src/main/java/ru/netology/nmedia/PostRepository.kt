package ru.netology.nmedia

import androidx.lifecycle.LiveData

interface PostRepository {
    fun getAll(): LiveData<List<Post>>
    fun likeById(id: Long)
    fun webById(id: Long)
    fun viewsById(id: Long)
    fun save(post: Post)
    fun removeById(id: Long)
    fun undoEditById(id: Long)
    //fun menuById(id: Long)
}