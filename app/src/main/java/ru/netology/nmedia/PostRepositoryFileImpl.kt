package ru.netology.nmedia

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PostRepositoryFileImpl(private val context: Context) : PostRepository {
    private val gson = Gson()
    private val typeToken = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val filename = "posts.json"
    private var nextId = 1L
    private var posts = emptyList<Post>()
        private set(value) {
            field = value
            sync()
        }

    private val data = MutableLiveData(posts)

    init {
        val file = context.filesDir.resolve(filename)
        if (file.exists()) {
            context.openFileInput(filename).bufferedReader().use {
                posts = gson.fromJson(it, typeToken)
                nextId = posts.maxOfOrNull { it.id }?.inc() ?: 1
                data.value = posts
            }
        }
    }

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {

        posts = posts.map {
            if (it.id != id) it
            else {
                var like = 0
                if (it.likedByMe) like-- else like++
                it.copy(likedByMe = !it.likedByMe, likes = it.likes + like)
            }
        }
        data.value = posts

    }

    override fun webById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(web = it.web + 1, webByMe = true)
        }
        data.value = posts

    }

    override fun viewsById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(views = it.views + 1, viewsByMe = true)
        }
        data.value = posts

    }

    override fun save(post: Post) {
        if (post.id == 0L) {
            posts = listOf(
                post.copy(
                    id = nextId++,
                    author = "Me",
                    likedByMe = false,
                    published = "Now",
                    likes = 0,
                    web = 0,
                    views = 0,
                    contentOld = "",
                    webByMe = false,
                    viewsByMe = false,
                    video = null
                )
            ) + posts
            data.value = posts

            return
        }

        posts = posts.map {
            if (it.id != post.id) it else it.copy(contentOld = it.content, content = post.content)
        }
        data.value = posts

    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts

    }

    override fun undoEditById(id: Long) {
        posts = posts.map {
            if (it.id != id || it.contentOld.equals("")) it else it.copy(
                content = it.contentOld,
                contentOld = ""
            )
        }
        data.value = posts

    }

    override fun videoById(id: Long) {
        TODO("Not yet implemented")
    }

    private fun sync() {
        context.openFileOutput(filename, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(posts))
        }
    }


}