package com.example.firstclock

import android.content.IntentSender.OnFinished
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var countdownTextView: TextView
    private lateinit var txtInfo: TextView
    private lateinit var startButton: ImageView
    private lateinit var btnReset: ImageView

    private lateinit var progressBar: ProgressBar

    private var isCountdownRunning = false
    private val totalMillis = 25 * 60 * 1000 //25 minbutes in milliseconds
    private val secondCountdownMillis = 5 * 60 * 1000 //5 minutes in milliseconds


    private var remainingMillis = totalMillis
    private var countDownTimer: CountDownTimer? = null

    private var pausedMillis: Long = 0
    private var isSecondCountdown = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        countdownTextView = findViewById(R.id.countdownTextView)
        txtInfo = findViewById(R.id.txtInfo)
        startButton = findViewById(R.id.btnStart)
        btnReset = findViewById(R.id.btnReset)

        progressBar = findViewById(R.id.progressBar)
        progressBar.max = totalMillis.toInt()


        startButton.setOnClickListener {
            if (isCountdownRunning) {
                pauseCountdown()
            } else {
                if (!isSecondCountdown) {
                    startCountdown(totalMillis.toLong())
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
                progressBar.progress = remainingMillis
            }

            override fun onFinish() {
                isCountdownRunning = false
                if (!isSecondCountdown) {
                    txtInfo.text = "Rest time"
                    isSecondCountdown = true
                    startCountdown(secondCountdownMillis.toLong())
                } else {
                    txtInfo.text = "Tempo de descanço esgotado!"
                }
            }
        }
        countDownTimer?.start()
        startButton.setImageResource(R.drawable.pausa)
    }

    private fun pauseCountdown() {
        countDownTimer?.cancel()
        isCountdownRunning = false
        startButton.setImageResource(R.drawable.play)
        pausedMillis = remainingMillis.toLong() //saved the ramain time
    }


    private fun updateCountdownText() {
        val minutes = (remainingMillis / 1000) / 60
        val seconds = (remainingMillis / 1000) % 60
        val formattedTime = String.format("%02d:%02d", minutes, seconds)
        countdownTextView.text = formattedTime
    }

    private fun resetCountdown() {
        if (isCountdownRunning){
            countDownTimer?.cancel()
        }
        isCountdownRunning = false
        isSecondCountdown = false
        remainingMillis = totalMillis
        updateCountdownText()
        pausedMillis = 0
        progressBar.progress = totalMillis
        startButton.setImageResource(R.drawable.play)
    }

}