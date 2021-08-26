package com.example.quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.quiz.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding:ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val good = intent.getIntExtra("GOOD",0)
        val allTimer = intent.getLongExtra("ALLTIME",0L)

        val m = allTimer / 1000L / 60L
        val s = allTimer / 1000L % 60L
        binding.maruQ.text = "${good}/10モン正解！"
        binding.totalTime.text = "%1d:%2$02d".format(m, s)
    }
}