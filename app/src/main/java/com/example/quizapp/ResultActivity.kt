package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.quizapp.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvName.text=intent.getStringExtra(Constants.USER_NAME)

        val totalQuestion=intent.getIntExtra(Constants.TOTAL_QUESTION,0)
        val correctAnswer=intent.getIntExtra(Constants.CORRECT_ANSWER,0)

        binding.tvScore.text="Your Score is $correctAnswer out of $totalQuestion"

        binding.btnFinish.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

    }
}