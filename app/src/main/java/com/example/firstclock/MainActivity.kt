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
    private lateinit var btnReset: Button

    private var isCountdownRunning = false
    private val totalMillis = 25 * 60 * 1000 //25 minbutes in milliseconds
    private val secondCountdownMillis = 5 * 60 * 1000 //5 minutes in milliseconds

    private val testSeconds = 1 * 60 * 1000

    private var remainingMillis = totalMillis
    private var countDownTimer: CountDownTimer? = null

    private var pausedMillis: Long = 0
    private var isSecondCountdown = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        countdownTextView = findViewById(R.id.countdownTextView)
        startButton = findViewById(R.id.btnStart)
        btnReset = findViewById(R.id.btnReset)

        startButton.setOnClickListener {
            if (isCountdownRunning) {
                pauseCountdown()
            } else {
                if (!isSecondCountdown) {
                    startCountdown(testSeconds.toLong())
                } else {
                    startCountdown(secondCountdownMillis.toLong())
                }
            }
        }

        btnReset.setOnClickListener {
            resetCountdown()
        }
    }

    private fun startCountdown(durationMillis: Long) {
        countDownTimer?.cancel()


        countDownTimer = object : CountDownTimer(
            if (pausedMillis > 0) pausedMillis else durationMillis,
            1000
        ) { //verify paused time
            override fun onTick(millisUntilFinished: Long) {
                isCountdownRunning = true
                remainingMillis = millisUntilFinished.toInt()
                updateCountdownText()
            }

            override fun onFinish() {
                isCountdownRunning = false
                if (!isSecondCountdown) {
                    countdownTextView.text = "Tempo esgotado!"
                    isSecondCountdown = true
                    startCountdown(secondCountdownMillis.toLong())
                } else {
                    countdownTextView.text = "Tempo de descanço esgotado!"
                }
            }
        }
        countDownTimer?.start()
        startButton.text = "pausar"
    }

    private fun pauseCountdown() {
        countDownTimer?.cancel()
        isCountdownRunning = false
        startButton.text = "iniciar"
        pausedMillis = remainingMillis.toLong() //saved the ramain time
    }


    private fun updateCountdownText() {
        val minutes = (remainingMillis / 1000) / 60
        val seconds = (remainingMillis / 1000) % 60
        val formattedTime = String.format("%02d:%02d", minutes, seconds)
        countdownTextView.text = formattedTime
    }

    private fun resetCountdown() {
        countDownTimer?.cancel()
        isCountdownRunning = false
        isSecondCountdown = false
        remainingMillis = totalMillis
        updateCountdownText()
        startButton.text = "iniciar"
    }

}