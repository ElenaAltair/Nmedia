package ru.netology.nmedia

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.databinding.ActivityIntentHandlerBinding

class IntentHandlerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityIntentHandlerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent?.let{
            if(it.action != Intent.ACTION_SEND){
                return@let
            }
            val text = it.getStringExtra(Intent.EXTRA_TEXT)
            if(text.isNullOrBlank()){
                Snackbar.make(binding.root, R.string.error_empty_content, LENGTH_INDEFINITE ) //Content can't be empty
                    .setAction(android.R.string.ok){
                        finish()
                    }.show()
                return@let
            }

            //Log.d("XXX", text)
            binding.editTextSend.setText(text)
            binding.ok.setOnClickListener {

                val intent = Intent(this@IntentHandlerActivity, MainActivity::class.java)
                if (binding.editTextSend.text.isNullOrBlank()) {
                    setResult(Activity.RESULT_CANCELED, intent)
                } else {
                    val content = binding.editTextSend.text.toString()
                    intent.putExtra(Intent.EXTRA_TEXT, content)
                    setResult(Activity.RESULT_OK, intent)
                    startActivity(intent)
                }
                finish()
            }
        }
    }
}