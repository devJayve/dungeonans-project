package com.example.dungeonans.Fragment

import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnScrollChangedListener
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dungeonans.Activity.MainActivity
import com.example.dungeonans.Adapter.CommunityCardViewAdapter
import com.example.dungeonans.DataClass.*
import com.example.dungeonans.R
import com.example.dungeonans.Retrofit.RetrofitClient
import com.example.dungeonans.Space.LinearSpacingItemDecoration
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommunityFragment : Fragment() {
    var selectedBtn : Int? = null
    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.communitypage_fragment,container,false)

        setHashTag(view)
        renderPost(view)
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

    private fun renderPost(view: View) {
        var mainLayout : LinearLayout = view.findViewById(R.id.mainLayout)
        var loading : ProgressBar = view.findViewById(R.id.loading)

        var retrofit = RetrofitClient.initClient()
        var data = board_req_format(0,6)
        var getCommunityPostApi = retrofit.create(RetrofitClient.GetCommunityPostAPI::class.java)
        getCommunityPostApi.sendBoardReq(data).enqueue(object : Callback<CommunityPostData>{
            override fun onFailure(call: Call<CommunityPostData>, t: Throwable) {
                Toast.makeText(context,"서버 연결이 불안정합니다",Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<CommunityPostData>, response: Response<CommunityPostData>) {
                var recyclerView : RecyclerView = view.findViewById(R.id.communityPageRecyclerView)
                var postingList = response.body()!!.posting_list
                var sendData : MutableList<CommunityData> = setData(postingList)
                var adapter = CommunityCardViewAdapter()
                adapter.setItemClickListener(object : CommunityCardViewAdapter.OnItemClickListener {
                    override fun postClick(v: View, position: Int) {
                        var mainActivity = context as MainActivity
                        mainActivity.showPost()
                    }
                })
                adapter.listData = sendData
                recyclerView.adapter = adapter
                LinearLayoutManager(context).also { recyclerView.layoutManager = it }

                var space = LinearSpacingItemDecoration(10)
                recyclerView.addItemDecoration(space)
                mainLayout.visibility = View.VISIBLE
                loading.visibility = View.GONE
            }
        })

        var getCommunityHotPostApi = retrofit.create(RetrofitClient.GetCommunityHotPostApi::class.java)
        var hotPostData = send_post_cnt(2)
        getCommunityHotPostApi.sendPostCount(hotPostData).enqueue(object : Callback<CommunityHotPostData> {
            override fun onFailure(call: Call<CommunityHotPostData>, t: Throwable) {
            }
            override fun onResponse(call: Call<CommunityHotPostData>,response: Response<CommunityHotPostData>) {
                if(response.body()!!.success) {
                    var hotpost_1 = view.findViewById<ConstraintLayout>(R.id.hotpost_1)
                    var hotpost_1_title = hotpost_1.findViewById<TextView>(R.id.hotpost_1_title)
                    hotpost_1_title.text = response.body()!!.posting_list[0].title
                    var hotpost_1_content = hotpost_1.findViewById<TextView>(R.id.hotpost_1_content)
                    hotpost_1_content.text = response.body()!!.posting_list[0].content
                    var hotpost_1_likecount = hotpost_1.findViewById<TextView>(R.id.hotpost_1_likecount)
                    hotpost_1_likecount.text = response.body()!!.posting_list[0].like_num.toString()
                    var hotpost_1_commentcount = hotpost_1.findViewById<TextView>(R.id.hotpost_1_commentcount)
                    hotpost_1_commentcount.text = response.body()!!.posting_list[0].comment_num.toString()

                    var hotpost_2 = view.findViewById<ConstraintLayout>(R.id.hotpost_2)
                    var hotpost_2_title = hotpost_2.findViewById<TextView>(R.id.hotpost_2_title)
                    hotpost_2_title.text = response.body()!!.posting_list[1].title
                    var hotpost_2_content = hotpost_2.findViewById<TextView>(R.id.hotpost_2_content)
                    hotpost_2_content.text = response.body()!!.posting_list[1].content
                    var hotpost_2_likecount = hotpost_2.findViewById<TextView>(R.id.hotpost_2_likecount)
                    hotpost_2_likecount.text = response.body()!!.posting_list[1].like_num.toString()
                    var hotpost_2_commentcount = hotpost_2.findViewById<TextView>(R.id.hotpost_2_commentcount)
                    hotpost_2_commentcount.text = response.body()!!.posting_list[1].comment_num.toString()
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

