package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController

import ru.netology.nmedia.R
import ru.netology.nmedia.activity.NewPostFragment.Companion.textArg
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.dto.OnInteractionListener
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel


class FeedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var binding = FragmentFeedBinding.inflate(inflater, container, false)

        val viewModel: PostViewModel by activityViewModels()

        /* Можно вместо

        // у наших двух фрагметов общая viewModels, т.е. viewModels каждого из них
        // привязана к родительскому фрагменту, которым является контроллер навигации
        //(в котором фрагменты сменяют друг друга), он у них общий
        //(поэтому в FeedFragment мы можем начать редактирование, а в NewPostFragmente редактирование закончить
        // обращение к ViewModel)
                // val viewModel: PostViewModel by viewModels(
                //            ownerProducer = ::requireParentFragment
                //        )

        использовать

                val viewModel: PostViewModel by activityViewModels()

        но тогда надо исправить и в NewPostFragment

        Это можно сделать если нужно сделать общую viewModel не только для фрагментов, но и для активити
         */


        // Просмотр видео по ссылке
        fun helpVideo(post: Post) {
            val src: String = post.video.toString()
            if (src.contains("youtu")) {
                val intent2 = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(src)
                }
                startActivity(intent2)
            } else {
                val intent = Intent(context, VideoActivity::class.java)
                intent.putExtra(Intent.EXTRA_TEXT, post.video.toString())
                startActivity(intent)
            }
        }


        val adapter = PostsAdapter(object : OnInteractionListener {
            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onImageVideo(post: Post) {
                helpVideo(post)
            }

            override fun onVideo(post: Post) {
                helpVideo(post)
                //viewModel.videoById(post)
            }

            override fun onEdit(post: Post) {

                // передадим текст выбранного для редактирования поста
                // из фрагмента feedFragment во фрагмент newPostFragment
                findNavController().navigate(

                    R.id.action_feedFragment_to_newPostFragment,
                    Bundle().apply {
                        textArg = post.content
                    }
                )

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

            override fun onContent(post: Post) {

                findNavController().navigate(
                    R.id.action_feedFragment_to_onePostFragment,
                    Bundle().apply {
                        textArg = post.id.toString()
                    }
                )

            }
        })

        /*
        Шаблон Observer — это поведенческий программный паттерн в Kotlin.
        Шаблон показывает определение механизма подписки,
        который уведомляет несколько объектов о любых изменениях, происходящих с наблюдаемым объектом.
         */
        binding.list.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
        }

        viewModel.edited.observe(viewLifecycleOwner) { post ->
            if (post.id == 0L) {
                return@observe
            }
        }




        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.newPostFragment)
        }

        return binding.root
    }

}


