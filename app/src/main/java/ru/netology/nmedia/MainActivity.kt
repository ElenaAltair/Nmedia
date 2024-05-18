package ru.netology.nmedia

import android.os.Bundle
import android.util.Log
//import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
import ru.netology.nmedia.databinding.ActivityMainBinding
import java.math.RoundingMode

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое главное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия – помочь встать на путь роста и начать цепочку перемен. http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            likedByMe = false,
            likes = 0,
            web = 0,
            views = 0
        )

        with(binding) {
            author.text = post.author
            dataPublic.text = post.published
            content.text = post.content
            textStars.text = counter(post.likes)
            textWeb.text = counter(post.web)
            textViews.text = counter(post.views)

            //if (post.likedByMe) {
            //    heart.setImageResource(R.drawable.ic_heart_red_foreground)
            //}

            //root.setOnClickListener {
            //    Log.d("stuff", "stuff")
            //}

            heart.setOnClickListener {
                //Log.d("stuff", "like")
                post.likedByMe = !post.likedByMe
                heart.setImageResource(
                    if (post.likedByMe) R.drawable.ic_heart_red_foreground else R.drawable.ic_heart_foreground
                )
                if (post.likedByMe) post.likes++ else post.likes--
                textStars.text = counter(post.likes)
            }

            //avatar.setOnClickListener{
            //    Log.d("stuff", "avatar")
            //}

            //menu.setOnClickListener{
            //    Log.d("stuff", "menu")
            //}

            web.setOnClickListener {
                //Log.d("stuff", "web")
                post.web++
                textWeb.text = counter(post.web)
            }

            views.setOnClickListener {
                //Log.d("stuff", "views")
                post.views++
                textViews.text = counter(post.views)
            }


        }
        /*
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        */
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
}