package ru.netology.nmedia

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding
import java.math.RoundingMode


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        viewModel.data.observe(this) { post ->
            with(binding) {
                author.text = post.author
                dataPublic.text = post.published
                content.text = post.content
                textStars.text = counter(post.likes)
                textWeb.text = counter(post.web)
                textViews.text = counter(post.views)

                heart.setImageResource(
                    if (post.likedByMe) R.drawable.ic_heart_red_foreground else R.drawable.ic_heart_foreground
                )

            }

            binding.heart.setOnClickListener {
                viewModel.like()
            }
            binding.web.setOnClickListener {
                viewModel.web()
            }
            binding.views.setOnClickListener {
                viewModel.views()
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
