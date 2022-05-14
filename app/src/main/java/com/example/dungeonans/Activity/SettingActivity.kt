package com.example.dungeonans.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dungeonans.Fragment.SetAccountFragment
import com.example.dungeonans.Fragment.SetAppInfoFragment
import com.example.dungeonans.Fragment.SetSecurityFragement
import com.example.dungeonans.Fragment.SetSettingFragment
import com.example.dungeonans.R

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.empty_layout)

        var fragmentKey = intent.getStringExtra("key")
        when (fragmentKey) {
            "0" -> {
                supportFragmentManager.beginTransaction().replace(R.id.emptyLayout,SetSettingFragment()).commit()
            }
            "1" -> {
                supportFragmentManager.beginTransaction().replace(R.id.emptyLayout,SetSecurityFragement()).commit()
            }
            "2" -> {
                supportFragmentManager.beginTransaction().replace(R.id.emptyLayout,SetAccountFragment()).commit()
            }
            "3" -> {
                supportFragmentManager.beginTransaction().replace(R.id.emptyLayout,SetAppInfoFragment()).commit()
            }
        }
    }
}