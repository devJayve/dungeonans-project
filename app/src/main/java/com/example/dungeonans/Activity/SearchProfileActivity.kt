package com.example.dungeonans.Activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dungeonans.Adapter.BlogGridViewRecyclerViewAdapter
import com.example.dungeonans.Adapter.SearchProfileRVAdapter
import com.example.dungeonans.BlogData
import com.example.dungeonans.DataClass.SearchData
import com.example.dungeonans.DataClass.SearchProfileData
import com.example.dungeonans.R
import com.example.dungeonans.Retrofit.RetrofitManager
import com.example.dungeonans.Utils.Constants
import com.example.dungeonans.Utils.RESPONSE_STATUS
import kotlinx.android.synthetic.main.activity_search_result.*
import kotlinx.android.synthetic.main.answer_fragment.view.*
import java.util.*
import kotlin.collections.ArrayList

class SearchProfileActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    var photoList = ArrayList<BlogData>()
    var profileList = ArrayList<SearchProfileData>()
    var content = 100

    private var searchHistoryList = ArrayList<SearchData>()

    private lateinit var searchProfileRVAdapter: SearchProfileRVAdapter
    private lateinit var blogGridRecyclerViewAdapter: BlogGridViewRecyclerViewAdapter
    private lateinit var mSearchView: SearchView
    private lateinit var mSearchViewET : EditText



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        content = intent.getIntExtra("content",0)
        Log.d("TAG", "content is $content")

        // 액티비티 내의 액션바 설정
        setSupportActionBar(top_tool_bar)

        when (content) {
            2 -> {
                this.blogGridRecyclerViewAdapter = BlogGridViewRecyclerViewAdapter()
                this.blogGridRecyclerViewAdapter.submitList(photoList)

                search_recycler_view.apply {
                    layoutManager = GridLayoutManager(
                        SearchProfileActivity(),
                        2,
                        GridLayoutManager.VERTICAL,
                        false
                    )
                    adapter = blogGridRecyclerViewAdapter
                }
            }
            3 -> {
                this.searchProfileRVAdapter = SearchProfileRVAdapter()
                this.searchProfileRVAdapter.submitList(profileList)

                search_recycler_view.apply {
                    layoutManager = LinearLayoutManager(
                        SearchProfileActivity(),
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    adapter = searchProfileRVAdapter
                }
            }
        }
        
        /**
        저장된 검색 기록 가져오기 => 검색군 별로 분리 필요
        this.searchHistoryList = PrefManager.getSearchHistoryList() as ArrayList<SearchData>
         **/
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)

        val searchItem =  menu?.findItem(R.id.action_search)
        val mSearchView = searchItem?.actionView as androidx.appcompat.widget.SearchView

        searchItem.expandActionView() // searchView 자동 확장

        mSearchView.apply {
            this.queryHint = "검색어를 입력해주세요."
            this.setOnQueryTextListener(this@SearchProfileActivity)

            this.setOnQueryTextFocusChangeListener { _, hasExpanded ->
                when (hasExpanded) {
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

        this.mSearchViewET.apply { // search Text
            this.filters = arrayOf(InputFilter.LengthFilter(12))
            this.setTextColor(Color.BLACK)
            this.setHintTextColor(Color.GRAY)
        }
        return true
    }

    /** 서치뷰 검색어 입력 **/
    @SuppressLint("NotifyDataSetChanged")
    override fun onQueryTextSubmit(query: String?): Boolean {

        if(!query.isNullOrEmpty()) {
            this.top_tool_bar.title = query

            var newSearchData = SearchData(term = query, timestamp = Date().toString())

            this.searchHistoryList.add(newSearchData)

            //PrefManager.storeSearchHistoryList(this.searchHistoryList)

            hostSearchApi(query)
        }

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

    @SuppressLint("NotifyDataSetChanged")
    fun hostSearchApi(query: String?) {
        when (content) {
            2 -> { // 블로그 화면일 때
                RetrofitManager.instance.searchBlogs(searchTerm = query, completion = {
                        responseState, responseDataArrayList ->

                    when(responseState) {

                        RESPONSE_STATUS.OKAY -> {
                            this.blogGridRecyclerViewAdapter.submitList(responseDataArrayList as ArrayList<BlogData>)
                            blogGridRecyclerViewAdapter.notifyDataSetChanged()
                        }
                        RESPONSE_STATUS.FAIL -> {
                            Toast.makeText(this, "api 호출 에러입니다.", Toast.LENGTH_SHORT).show()
                            Log.d(Constants.TAG, "api 호출 실패 : $responseDataArrayList")
                        }

                        RESPONSE_STATUS.NO_CONTENT -> {
                            Toast.makeText(this, "검색 결과가 없습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }

            3 -> { // 프로필 화면일 때
                val profileInfoList = ArrayList<SearchProfileData>()

                val userNameList = arrayListOf("김주영","문승재","김용준","김주영","김해성","신정민")
                val userNickNameList = arrayListOf("aa12","adfs12","dsmd12","1dsf11","wnddudu1","12131dd")
                val profileImgList = arrayListOf("","","","","","")
                for (i in 0 until userNameList.size) {
                    val profileData = SearchProfileData(userName = userNameList[i],userNickName = userNickNameList[i],profileImg = profileImgList[i])
                    profileInfoList.add(profileData)
                }
                this.searchProfileRVAdapter.submitList(profileInfoList)
            }
        }



    }


}