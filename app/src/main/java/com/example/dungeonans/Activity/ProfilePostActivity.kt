package com.example.dungeonans.Activity

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dungeonans.Fragment.MyAllBlogShowFragment
import com.example.dungeonans.Fragment.MyAllCommentShowFragment
import com.example.dungeonans.Fragment.MyAllPostShowFragment
import com.example.dungeonans.R

class ProfilePostActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.empty_layout)

        when (intent.getStringExtra("key")) {
            "1" -> supportFragmentManager.beginTransaction()
                .replace(R.id.emptyLayout, MyAllPostShowFragment()).commit()
            "2" -> supportFragmentManager.beginTransaction()
                .replace(R.id.emptyLayout, MyAllCommentShowFragment()).commit()
            "3" -> supportFragmentManager.beginTransaction()
                .replace(R.id.emptyLayout, MyAllBlogShowFragment()).commit()
        }
    }
}