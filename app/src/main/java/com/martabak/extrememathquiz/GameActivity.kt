package com.martabak.extrememathquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.util.*
import kotlin.random.Random

class GameActivity : AppCompatActivity() {
    lateinit var scoreText : TextView
    lateinit var lifeText : TextView
    lateinit var timeText : TextView
    lateinit var questionText : TextView
    lateinit var editTextAnswer : TextView
    lateinit var okButton : Button
    lateinit var nextButton : Button
    var mode = 0

    var correctAnswer = 0
    var userScore = 0
    var userLife = 3

    //create timer
    lateinit var timer : CountDownTimer
    private val startTimerInMs : Long = 10000
    var timeLeftInMs : Long = startTimerInMs




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)


        scoreText = findViewById(R.id.textViewScore)
        lifeText = findViewById(R.id.textViewLife)
        timeText = findViewById(R.id.textViewTime)
        questionText = findViewById(R.id.textViewQuiz)
        editTextAnswer = findViewById(R.id.editTextAnswer)
        okButton = findViewById(R.id.okButton)
        nextButton = findViewById(R.id.skipButton)

        supportActionBar!!.title = "Addition Quiz"
        //mode 0 addition, 1 is multiplication
        mode = intent.getIntExtra("Mode", 0)
        Log.d("ZAKY'S EYES ONLY", "MODE = $mode")
        gameContinue(mode)

        okButton.setOnClickListener {
            val input = editTextAnswer.text.toString()

            if(input == "") {
                Toast.makeText(applicationContext, "Provide the answer please!", Toast.LENGTH_SHORT)
                    .show()

            }
            else{
                val userAnswer : Int = input.toInt()

                pauseTimer()
                if (userAnswer == correctAnswer){
                    userScore += 10
                    scoreText.text = userScore.toString()
                    pauseTimer()
                    resetTimer()
                    gameContinue(mode)
                    editTextAnswer.text = ""

                }
                else {
                    userLife--
                    lifeText.text = userLife.toString()
                    Toast.makeText(applicationContext, "Wrong Answer", Toast.LENGTH_SHORT)
                        .show()

                    checkLife()
                }
            }
        }

        nextButton.setOnClickListener {
            pauseTimer()
            resetTimer()

            editTextAnswer.text = ""

            checkLife()

        }


    }

    override fun onPause() {
        super.onPause()
        Log.d("ZAKY'S EYES ONLY", "It's onPause")
    }

    override fun onStop() {
        super.onStop()
        finish()
    }
    fun gameContinue(mode : Int) {

        if (mode == 0) {
            var a = Random.nextInt(1, 100)
            var b = Random.nextInt(1, 100)

            questionText.text = "$a + $b"
            correctAnswer = a + b
        }
        else if (mode == 1) {
            var a = Random.nextInt(1, 10)
            var b = Random.nextInt(1, 99)

            questionText.text = "$a x $b"
            correctAnswer = a * b
        }


        startTimer()


    }
    //the object keyword used for creating an anonymous object from an abstract class,
    //because in this we can't do it the normal way (initialize/construct class
    //abstract class normally can't be constructed and must be inherited

    fun startTimer() {
        timer = object : CountDownTimer(timeLeftInMs, 1000){
            // this abstract class has some abstract fun
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMs = millisUntilFinished
                updateText()
            }

            override fun onFinish() {
                pauseTimer()
                resetTimer()
                updateText()

                userLife--
                lifeText.text = userLife.toString()
                Toast.makeText(this@GameActivity, "You Are Out of Time", Toast.LENGTH_SHORT).show()
                checkLife()
            }

        }.start()

    }
    fun checkLife(){
        if (userLife <= 0){
            Toast.makeText(this@GameActivity, "Game Over", Toast.LENGTH_LONG)
                .show()
            val intent = Intent(this@GameActivity, ResultActivity::class.java)
            // this will ensure when the next activity launch it will be the sole activity in the task
            // so exiting that activity will end all (at least that's the goal)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            //send value to other activity (result)
            intent.putExtra("score", userScore)
            startActivity(intent)
            //close this activity
            finish()
        }
        else {
            gameContinue(mode)
        }
    }

    fun updateText(){
        val remainingTime : Int = timeLeftInMs.toInt() / 1000
        timeText.text = String.format("%2d", remainingTime)


    }

    fun pauseTimer(){
        timer.cancel()
    }

    fun resetTimer(){
        timeLeftInMs = startTimerInMs
        updateText()
    }
}