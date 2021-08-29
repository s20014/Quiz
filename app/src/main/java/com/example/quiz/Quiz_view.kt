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
    var alldata = arrayListOf<ArrayList<String>>()
    var testData = arrayListOf<ArrayList<String>>()
    var good = 0
    var totalQ = 1
    val n = 0..10
    val qnum = n.shuffled()
    var i = 0
    var allTime = 0L
    var times = allTimer(100 * 1000L, 100L, allTime)
    var q_times = qtimer(10L * 1000L, 1000L)
    val studentName = arrayListOf<String>("sample","s20001","s20002","s20003","s20004","s20005","s20006","s20007","s20008","s20009","s20010","s20011","s20012","s200013","s20014","s20015","s20016","s20017","s20018", "s20019", "s20020","s20021","s20022","s20023","s20024")


    inner class allTimer(millisInFuture: Long, countDownInterval: Long , a:Long) :
        CountDownTimer(millisInFuture, countDownInterval) {
        var fastTime = millisInFuture
        var a = a



        override fun onTick(unko: Long) {
            val m = (fastTime - (unko - a) ) / 1000L / 60L
            val s = (fastTime - (unko - a) ) / 1000L % 60L
            binding.quizTimeCounter.text = "%1d:%2$02d".format(m, s)
            allTime = fastTime - (unko - a)

        }

        override fun onFinish() {
        }
    }

    inner class qtimer(at: Long, inter: Long) : CountDownTimer(at, inter) {
        override fun onTick(millisUntilFinished: Long) {
            binding.qTimer.progress = (millisUntilFinished / 100).toInt()
        }

        override fun onFinish() {
            times.cancel()
            times = allTimer(100 * 1000L, 100L, allTime)
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

        for (name in studentName) {
            try {
                try {
                    a = assets.open("${name}.csv")
                    z = BufferedReader(InputStreamReader(a))
                    var nnn = 0

                    do {
                        var o = arrayListOf<String>()
                        val e = z.readLine()
                        for (i in e.split(",")) {
                           o.add(i)
                        }
                        if (nnn==0) {
                            nnn++
                        }else {
                            alldata.add(o)
                        }
                    } while (e != null)
                } finally {
                    a?.close()
                    z?.close()
                }
            } catch (e: Exception) {
                //println(e)
            }
       }

        alldata.shuffle()
        testData = alldata




        //セット
        next()



        binding.Q1.setOnClickListener {
            clickAnswer(0)
        }
        binding.Q2.setOnClickListener {
            clickAnswer(1)
        }
        binding.Q3.setOnClickListener {
            clickAnswer(2)
        }
        binding.Q4.setOnClickListener {
            clickAnswer(3)

        }
        binding.nextButton.setOnClickListener {
            totalQ++
            i++

            if (totalQ > 10) {
                val intent = Intent(this, ResultActivity::class.java)
                times.cancel()
                intent.putExtra("GOOD", good)
                intent.putExtra("ALLTIME", allTime)
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
        println(testData[qnum[i]])
        times.start()
        Glide.with(this).load(
            resources.getIdentifier(
                testData[qnum[i]][1].split(".")[0],
                "drawable",
                packageName
            )
        ).into(binding.imageView)

        binding.Q1.isEnabled = true
        binding.Q2.isEnabled = true
        binding.Q3.isEnabled = true
        binding.Q4.isEnabled = true
        binding.nextButton.isEnabled = false

        val list = listOf(2, 3, 4, 5).shuffled()
        binding.quizNumber.text = "${totalQ}問目"
        binding.quizText.text = testData[qnum[i]][0]
        binding.Q1.text = testData[qnum[i]][list[0]]
        binding.Q2.text = testData[qnum[i]][list[1]]
        binding.Q3.text = testData[qnum[i]][list[2]]
        binding.Q4.text = testData[qnum[i]][list[3]]
        q_times.start()


    }

    private fun clickAnswer(n: Int) {
        q_times.cancel()
        times.cancel()
        times = allTimer(100 * 1000L, 100L, allTime)
        val Q1 = binding.Q1
        val Q2 = binding.Q2
        val Q3 = binding.Q3
        val Q4 = binding.Q4

        var button = arrayListOf(Q1, Q2, Q3, Q4)

        if (button[n].text == testData[qnum[i]][2]) {
            dialog(true)
            good++

        } else {
            dialog(false)

        }
        allfalse()
    }
}


