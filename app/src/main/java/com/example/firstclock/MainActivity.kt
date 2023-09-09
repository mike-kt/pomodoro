package com.example.firstclock

import android.content.IntentSender.OnFinished
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var countdownTextView: TextView
    private lateinit var startButton: Button
    private var isCountdownRunning = false
    private val totalMillis = 2 * 60 * 1000 //2 minbutes in milliseconds
    private var remainingMillis = totalMillis
    private var countDownTimer: CountDownTimer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        countdownTextView = findViewById(R.id.countdownTextView)
        startButton = findViewById(R.id.btnStart)

        startButton.setOnClickListener {
            if (isCountdownRunning) {
                pauseCountdown()
            } else {
                startCountdown()
            }
        }
    }

    private fun startCountdown() {
        countDownTimer = object : CountDownTimer(totalMillis.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                isCountdownRunning = true
                remainingMillis = millisUntilFinished.toInt()
                updateConuntdownText()
            }

            override fun onFinish() {
                isCountdownRunning = false
                countdownTextView.text = "Tempo esgotado!"
            }
        }
        countDownTimer?.start()
        startButton.text = "pausar"
    }

    private fun pauseCountdown(){
        countDownTimer?.cancel()
        isCountdownRunning = false
        startButton.text = "iniciar"
    }


    private fun updateConuntdownText() {
        val minutes = (remainingMillis / 1000) / 60
        val seconds = (remainingMillis / 1000) % 60
        val formattedTime = String.format("%02d:%02d", minutes, seconds)
        countdownTextView.text = formattedTime
    }


}