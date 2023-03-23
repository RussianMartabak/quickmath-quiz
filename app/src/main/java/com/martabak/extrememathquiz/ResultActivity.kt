package com.martabak.extrememathquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ResultActivity : AppCompatActivity() {
    lateinit var result : TextView
    lateinit var playAgain : Button
    lateinit var exitButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        result = findViewById(R.id.scoreText)
        playAgain = findViewById(R.id.againButton)
        exitButton = findViewById(R.id.exitButton)
        val score = intent.getIntExtra("score", 0)

        playAgain.setOnClickListener {
            val intent : Intent = Intent(this@ResultActivity, MainActivity::class.java)
            startActivity(intent)
            //must close activity
            finish()
        }

        exitButton.setOnClickListener {

            //close this activity
            finishAffinity()
        }

    }
}