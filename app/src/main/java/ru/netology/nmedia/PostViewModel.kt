package ru.netology.nmedia

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

private val empty = Post(
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
)

class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.getAll()
    val edited = MutableLiveData(empty)

    fun save() {
        edited.value?.let {
            repository.save(it)
        }
        edited.value = empty
    }

    fun cancelEdit(){edited.value = empty
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