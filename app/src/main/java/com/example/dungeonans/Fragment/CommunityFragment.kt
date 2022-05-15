package com.example.dungeonans.Fragment

import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnScrollChangedListener
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    var selectedBtn : Int? = null
    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.communitypage_fragment,container,false)



        setHashTag(view)
        renderPost(view)
        renderHotPost(view)
        connectScrollListener(view)
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

    private fun renderHotPost(view : View) {
        var retrofit = RetrofitClient.initClient()
        var getCommunityHotPostApi = retrofit.create(RetrofitClient.GetCommunityHotPostApi::class.java)
        var data = send_post_cnt(2)
        getCommunityHotPostApi.sendPostCount(data).enqueue(object : Callback<CommunityHotPostData> {
            override fun onFailure(call: Call<CommunityHotPostData>, t: Throwable) {
            }

            override fun onResponse(call: Call<CommunityHotPostData>,response: Response<CommunityHotPostData>) {

            }
        })
    }

    private fun renderPost(view: View) {
        var retrofit = RetrofitClient.initClient()
        var data = board_req_format(0,6)
        var getCommunityPostApi = retrofit.create(RetrofitClient.GetCommunityPostAPI::class.java)
        getCommunityPostApi.sendBoardReq(data).enqueue(object : Callback<CommunityPostData>{
            override fun onFailure(call: Call<CommunityPostData>, t: Throwable) {
                Toast.makeText(context,"서버 연결이 불안정합니다",Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<CommunityPostData>, response: Response<CommunityPostData>) {
                if (response.body()!!.success) {
                    var recyclerView: RecyclerView =
                        view.findViewById(R.id.communityPageRecyclerView)
                    var postingList = response.body()!!.posting_list
                    var sendData: MutableList<CommunityData> = setData(postingList)
                    var adapter = CommunityRVAdapter()
                    adapter.setItemClickListener(object : CommunityRVAdapter.OnItemClickListener {
                        override fun postClick(v: View, position: Int) {
                            var mainActivity = context as MainActivity
                            mainActivity.showPost()
                        }
                    })
                    adapter.communityList = sendData
                    recyclerView.adapter = adapter
                    LinearLayoutManager(context).also { recyclerView.layoutManager = it }

                    var space = LinearSpacingItemDecoration(10)
                    recyclerView.addItemDecoration(space)
                } else {
                    Log.d("TAG","errmsg = ${response.body()!!.errmsg}")
                }
            }
        })
    }

    private fun setData(postingData : List<posting_format_res>) : MutableList<CommunityData> {
        var data : MutableList<CommunityData>  = mutableListOf()
        for (index in 0 until 6) {
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

