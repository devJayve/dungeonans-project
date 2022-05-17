package com.example.dungeonans.Activity

import android.annotation.SuppressLint
import android.content.Intent
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
import com.example.dungeonans.Adapter.AskRVAdapter
import com.example.dungeonans.Adapter.BlogGridViewRecyclerViewAdapter
import com.example.dungeonans.Adapter.CommunityRVAdapter
import com.example.dungeonans.Adapter.SearchProfileRVAdapter
import com.example.dungeonans.BlogData
import com.example.dungeonans.DataClass.*
import com.example.dungeonans.R
import com.example.dungeonans.Retrofit.RetrofitClient
import com.example.dungeonans.Retrofit.RetrofitManager
import com.example.dungeonans.Utils.Constants
import com.example.dungeonans.Utils.PrefManager
import com.example.dungeonans.Utils.RESPONSE_STATUS
import kotlinx.android.synthetic.main.activity_search_result.*
import kotlinx.android.synthetic.main.answer_fragment.view.*
import kotlinx.android.synthetic.main.ask_allpost_cardview.*
import kotlinx.android.synthetic.main.blogpage_fragment.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class SearchProfileActivity : AppCompatActivity(), SearchView.OnQueryTextListener {


    var content = 100


    /** RecyclerView Adapter **/
    private lateinit var communityRVAdapter: CommunityRVAdapter
    private lateinit var askRVAdapter: AskRVAdapter
    private lateinit var searchProfileRVAdapter: SearchProfileRVAdapter
    private lateinit var blogGridRecyclerViewAdapter: BlogGridViewRecyclerViewAdapter

    /** Search 관련 View **/
    private lateinit var mSearchView: SearchView
    private lateinit var mSearchViewET : EditText

    /** DataList **/
    private lateinit var communityPostList : ArrayList<CommunityData>
    private lateinit var  askPostList : ArrayList<AskData>
    private lateinit var  blogPostList : ArrayList<BlogData>
    private lateinit var  profilePostList : ArrayList<SearchProfileData>
    private var searchHistoryList = ArrayList<SearchData>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        content = intent.getIntExtra("content",0)
        Log.d("TAG", "content is $content")

        // 액티비티 내의 액션바 설정
        setSupportActionBar(top_tool_bar)

        when (content) {
            0 -> { // Community 검색
                Log.d("TAG", "SEARCH - community")
                communityPostList = ArrayList()
                this.communityRVAdapter = CommunityRVAdapter()
                this.communityRVAdapter.submitList(communityPostList)

                search_recycler_view.apply {
                    layoutManager = LinearLayoutManager(
                        SearchProfileActivity(),
                        LinearLayoutManager.VERTICAL,
                        false
                    )

                    adapter = communityRVAdapter
                }
            }

            1 -> { // Ask 검색
                Log.d("TAG", "SEARCH - Ask")
                askPostList = ArrayList()
                this.askRVAdapter = AskRVAdapter()
                this.askRVAdapter.submitList(askPostList)

                search_recycler_view.apply {
                     layoutManager = LinearLayoutManager(
                         SearchProfileActivity(),
                         LinearLayoutManager.VERTICAL,
                         false
                    )
                    adapter = askRVAdapter
                }
            }

            2 -> { // Blog 검색
                blogPostList = ArrayList()
                this.blogGridRecyclerViewAdapter = BlogGridViewRecyclerViewAdapter()
                this.blogGridRecyclerViewAdapter.submitList(blogPostList)

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

            3 -> { // Profile 검색
                profilePostList = ArrayList()
                this.searchProfileRVAdapter = SearchProfileRVAdapter()
                this.searchProfileRVAdapter.submitList(profilePostList)

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
        val retrofit = RetrofitClient.initClient()


        when (content) {
            0 -> { // 커뮤니티 게시글 불러오기
                communityPostList.clear()



                val requestSearchApi = retrofit.create(RetrofitClient.SearchApi::class.java)
                requestSearchApi.postSearchCommunity(QueryData(query = query.toString())).enqueue(object : Callback<CommunityHotPostData> {
                    override fun onFailure(call: Call<CommunityHotPostData>, t: Throwable) {
                    }
                    override fun onResponse(call: Call<CommunityHotPostData>, response: Response<CommunityHotPostData>) {
                        if (response.body()?.success == true) {
                            setData(response.body()!!.posting_list)

                            communityRVAdapter.setItemClickListener(object : CommunityRVAdapter.OnItemClickListener {
                                override fun postClick(v: View, position: Int) {
                                    Log.d("TAG!", "$position SearchProfileActivity - community post click")
                                }
                            })

                            communityRVAdapter.submitList(communityPostList)
                            communityRVAdapter.notifyDataSetChanged()
                        }
                        else {
                            Log.d("community - errmsg : ", response.body()!!.errmsg)
                        }
                    }
                })
            }

            1 -> { // 질문 게시글 불러오기
                askPostList.clear()


                for (i in 0 until 10) {
                    val askData = AskData(
                        askUserImage = 1,
                        askUserName = "",
                        askUserNickname = "",
                        askPostTitle = "",
                        askPostBody = "",
                        askStatusImage = 1,
                        askPostLikeCount = "",
                        askCommentCount = "",
                    )
                    askPostList.add(askData)
                }
                this.askRVAdapter.submitList(askPostList)
                askRVAdapter.notifyDataSetChanged()
            }

            2 -> { // 블로그 게시글 불러오기
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

            3 -> { // 프로필 게시글 불러오기
                profilePostList.clear()

                val userNameList = arrayListOf("김주영","문승재","김용준","김주영","김해성","신정민")
                val userNickNameList = arrayListOf("aa12","adfs12","dsmd12","1dsf11","wnddudu1","12131dd")
                val profileImgList = arrayListOf("","","","","","")

                for (i in 0 until userNameList.size) {
                    val profileData = SearchProfileData(userName = userNameList[i],userNickName = userNickNameList[i],profileImg = profileImgList[i])
                    profilePostList.add(profileData)
                }

                this.searchProfileRVAdapter.submitList(profilePostList)
                searchProfileRVAdapter.notifyDataSetChanged()
            }
        }
    }


    private fun setData(postingData : List<posting_format_res>) {

        for (index in 0 until 6) {
            val postTitle = postingData[index].title
            val postBody = postingData[index].content
            val hashtag = postingData[index].board_tag.toString()
            val likeCount = postingData[index].like_num.toString()
            val commentCount = postingData[index].comment_num.toString()
            val listData = CommunityData(postTitle,postBody,hashtag,likeCount,commentCount)

            communityPostList.add(listData)
        }
    }
}