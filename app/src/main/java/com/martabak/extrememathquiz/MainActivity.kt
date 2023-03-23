package com.martabak.extrememathquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity() {
    lateinit var additionButton : Button
    lateinit var multipliButton : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        additionButton = findViewById(R.id.buttonAddition)
        multipliButton = findViewById(R.id.buttonMultiplier)

        //Intent to move to other activity
        additionButton.setOnClickListener {
            val intent : Intent = Intent(this@MainActivity, GameActivity::class.java)
            intent.putExtra("Mode", 0)
            startActivity(intent)
        }

        multipliButton.setOnClickListener {
            val intent : Intent = Intent(this@MainActivity, GameActivity::class.java)
            intent.putExtra("Mode", 1)
            startActivity(intent)
        }
    }
}