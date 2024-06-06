package ru.netology.nmedia

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.databinding.CardPostBinding
import java.math.RoundingMode
import java.util.concurrent.TimeUnit
import android.widget.TextView

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onInteractionListener: OnInteractionListener,
) : RecyclerView.ViewHolder(binding.root) {


    fun bind(post: Post) {

        binding.apply {
            author.text = post.author
            dataPublic.text = post.published
            content.text = post.content
            //textStars.text = counter(post.likes)
            //textWeb.text = counter(post.web)
            //textViews.text = counter(post.views)

            heart.text = counter(post.likes)
            web.text = counter(post.web)
            views.text = counter(post.views)

            /*
            heart.setImageResource(
                if (post.likedByMe) R.drawable.ic_heart_red_foreground else R.drawable.ic_heart_foreground
            )*/
            heart.isChecked = post.likedByMe
            web.isChecked = post.webByMe
            views.isChecked = post.viewsByMe

            if(post.video!=null && post.video != ""){
                avatar1.visibility = View.VISIBLE
                video.visibility = View.VISIBLE
            }else{
                avatar1.visibility = View.GONE
                video.visibility = View.INVISIBLE
            }

            avatar1.setOnClickListener{
                onInteractionListener.onImageVideo(post)
            }
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

            video.setOnClickListener{
                onInteractionListener.onVideo(post)
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