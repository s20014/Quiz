package com.example.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.example.quiz.databinding.ActivityQuizViewBinding
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader


class Quiz_view : AppCompatActivity() {
    val testData = arrayListOf<ArrayList<String>>()
    var good = 0
    var totalQ = 1
    val n = 0..(testData.size-1)
    val qnum = n.shuffled()
    var i = 0
    var allTime = 0L
    var times = allTimer(100 * 1000L, 100L)
    var q_times = qtimer(10L * 1000L, 100L)



    inner class allTimer(millisInFuture: Long, countDownInterval: Long)
        : CountDownTimer(millisInFuture, countDownInterval) {
        var fastTime = millisInFuture
        override fun onTick(unko: Long) {
            val m = (fastTime - unko) / 1000L / 60L
            val s = (fastTime - unko) / 1000L % 60L
            binding.quizTimeCounter.text = "%1d:%2$02d".format(m,s)
            allTime = fastTime - unko
        }

        override fun onFinish() {

        }
    }
    inner class qtimer(at:Long, inter: Long) :CountDownTimer(at, inter) {
        override fun onTick(millisUntilFinished: Long) {
            binding.qTimer.progress = (millisUntilFinished / 100) .toInt()
        }

        override fun onFinish() {
            dialog(false)
            allfalse()
        }

    }

    private lateinit var binding: ActivityQuizViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var a: InputStream? = null
        var z: BufferedReader? = null

        try {
            try {
                a = assets.open("s20014.csv")
                z = BufferedReader(InputStreamReader(a))

                do {
                    var o = arrayListOf<String>()
                    val e = z.readLine()
                    for (i in e.split(",")) {
                        o.add(i)
                    }
                    println(o)
                    testData.add(o)
                } while (e != null)
            } finally {
                a?.close()
                z?.close()
            }
        } catch (e: Exception) {
            println(e)
        }




      // times.start()


        //セット
        next()

        //readCsv("s20014.csv")

        binding.Q1.setOnClickListener {
            clickAnswer(0)
        }
        binding.Q2.setOnClickListener {
            clickAnswer(0)
        }
        binding.Q3.setOnClickListener {
            clickAnswer(0)
        }
        binding.Q4.setOnClickListener {
            clickAnswer(0)
        }
        binding.nextButton.setOnClickListener {
            totalQ++
            i++

            if (totalQ > 10) {
                val intent = Intent(this, ResultActivity::class.java)
                times.cancel()
                intent.putExtra("GOOD", good)
                intent.putExtra("ALLTIME",allTime)
                startActivity(intent)

            } else {
                next()
            }

        }


    }

    private fun dialog(answer: Boolean) {
        if (answer) {
            AlertDialog.Builder(this)
                .setTitle("正解！")
                .setMessage("正解は${testData[qnum[i]][2]}です")
                .setPositiveButton("OK", null)
                .show()
        } else {
            AlertDialog.Builder(this)
                .setTitle("不正解")
                .setMessage("正解は${testData[qnum[i]][2]}です")
                .setPositiveButton("OK", null)
                .show()

        }

    }

    private fun allfalse() {
        binding.Q1.isEnabled = false
        binding.Q2.isEnabled = false
        binding.Q3.isEnabled = false
        binding.Q4.isEnabled = false
        binding.nextButton.isEnabled = true
    }

    private fun next() {
        Glide.with(this).load(resources.getIdentifier(testData[qnum[i]][1].split(".")[0], "drawable", packageName)).into(binding.imageView)
        val count = binding.quizNumber
        val question = binding.quizText
        val q1 = binding.Q1
        val q2 = binding.Q2
        val q3 = binding.Q3
        val q4 = binding.Q4
        val nextButton = binding.nextButton
        q1.isEnabled = true
        q2.isEnabled = true
        q3.isEnabled = true
        q4.isEnabled = true
        nextButton.isEnabled = false

        val list = listOf(2, 3, 4, 5).shuffled()
        count.text = "${totalQ}問目"
        question.text = testData[qnum[i]][0]
        q1.text = testData[qnum[i]][list[0]]
        q2.text = testData[qnum[i]][list[1]]
        q3.text = testData[qnum[i]][list[2]]
        q4.text = testData[qnum[i]][list[3]]
        q_times.start()



    }

    private fun clickAnswer(n:Int) {
        q_times.cancel()

        if (binding.Q1.text == testData[qnum[i]][2]) {
            dialog(true)
            good++

        } else {
            dialog(false)

        }
        allfalse()
    }


}


