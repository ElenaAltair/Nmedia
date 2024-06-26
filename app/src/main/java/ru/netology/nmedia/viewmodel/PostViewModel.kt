package ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.*
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.*

var empty = Post(
    id = 0,
    author = "",
    content = "",
    published = "",
    likedByMe = false,
    likes = 0,
    web = 0,
    views = 0,
    contentOld = "",
    webByMe = false,
    viewsByMe = false,
    video = null
)

class PostViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PostRepository = PostRepositoryImpl(
        AppDb.getInstance(context = application).postDao()
    )


    val data = repository.getAll()
    val edited = MutableLiveData(empty)

    fun save() {
        edited.value?.let {
            repository.save(it)
        }
        edited.value = empty
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun changeContent(content: String) {

        val text = content.trim()
        var textOld = edited.value?.content
        if (edited.value?.content == text) {
            return
        }
        edited.value = edited.value?.copy(contentOld = textOld.toString(), content = text)

    }

    fun undoEditById(id: Long) = repository.undoEditById(id)

    fun likeById(id: Long) = repository.likeById(id)
    fun webById(id: Long) = repository.webById(id)
    fun viewsById(id: Long) = repository.viewsById(id)
    fun removeById(id: Long) = repository.removeById(id)

}