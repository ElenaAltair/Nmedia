package ru.netology.nmedia.adapter

import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.OnInteractionListener
import ru.netology.nmedia.dto.Post
import java.math.RoundingMode

class PostViewHolder(
    // этот класс отвечает за отрисовку одного поста
    private val binding: CardPostBinding,
    private val onInteractionListener: OnInteractionListener,
) : RecyclerView.ViewHolder(binding.root) {


    fun bind(post: Post) {

        binding.apply {
            author.text = post.author
            dataPublic.text = post.published
            content.text = post.content

            heart.text = counter(post.likes)
            web.text = counter(post.web)
            views.text = counter(post.views)

            heart.isChecked = post.likedByMe
            web.isChecked = post.webByMe
            views.isChecked = post.viewsByMe

            if (post.video != null && post.video != "") {
                avatar1.visibility = View.VISIBLE
                video.visibility = View.VISIBLE
            } else {
                avatar1.visibility = View.GONE
                video.visibility = View.INVISIBLE
            }

            avatar1.setOnClickListener {
                onInteractionListener.onImageVideo(post)
            }
            heart.setOnClickListener {
                onInteractionListener.onLike(post)
            }

            web.setOnClickListener {
                onInteractionListener.onWeb(post)
            }

            views.setOnClickListener {
                onInteractionListener.onViews(post)
            }

            video.setOnClickListener {
                onInteractionListener.onVideo(post)
            }

            content.setOnClickListener {
                onInteractionListener.onContent(post)
            }

            menu.setOnClickListener {

                val popupMenu: PopupMenu = PopupMenu(it.context, it)


                popupMenu.menuInflater.inflate(R.menu.options_post, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->

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
                    true
                })
                popupMenu.setOnDismissListener {
                    menu.isChecked = false
                }

                popupMenu.show()

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