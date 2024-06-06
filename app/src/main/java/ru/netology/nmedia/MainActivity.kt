package ru.netology.nmedia

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding


var qqq = 1

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val viewModel: PostViewModel by viewModels()


        val newPostLauncher = registerForActivityResult(NewPostResultContract()) { result ->
            result ?: return@registerForActivityResult

            viewModel.changeContent(result)
            viewModel.save()
        }

        val newPostLauncher2 = registerForActivityResult(NewPostResultContract2()) { result ->
            result ?: return@registerForActivityResult

            viewModel.changeContent(result)
            viewModel.save()
        }

        // Если текст пришлют из внешнего источника в IntelHandlerActivity,
        // то создаём новый пост,
        // правда, так как приложение запускается заново, все ранее созданные обновления забываются,
        // думаю, если хранить данные в БД, это решит проблему
        val text = intent.getStringExtra(Intent.EXTRA_TEXT)
        if(text!=null){
            viewModel.changeContent(text)
            viewModel.save()
        }


        val adapter = PostsAdapter(object : OnInteractionListener {
            override fun onLike(post: Post) {
                viewModel.likeById(post.id)

                // Тренировка
                // создание интента для запуска другой активити
                // val myIntent = Intent(this@MainActivity, IntentHandlerActivity::class.java)
                // startActivity(myIntent)
            }

            override fun onVideo(post: Post) {
                val intent2 = Intent(this@MainActivity, VideoActivity::class.java)
                val src:String = post.video.toString()
                intent2.putExtra(Intent.EXTRA_TEXT, src)
                startActivity(intent2)

                //viewModel.videoById(post.id)
            }

            override fun onEdit(post: Post) {
                newPostLauncher2.launch(post.content)
                viewModel.edit(post)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onWeb(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }
                // Вызываем диалог выбора приложений для отправки текста поста в другое приложение
                val shareIntent = Intent.createChooser(intent, post.content)
                startActivity(shareIntent)
                viewModel.webById(post.id)
            }

            override fun onViews(post: Post) {
                viewModel.viewsById(post.id)
            }

            override fun onUndoEdit(post: Post) {
                viewModel.undoEditById(post.id)
            }

        })

        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        viewModel.edited.observe(this) { post ->
            if (post.id == 0L) {
                return@observe
            }

        }

        binding.fab.setOnClickListener {
            newPostLauncher.launch()
        }


    }


}


