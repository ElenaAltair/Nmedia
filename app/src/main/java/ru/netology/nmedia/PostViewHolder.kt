package ru.netology.nmedia

import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.databinding.CardPostBinding
import java.math.RoundingMode

class PostViewHolder(
    private val binding: CardPostBinding,
    //private val onClickListener: OnClickListener
    private val onLikeListener: OnLikeListener,
    private val onWebListener: OnWebListener,
    private val onViewsListener: OnViewsListener,
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
                onLikeListener(post)
            }

            web.setOnClickListener {
                onWebListener(post)
            }
            views.setOnClickListener {
                onViewsListener(post)
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