package com.example.dungeonans.Activity

import android.app.SearchManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dungeonans.BlogData
import com.example.dungeonans.R
import com.example.dungeonans.Utils.Constants.TAG
import com.example.dungeonans.recylcerview.BlogGridViewRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_search_result.*

class SearchResultActivity: AppCompatActivity(), androidx.appcompat.widget.SearchView.OnQueryTextListener{

    // 데이터
    var photoList = ArrayList<BlogData>()
    // 어뎁터
    private lateinit var blogGridRecyclerViewAdapter: BlogGridViewRecyclerViewAdapter

    // 서치뷰
    private lateinit var mSearchView: androidx.appcompat.widget.SearchView

    // 서치뷰 ET
    private lateinit var mSearchViewET : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        Log.d(TAG, "searchResultActivity" )

        val bundle = intent.getBundleExtra("array_bundle")
        val searchTerm = intent.getStringExtra("search_term")

        photoList = bundle?.getSerializable("blog_array_list") as ArrayList<BlogData>

        Log.d(TAG, "searchTerm : $searchTerm, photoList.count(): ${photoList.count()}" )

        top_tool_bar.title = searchTerm

        // 액티비티 내의 액션바 설정
        setSupportActionBar(top_tool_bar)

        this.blogGridRecyclerViewAdapter = BlogGridViewRecyclerViewAdapter()
        this.blogGridRecyclerViewAdapter.submitList(photoList)

        search_recycler_view.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        search_recycler_view.adapter = this.blogGridRecyclerViewAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater = menuInflater

        inflater.inflate(R.menu.toolbar_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        this.mSearchView = menu?.findItem(R.id.action_search)?.actionView as androidx.appcompat.widget.SearchView

        this.mSearchView.apply {
            this.queryHint = "검색어를 입력해주세요."

            this.setOnQueryTextListener(this@SearchResultActivity)

            this.setOnQueryTextFocusChangeListener { _, hasExpanded ->
                when(hasExpanded) {
                    true -> { // 서치뷰 열림
                        linear_search_history_view.visibility = View.VISIBLE
                    }
                    false -> { // 서치뷰 닫힘
                        linear_search_history_view.visibility = View.INVISIBLE
                    }
                }
            }
            mSearchViewET = this.findViewById(androidx.appcompat.R.id.search_src_text)
        }
        this.mSearchViewET.apply {
            this.filters = arrayOf(InputFilter.LengthFilter(12))
            this.setTextColor(Color.BLACK)
            this.setHintTextColor(Color.GRAY)
        }

        return true
    }

    // 서치뷰 검색어 입력
    override fun onQueryTextSubmit(query: String?): Boolean {

        if(!query.isNullOrEmpty()) {
            this.top_tool_bar.title = query

            // 검색어 저장
        }
//
//        this.mSearchView.setQuery("", false)
//        this.mSearchView.clearFocus() // 키보드가 내려감
        this.top_tool_bar.collapseActionView() // action View 가 닫힘

        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        val userInputText = newText.let {
            it
        }?: "" // 값이 비어 있으면 빈 값

        if (userInputText.count() == 12) {
            Toast.makeText(this, "검색어는 12자까지만 입력 가능합니다.", Toast.LENGTH_SHORT).show()
        }

        return true
    }

}