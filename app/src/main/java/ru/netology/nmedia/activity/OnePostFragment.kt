package ru.netology.nmedia.activity

import android.content.Intent
import android.os.Bundle
import android.net.Uri

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import ru.netology.nmedia.activity.NewPostFragment.Companion.textArg
import ru.netology.nmedia.adapter.PostViewHolder
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.FragmentOnePostBinding
import ru.netology.nmedia.dto.OnInteractionListener
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel


class OnePostFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentOnePostBinding.inflate(inflater, container, false)
        val idF = arguments?.textArg
        val viewModel: PostViewModel by activityViewModels()

        var dataL: LiveData<List<Post>> = viewModel.getAll()
        // находим нужный пост по id
        val post: Post? = dataL.map {
            it.find { it.id.toInt() == idF?.toInt() }
        }.value


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

        val onInteractionListener = object : OnInteractionListener {
            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }


            override fun onImageVideo(post: Post) {
                helpVideo(post)
            }

            override fun onVideo(post: Post) {
                helpVideo(post)
            }

            override fun onEdit(post: Post) {
                // передадим текст выбранного для редактирования поста
                // из фрагмента feedFragment во фрагмент newPostFragment
                findNavController().navigate(
                    R.id.newPostFragment,
                    Bundle().apply {
                        textArg = post.content
                    }
                )
                viewModel.edit(post)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
                findNavController().navigateUp() //закрыть текущий фрагмент и вернуться к предыдущему
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

        }

        var pvh = PostViewHolder(binding = binding.postOne, onInteractionListener)
        pvh.run {
            if (post != null) {
                bind(post)
            }
        }

        
        viewModel.data.observe(viewLifecycleOwner) {
            var data: LiveData<List<Post>> = viewModel.getAll()
            // находим нужный пост по id
            val post: Post? = data.map {
                it.find { it.id.toInt() == idF?.toInt() }
            }.value
            binding.postOne.heart.text = post?.likes.toString()
            binding.postOne.web.text = post?.web.toString()
            binding.postOne.views.text = post?.views.toString()
            binding.postOne.content.text = post?.content
            binding.postOne.views.isChecked = post?.viewsByMe == true
            binding.postOne.web.isChecked = post?.webByMe == true
        }

        return binding.root
    }

}