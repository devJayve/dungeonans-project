package com.example.dungeonans.Activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dungeonans.Fragment.FindIdFragment
import com.example.dungeonans.Fragment.FindPwFragment
import com.example.dungeonans.R

class FindAccountActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.empty_activity_layout)

        val display: String? = intent.getStringExtra("find")

        when(display) {
            "id" -> {
                this.supportFragmentManager.beginTransaction()
                    .replace(R.id.emptyLayout, FindIdFragment())
                    .commit()

            }
            "pw" -> {
                this.supportFragmentManager.beginTransaction()
                    .replace(R.id.emptyLayout, FindPwFragment())
                    .commit()
            }
        }
    }

    fun moveBackPage() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

}