package ru.netology.nmedia

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.databinding.CardPostBinding

typealias OnLikeListener = (post: Post) -> Unit
typealias OnWebListener = (post: Post) -> Unit
typealias OnViewsListener = (post: Post) -> Unit
typealias OnClickListener = (post: Post) -> Unit

class PostsAdapter(
    private val onClickListener: OnClickListener,
    //private val onLikeListener: OnLikeListener,
    //private val onWebListener: OnWebListener,
    //private val onViewsListener: OnViewsListener,
) : ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onClickListener)//, onLikeListener, onWebListener, onViewsListener
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }


}