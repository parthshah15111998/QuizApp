package com.example.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.quizapp.databinding.ActivityQuizQuestionBinding

class QuizQuestionActivity : AppCompatActivity() ,View.OnClickListener  {

    private var mCurrentPosition:Int=1
    private var mQuestionList:ArrayList<Question>? = null
    private var mSelectOption:Int=0
    lateinit var binding: ActivityQuizQuestionBinding
    private var mUserName:String?=null
    private var mCorrectAnswer:Int=0
    private var mTotalQuestion:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityQuizQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mUserName =intent.getStringExtra(Constants.USER_NAME)

        binding.tvOptionOne.setOnClickListener(this)
        binding.tvOptionTwo.setOnClickListener(this)
        binding.tvOptionThree.setOnClickListener(this)
        binding.tvOptionFour.setOnClickListener(this)
        binding.btnSubmit.setOnClickListener(this)

        mQuestionList = Constants.getQuestion()

        setQuestion()


    }

    private fun setQuestion() {
        defaultOption()
        val question: Question = mQuestionList!![mCurrentPosition - 1]
        binding.progressBar.progress = mCurrentPosition
        binding.tvProgress.text = "$mCurrentPosition/${binding.progressBar.max}"
        binding.tvQuestion.text = question.question
        binding.imgImage.setImageResource(question.image)
        binding.tvOptionOne.text = question.optionOne
        binding.tvOptionTwo.text = question.optionTwo
        binding.tvOptionThree.text = question.optionThree
        binding.tvOptionFour.text = question.optionFour

        if (mCurrentPosition == mQuestionList!!.size){
            binding.btnSubmit.text="FINISH"
        }else{
            binding.btnSubmit.text="SUBMIT"
        }


    }

    private fun defaultOption(){
        val options=ArrayList<TextView>()
        binding.tvOptionOne.let {
            options.add(0,it)
        }
        binding.tvOptionTwo.let {
            options.add(1,it)
        }
        binding.tvOptionThree.let {
            options.add(2,it)
        }
        binding.tvOptionFour.let {
            options.add(3,it)
        }

        for (option in options){
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface= Typeface.DEFAULT
            option.background=ContextCompat.getDrawable(this,R.drawable.default_option_border_bg)
        }
    }

    private fun selectOptionView(tv:TextView,selectOptioNum:Int){

        defaultOption()
        mSelectOption=selectOptioNum
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface,Typeface.BOLD)
        tv.background=ContextCompat.getDrawable(this,R.drawable.select_option_border)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.tv_optionOne->{
                binding.tvOptionOne.let{
                    selectOptionView(it,1)
                }
            }
            R.id.tv_optionTwo->{
                binding.tvOptionTwo.let{
                    selectOptionView(it,2)
                }
            }
            R.id.tv_optionThree->{
                binding.tvOptionThree.let{
                    selectOptionView(it,3)
                }
            }
            R.id.tv_optionFour->{
                binding.tvOptionFour.let{
                    selectOptionView(it,4)
                }
            }
            R.id.btn_submit->{
                if (mSelectOption == 0){
                    mCurrentPosition++

                    when{
                        mCurrentPosition <= mQuestionList!!.size->{
                            setQuestion()
                        }else->{
                           val intent=Intent(this,ResultActivity::class.java)
                           intent.putExtra(Constants.USER_NAME,mUserName)
                           intent.putExtra(Constants.TOTAL_QUESTION,mQuestionList?.size)
                           intent.putExtra(Constants.CORRECT_ANSWER,mCorrectAnswer)
                           startActivity(intent)
                           finish()
                        }
                    }
                }else{
                    val question=mQuestionList?.get(mCurrentPosition-1)
                    if (question!!.correctAnswer != mSelectOption){
                            answerView(mSelectOption,R.drawable.wrong_option_border_bg)
                    }else{
                        mCorrectAnswer++
                    }
                    answerView(question.correctAnswer,R.drawable.correct_option_border_bg )

                    if (mCurrentPosition == mQuestionList!!.size){
                        binding.btnSubmit.text="FINISH"
                    }else{
                        binding.btnSubmit.text="GO TO NEXT QUESTION"
                    }
                        mSelectOption = 0
                }
            }
        }
    }

    private fun answerView(answer:Int,drawerView:Int){
        when(answer){
            1->{
                binding.tvOptionOne.background=ContextCompat.getDrawable(this,drawerView)
            }
            2->{
                binding.tvOptionTwo.background=ContextCompat.getDrawable(this,drawerView)
            }
            3->{
                binding.tvOptionThree.background=ContextCompat.getDrawable(this,drawerView)
            }
            4->{
                binding.tvOptionFour.background=ContextCompat.getDrawable(this,drawerView)
            }
        }
    }

}
