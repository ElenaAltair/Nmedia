package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

/*
ActivityResultContract — это объект, который определяет тип ввода,
требуемый запущенной активностью для получения результата, а также тип вывода результата.

API Activity Result API предоставляет предопределённые контракты для общих действий намерения,
таких как съёмка изображения, выбор контента или запрос разрешений.
Также можно создавать собственные контракты для более конкретных потребностей.

Контракт — это класс, реализующий интерфейс ActivityResultContract<I,O>.
Где I определяет тип входных данных, необходимых для запуска Activity, а O — тип возвращаемого результата.

При создании контракта мы обязаны реализовать два его метода:
createIntent() — принимает входные данные и создает интент, который будет в дальнейшем запущен вызовом launch()
parseResult() — отвечает за возврат результата, обработку resultCode и парсинг данных
 */
class NewPostResultContract2 : ActivityResultContract<String, String?>() {

    override fun createIntent(context: Context, input: String): Intent {
        return Intent(context, NewPostFragment::class.java).putExtra(Intent.EXTRA_TEXT, input)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String? =
        if (resultCode == Activity.RESULT_OK) {
            intent?.getStringExtra(Intent.EXTRA_TEXT)
        } else {
            null
        }

}