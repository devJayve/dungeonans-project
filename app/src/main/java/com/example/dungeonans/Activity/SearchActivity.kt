package com.example.dungeonans.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.dungeonans.R
import com.example.dungeonans.Retrofit.RetrofitManager
import com.example.dungeonans.Utils.Constants.TAG
import com.example.dungeonans.Utils.RESPONSE_STATUS
import com.example.dungeonans.Utils.SEARCH_TYPE
import com.example.dungeonans.Utils.onMyTextChanged
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class SearchActivity : AppCompatActivity() {

    private var currentSearchType: SEARCH_TYPE = SEARCH_TYPE.BLOG //검색 타입을 블로그 포스팅으로 기본 설정

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_activity_layout)

        val searchTermTextLayout = findViewById<TextInputLayout>(R.id.search_term_text_layout)
        val searchTermEditText = findViewById<TextInputEditText>(R.id.search_term_edit_text)
        val frameSearchBtn = findViewById<FrameLayout>(R.id.frame_search_btn)
        val searchBtn = findViewById<Button>(R.id.btn_search)

        val searchTermRadioGroup = findViewById<RadioGroup>(R.id.search_term_radio_group)
        searchTermRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId) {
                R.id.photo_search_radio_btn -> {
                    Log.d(TAG, "사진검색버튼")
                    searchTermTextLayout.hint = "사진검색"
                    searchTermTextLayout.startIconDrawable = resources.getDrawable(R.drawable.ic_baseline_photo_library_24, resources.newTheme())
                    this.currentSearchType = SEARCH_TYPE.BLOG
                }
                R.id.user_search_radio_btn -> {
                    searchTermTextLayout.hint = "사용자검색"
                    searchTermTextLayout.startIconDrawable = resources.getDrawable(R.drawable.ic_baseline_person_24, resources.newTheme())
                    this.currentSearchType = SEARCH_TYPE.PROFILE
                }
            }
        }

        // 텍스트가 변경이 되었을 때
        searchTermEditText.onMyTextChanged {
            // 입력된 글자가 하나라도 있다면 검색 버튼을 보여줌
            if(it.toString().count() > 0) {
                frameSearchBtn.visibility = View.VISIBLE
            } else {
                frameSearchBtn.visibility = View.GONE
            }
        }

        searchBtn.setOnClickListener {
            Log.d(TAG, "MainActivity - 검색 버튼이 클릭되었다. / currentSearchType : $currentSearchType")

            val userSearchInput = searchTermEditText.text.toString()
            // 검색 api 호출
            RetrofitManager.instance.searchBlogs(searchTerm = userSearchInput, completion = {
                    responseState, responseDataArrayList ->

                when(responseState) {
                    RESPONSE_STATUS.OKAY -> {
                        Log.d(TAG, "api 호출 성공")

                        val intent = Intent(this, SearchBlogActivity::class.java)
                        val bundle = Bundle()
                        bundle.putSerializable("blog_array_list",responseDataArrayList)
                        intent.putExtra("array_bundle", bundle)
                        intent.putExtra("search_term", userSearchInput)
                        startActivity(intent)

                    }
                    RESPONSE_STATUS.FAIL -> {
                        Toast.makeText(this, "api 호출 에러입니다.", Toast.LENGTH_SHORT).show()
                        Log.d(TAG, "api 호출 실패 : $responseDataArrayList")
                    }

                    RESPONSE_STATUS.NO_CONTENT -> {
                        Toast.makeText(this, "검색 결과가 없습니다.", Toast.LENGTH_SHORT).show()
                    }
                }

            })
            this.handleSearchBtnUi()
        }
    }

    private fun handleSearchBtnUi() {
        val btnProgressBar = findViewById<ProgressBar>(R.id.btn_progress)
        val searchBtn = findViewById<Button>(R.id.btn_search)

        btnProgressBar.visibility = View.VISIBLE
        searchBtn.visibility = View.GONE
        Handler(Looper.getMainLooper()).postDelayed({
            btnProgressBar.visibility = View.GONE
            searchBtn.visibility = View.VISIBLE
        }, 1500)


    }

}