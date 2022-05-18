package com.example.dungeonans.Activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.dungeonans.Fragment.*
import com.example.dungeonans.R

class UserProfileEditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.empty_activity_layout)

        supportFragmentManager.beginTransaction().replace(R.id.emptyLayout, UserProfileEditFragment())
            .commit()

        /**
         * 처리해줘야 할 것
         * 1. 프로필 사진, 소개, 기술 스택 등 기존에 null 값이 갱신되었을 때 버튼 text 추가 => 수정
         * 2. 기존에 User 정보가 있을 때 api 갱신
         * 3. 스택 임의 추가
         */

    }

    fun transFragEvent(mFrag : Fragment) {
        this.supportFragmentManager.beginTransaction().replace(
            R.id.emptyLayout,
            mFrag
        ).commit()
    }

    fun moveBackEvent() {
        val loginIntent = Intent(this, MainActivity::class.java) // 메인 페이지로 전환
        startActivity(loginIntent)
    }

    private fun editAccountStack() {
        AlertDialog.Builder(this)
            .setView(R.layout.fragment_edit_stack)
            .setCancelable(true)
            .show()
            .also { alertDialog ->
                if (alertDialog == null) {
                    return@also
                }

            }

    }
}