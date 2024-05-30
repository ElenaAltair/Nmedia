package ru.netology.nmedia

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        val adapter = PostsAdapter(object : OnInteractionListener {
            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onEdit(post: Post) {
                //println("override fun onEdit!!!!!")
                viewModel.edit(post)
                //println("override fun onEdit!!!!!")
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onWeb(post: Post) {
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
            //println("viewModel.edited.observe")
            if (post.id == 0L) {
                return@observe
            }
            //println("viewModel.edited.observe")
            binding.group.visibility = View.VISIBLE
            with(binding.content2) {
                requestFocus()
                setText(post.content)
            }

        }



        binding.save.setOnClickListener {

            with(binding.content2) {
                if (text.isNullOrBlank()) {
                    Toast.makeText(
                        this@MainActivity,
                        "Content can't be empty",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                //println("binding.save.setOnClickListener")
                viewModel.changeContent(text.toString())
                viewModel.save()
                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)
                //println("binding.save.setOnClickListener")
            }
            binding.group.visibility = View.GONE
        }

        binding.content2.setOnClickListener() {
            binding.group.visibility = View.VISIBLE
        }

        binding.undo.setOnClickListener {
            viewModel.cancelEdit()
            binding.content2.setText("")
            binding.content2.clearFocus()
            AndroidUtils.hideKeyboard(binding.content2)
            binding.group.visibility = View.GONE
        }

    }


}


