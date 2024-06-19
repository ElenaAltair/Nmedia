package ru.netology.nmedia.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.activity.NewPostFragment.Companion.textArg
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityAppBinding

class AppActivity : AppCompatActivity() { //получение текста с внешнего источника
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent?.let {
            if (it.action != Intent.ACTION_SEND) {
                return@let
            }

            val text = it.getStringExtra(Intent.EXTRA_TEXT)
            if (text.isNullOrBlank()) {
                Snackbar.make(
                    binding.root,
                    R.string.error_empty_content,
                    LENGTH_INDEFINITE
                ) //Content can't be empty
                    .setAction(android.R.string.ok) {
                        finish()
                    }.show()
                return@let
            }

            //здесь мы полученный текст из внешнего источника отправляем на newPostFragment
            findNavController(R.id.nav_host).navigate(
                R.id.action_feedFragment_to_newPostFragment,
                // при передачи данных туда, мы их записываем во второй параметр функции navigate
                // передачу обратно мы не реализуем, т.к.
                // у фрагментов общая ViewModel и обработку результата можно делать в фрагменте,
                // который фактически мы вызываем (в данном случае во фрагменте в NewPostFragment
                // смотри там листенер при нажатии на кнопку Ok)
                Bundle().apply {
                    textArg = text
                }
            )

        }
    }
}