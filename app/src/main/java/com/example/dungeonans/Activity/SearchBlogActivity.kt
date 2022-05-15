package com.example.dungeonans.Activity

import android.annotation.SuppressLint
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
import com.example.dungeonans.DataClass.SearchData
import com.example.dungeonans.R
import com.example.dungeonans.Retrofit.RetrofitManager
import com.example.dungeonans.Utils.Constants.TAG
import com.example.dungeonans.Utils.PrefManager
import com.example.dungeonans.Utils.RESPONSE_STATUS
import com.example.dungeonans.Adapter.BlogGridViewRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_search_result.*
import java.util.*
import kotlin.collections.ArrayList

class SearchBlogActivity: AppCompatActivity(),
    androidx.appcompat.widget.SearchView.OnQueryTextListener {

    // 데이터
    var photoList = ArrayList<BlogData>()

    private var searchHistoryList = ArrayList<SearchData>()

    private lateinit var blogGridRecyclerViewAdapter: BlogGridViewRecyclerViewAdapter
    private lateinit var mSearchViewET : EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        // 액티비티 내의 액션바 설정
        setSupportActionBar(top_tool_bar)

        this.blogGridRecyclerViewAdapter = BlogGridViewRecyclerViewAdapter()
        this.blogGridRecyclerViewAdapter.submitList(photoList)

        search_recycler_view.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        search_recycler_view.adapter = this.blogGridRecyclerViewAdapter

        // 저장된 검색 기록 가져오기
        this.searchHistoryList = PrefManager.getSearchHistoryList() as ArrayList<SearchData>

        this.searchHistoryList.forEach {
            Log.d("TAG", "저장된 검색 기록 ${it.term}, ${it.timestamp}")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.toolbar_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        val searchItem =  menu?.findItem(R.id.action_search)
        val mSearchView = searchItem?.actionView as androidx.appcompat.widget.SearchView

        searchItem.expandActionView()

        //this.mSearchView = menu?.findItem(R.id.action_search)?.actionView as androidx.appcompat.widget.SearchView

        mSearchView.apply {
            this.queryHint = "검색어를 입력해주세요."
//            this.isIconified = false
//            this.isFocusable = true
            this.requestFocusFromTouch()

            this.setOnQueryTextListener(this@SearchBlogActivity)

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
    @SuppressLint("NotifyDataSetChanged")
    override fun onQueryTextSubmit(query: String?): Boolean {

        if(!query.isNullOrEmpty()) {
            this.top_tool_bar.title = query

            var newSearchData = SearchData(term = query, timestamp = Date().toString())

            this.searchHistoryList.add(newSearchData)

            //PrefManager.storeSearchHistoryList(this.searchHistoryList)


            RetrofitManager.instance.searchBlogs(searchTerm = query, completion = {
                    responseState, responseDataArrayList ->

                when(responseState) {
                    RESPONSE_STATUS.OKAY -> {
                        Log.d(TAG, "api 호출 성공")
                        this.blogGridRecyclerViewAdapter.submitList(responseDataArrayList as ArrayList<BlogData>)
                        blogGridRecyclerViewAdapter.notifyDataSetChanged()

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