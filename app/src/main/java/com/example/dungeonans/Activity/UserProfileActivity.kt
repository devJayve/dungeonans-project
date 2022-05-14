package com.example.dungeonans.Activity

import android.app.Activity
import android.os.Bundle
import android.widget.ImageButton
import com.example.dungeonans.R

class UserProfileActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.other_profile_activity)
        initEvent()
    }

    private fun initEvent() {
        var exitBtn : ImageButton = findViewById(R.id.exitBtn)
        exitBtn.setOnClickListener {
            finish()
        }
    }
}