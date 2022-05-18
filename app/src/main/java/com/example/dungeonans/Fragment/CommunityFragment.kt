package com.example.dungeonans.Fragment

import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnScrollChangedListener
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.dungeonans.Activity.MainActivity
import com.example.dungeonans.Adapter.CommunityRVAdapter
import com.example.dungeonans.DataClass.*
import com.example.dungeonans.R
import com.example.dungeonans.Retrofit.RetrofitClient
import com.example.dungeonans.Space.LinearSpacingItemDecoration
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommunityFragment : Fragment() {
    //조수민 수정 : boarding_index 가 1인 posting_format_res 를 담는 리스트
    var communityPostingList = ArrayList<posting_format_res>()
    //조수민 수정 : boarding_index 가 1인 ... api 가 달라서 따로 배열을 만들어야 할듯
    var communityHotPostList = ArrayList<posting_format_res>()
    //
    var selectedBtn : Int? = null
    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.communitypage_fragment2,container,false)

        setHashTag(view)
        renderPost(view,0)
        renderHotPost(view)
        connectScrollListener(view)

        var swipe = view.findViewById<SwipeRefreshLayout>(R.id.swapeView)
        swipe.setOnRefreshListener {

            renderPost(view,0)
            renderHotPost(view)

            swipe.isRefreshing = false
        }

        return view


    }

    private fun setHashTag(view:View) {
        var radioGroup : RadioGroup = view.findViewById(R.id.radioGroup)
        var radioButtonText = resources.getStringArray(R.array.hashtaglist)

        // 라디오 버튼 생성
        for (index in 0 until radioButtonText.count()) {
            var radioButton = layoutInflater.inflate(R.layout.hashtag_radiobutton,null)
            radioButton.id = index
            var buttonParams = RadioGroup.LayoutParams(
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,70f,resources.displayMetrics).toInt(),
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,30f,resources.displayMetrics).toInt())
            buttonParams.setMargins(
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,10f,resources.displayMetrics).toInt(),0,0,0)
            radioButton.layoutParams = buttonParams
            radioGroup.addView(radioButton)
            }

        // 라디오 버튼 텍스트 설정, 선택 해제 로직
        for (index in 0 until radioButtonText.count()) {
            var radioButton = view.findViewById<RadioButton>(index)
            radioButton.text = radioButtonText[index]
            radioButton.setOnClickListener{
                if (selectedBtn == index) {
                    radioButton.isChecked = false
                    view.findViewById<RadioButton>(selectedBtn!!).setTextColor(resources.getColor(R.color.black,null))
                }
                selectedBtn = index
            }
        }
        // 라디오 버튼 선택 해제 로직
        radioGroup.setOnCheckedChangeListener{ _, checkedId ->
            if (selectedBtn != null) {
                view.findViewById<RadioButton>(selectedBtn!!).setTextColor(resources.getColor(R.color.black,null))
            }
            when(checkedId) {
                checkedId ->  {
                    var checkedBtn = view.findViewById<RadioButton>(checkedId)
                    checkedBtn.setTextColor(resources.getColor(R.color.white,null))
                }
            }
        }
    }


    private fun renderPost(view: View, start_index : Int) {
        var mainLayout: LinearLayout = view.findViewById(R.id.mainLayout)
        var communityPageRecyclerView: RecyclerView =
            view.findViewById(R.id.communityPageRecyclerView)
        communityPageRecyclerView.visibility = View.GONE

        var retrofit = RetrofitClient.initClient()
        var getCommunityPostApi = retrofit.create(RetrofitClient.CommunityApi::class.java)
        var data = board_req_format(start_index, 6)
        getCommunityPostApi.getCommunityPost(data).enqueue(object : Callback<PostData> {
            override fun onFailure(call: Call<PostData>, t: Throwable) {
                Toast.makeText(context, "서버 연결이 불안정합니다", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<PostData>,
                response: Response<PostData>
            ) {

                var postingList = response.body()!!.success
                Log.d("Asdfasdf",postingList.toString())
                var recyclerView: RecyclerView = view.findViewById(R.id.communityPageRecyclerView)

                //조수민 수정 : 전체 posting_format_res 를 받고, for 문 돌려서 index 가 1인것 찾고, 저 위 선언해놓았던 배열에 넣어주기
                for (i in 0..response.body()!!.posting_list.size - 1) {
                    var (board_index, posting_index, name, id, nickname,
                        title, content, data, like_num, comment_num, board_tag, row_number) = response.body()!!.posting_list[i]
                    if (board_index == 1) {
                        communityPostingList.add(response.body()!!.posting_list[i])
                    }
                }
                //

                //조수민 수정 : setData 에 위에 배열 삽입
                var sendData: MutableList<CommunityData> = setData(6, communityPostingList)
                //
                var adapter = CommunityRVAdapter()
                adapter.setItemClickListener(object : CommunityRVAdapter.OnItemClickListener {
                    override fun postClick(v: View, position: Int) {
                        var mainActivity = context as MainActivity
                        Log.d("클릭됨!", this.toString())
                        mainActivity.showPost()
                    }
                })
                adapter.communityList = sendData
                recyclerView.adapter = adapter
                LinearLayoutManager(context).also { recyclerView.layoutManager = it }
                var space = LinearSpacingItemDecoration(10)
                recyclerView.addItemDecoration(space)
                mainLayout.visibility = View.VISIBLE
                communityPageRecyclerView.visibility = View.VISIBLE
            }
        })
    }

    private fun renderHotPost(view : View) {
        var mainLayout : LinearLayout = view.findViewById(R.id.mainLayout)
        var communityPageRecyclerView : RecyclerView = view.findViewById(R.id.communityPageRecyclerView)
        var retrofit = RetrofitClient.initClient()
        var getCommunityHotPostApi = retrofit.create(RetrofitClient.CommunityApi::class.java)
        var data = send_post_cnt(2)
        getCommunityHotPostApi.getCommunityHotPost(data).enqueue(object : Callback<PostData> {
            override fun onFailure(call: Call<PostData>, t: Throwable) {
                Toast.makeText(context,"서버 연결이 불안정합니다",Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<PostData>,response: Response<PostData>) {
                var recyclerView : RecyclerView = view.findViewById(R.id.communityPageHotPostRecyclerView)
                //조수민 수정 : communityHotPoistList 에 저장
                for (i in 0..response.body()!!.posting_list.size-1){
                    var (board_index, posting_index, name, id, nickname,
                        title, content,data,like_num,comment_num, board_tag,row_number) = response.body()!!.posting_list[i]
                    if (board_index == 1){
                        communityHotPostList.add(response.body()!!.posting_list[i])
                    }
                }

                //조수민 수정 : setData 에 위에 배열 삽입
                var sendData : MutableList<CommunityData> = setData(2,communityHotPostList)
                //
                var adapter = CommunityRVAdapter()
                adapter.setItemClickListener(object : CommunityRVAdapter.OnItemClickListener {
                    override fun postClick(v: View, position: Int) {
                        var mainActivity = context as MainActivity
                        Log.d("클릭됨!", this.toString())
                        mainActivity.showPost()
                    }
                })
                adapter.communityList = sendData
                recyclerView.adapter = adapter
                LinearLayoutManager(context).also { recyclerView.layoutManager = it }
                var space = LinearSpacingItemDecoration(10)
                recyclerView.addItemDecoration(space)
                mainLayout.visibility = View.VISIBLE
                communityPageRecyclerView.visibility = View.VISIBLE

            }
        })
    }

    private fun setData(postCount : Int, postingData : ArrayList<posting_format_res>) : MutableList<CommunityData> {
        var data : MutableList<CommunityData>  = mutableListOf()
        for (index in 0 until postCount) {
            var postTitle = postingData[index].title
            var postBody = postingData[index].content
            var hashtag = postingData[index].board_tag.toString()
            var likeCount = postingData[index].like_num.toString()
            var commentCount = postingData[index].comment_num.toString()
            var listData = CommunityData(postTitle,postBody,hashtag,likeCount,commentCount)
            data.add(listData)
        }
        return data
    }

    private fun connectScrollListener(view:View) {
        var recyclerView = view.findViewById<NestedScrollView>(R.id.communityPageScrollView)
        recyclerView.viewTreeObserver.addOnScrollChangedListener(OnScrollChangedListener {
            val scrollY: Int = recyclerView.scrollY
            if (recyclerView.getChildAt(0).bottom <=(recyclerView.height +scrollY)) {
            }
        })
    }
}

