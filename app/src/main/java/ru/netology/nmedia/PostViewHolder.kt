package ru.netology.nmedia

import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.databinding.CardPostBinding
import java.math.RoundingMode

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onInteractionListener: OnInteractionListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            author.text = post.author
            dataPublic.text = post.published
            content.text = post.content
            textStars.text = counter(post.likes)
            textWeb.text = counter(post.web)
            textViews.text = counter(post.views)

            heart.setImageResource(
                if (post.likedByMe) R.drawable.ic_heart_red_foreground else R.drawable.ic_heart_foreground
            )
            heart.setOnClickListener {
                onInteractionListener.onLike(post)
                //onLikeListener(post)
            }

            web.setOnClickListener {
                onInteractionListener.onWeb(post)
                //onWebListener(post)
            }
            views.setOnClickListener {
                onInteractionListener.onViews(post)
                //onViewsListener(post)
            }

            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                onInteractionListener.onRemove(post)
                                true
                            }

                            R.id.edit -> {
                                onInteractionListener.onEdit(post)
                                true
                            }

                            R.id.undoEdit -> {
                                onInteractionListener.onUndoEdit(post)
                                true
                            }

                            else -> false
                        }
                    }
                }.show()
            }
        }
    }
}

private fun counter(num: Long): String {
    var numEnd: String = num.toString()
    if (num in 1000..1099) {
        numEnd = "1K"
    } else if (num in 1100..9999) {
        numEnd = ((num.toDouble() / 1000).toBigDecimal().setScale(1, RoundingMode.DOWN)
            .toDouble()).toString() + "K"
    } else if (num in 10000..999999) {
        numEnd = (num / 1000).toString() + "K"
    } else if (num in 1000000..1099999) {
        numEnd = "1M"
    } else if (num >= 1100000) {
        numEnd = ((num.toDouble() / 1000000).toBigDecimal().setScale(1, RoundingMode.DOWN)
            .toDouble()).toString() + "M"
    }
    return numEnd
}