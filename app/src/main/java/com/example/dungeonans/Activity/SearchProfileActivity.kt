package com.example.dungeonans.Activity

import android.os.Bundle
import android.view.Menu
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dungeonans.Adapter.SearchProfileRVAdapter
import com.example.dungeonans.DataClass.SearchData
import com.example.dungeonans.DataClass.SearchProfileData
import com.example.dungeonans.R
import kotlinx.android.synthetic.main.activity_search_result.*

class SearchProfileActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    var profileList = ArrayList<SearchProfileData>()

    private var searchHistoryList = ArrayList<SearchData>()

    private lateinit var searchProfileRVAdapter: SearchProfileRVAdapter
    private lateinit var mSearchView: SearchView
    private lateinit var mSearchViewET : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        // 액티비티 내의 액션바 설정
        setSupportActionBar(top_tool_bar)

        this.searchProfileRVAdapter = SearchProfileRVAdapter()
        this.searchProfileRVAdapter.submitList(profileList)

        search_recycler_view.apply {
            layoutManager = LinearLayoutManager(SearchProfileActivity(), LinearLayoutManager.VERTICAL, false)
            adapter = searchProfileRVAdapter
        }

        /**
        저장된 검색 기록 가져오기 => 검색군 별로 분리 필요
        this.searchHistoryList = PrefManager.getSearchHistoryList() as ArrayList<SearchData>
         **/

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)

        val searchTerm = menu

        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {

        return true

    }

    override fun onQueryTextChange(newText: String?): Boolean {

        return true
    }


}