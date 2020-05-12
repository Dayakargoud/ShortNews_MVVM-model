package com.dayakar.shortnews.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dayakar.shortnews.R
import kotlinx.coroutines.*

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
           CoroutineScope(Dispatchers.Main).launch {
               updateUI()
           }


    }


    private suspend fun updateUI() {
        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)
            startNextActivity()

        }
    }

    private fun startNextActivity() {
        Intent(this, MainActivity::class.java).also { startActivity(it) }
        finish()
    }



}