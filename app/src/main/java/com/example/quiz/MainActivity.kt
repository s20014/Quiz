package com.example.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.quiz.databinding.ActivityMainBinding
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import org.apache.commons.lang3.mutable.Mutable
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startButton.setOnClickListener {
            startButton()
        }

    }

    private fun startButton() {
        val intent = Intent(this, Quiz_view::class.java)
        startActivity(intent)
    }


}