package com.example.dungeonans.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dungeonans.Fragment.*
import com.example.dungeonans.R

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.empty_activity_layout)

        supportFragmentManager.beginTransaction().replace(R.id.emptyLayout, RegisterTermsFragment())
            .commit()
    }

    fun transFragEvent(transNum : Int) {
        val transaction = this.supportFragmentManager.beginTransaction()
        val termsFrag = RegisterTermsFragment()
        val confirmFrag = RegisterConfirmFragment()
        val successFrag = RegisterSuccessFragment()

        when(transNum) {
            0 -> transaction.replace(R.id.emptyLayout, termsFrag)
            1 -> transaction.replace(R.id.emptyLayout, confirmFrag)
            2 -> transaction.replace(R.id.emptyLayout, successFrag)
            10 -> {
                val loginIntent = Intent(this, LoginActivity::class.java) // 메인 페이지로 전환
                startActivity(loginIntent)
            }
        }
        transaction.commit()
    }

    fun showToastEvent(text : String, isShort : Boolean) {
        if (isShort) {
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, text, Toast.LENGTH_LONG).show()
        }
    }
}