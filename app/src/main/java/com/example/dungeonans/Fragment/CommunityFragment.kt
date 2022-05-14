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
import retrofit2.Call
import retrofit2.Response

class CommunityFragment : Fragment() {
    var selectedBtn : Int? = null
    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.communitypage_fragment,container,false)

        setHashTag(view)
        renderUi(view)
        connectScrollListener(view)
        return view
    }

    private fun setHashTag(view:View) {
        var retrofit = RetrofitClient.initClient()

        var languageTag = language_tag(false,false,false,false,false,false,false,false,false,false)
        var data = board_req_format(0,4,0,languageTag)
        Log.d("hashtag",data.toString())

        var getTagApi = retrofit.create(RetrofitClient.GetCommunityPostAPI::class.java)

        getTagApi.sendBoardReq(data).enqueue(object : retrofit2.Callback<CommunityPostData>{
            override fun onFailure(call: Call<CommunityPostData>, t: Throwable) {
                Log.d("hashtag",t.toString())
            }
            override fun onResponse(call: Call<CommunityPostData>, response: Response<CommunityPostData>) {
                Log.d("hashtag",response.body()!!.success.toString())
                Log.d("hashtag",response.body()!!.toString())
            }
        })
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

    private fun renderUi(view: View) {
        var recyclerView : RecyclerView = view.findViewById(R.id.communityPageRecyclerView)
        var data : MutableList<CommunityData> = setData()
        var adapter = CommunityCardViewAdapter()
        adapter.setItemClickListener(object : CommunityCardViewAdapter.OnItemClickListener {
            override fun postClick(v: View, position: Int) {
                var mainActivity = context as MainActivity
                mainActivity.showPost()
            }
        })
        adapter.listData = data
        recyclerView.adapter = adapter
        LinearLayoutManager(context).also { recyclerView.layoutManager = it }

        var space = LinearSpacingItemDecoration(10)
        recyclerView.addItemDecoration(space)
    }

    private fun setData() : MutableList<CommunityData> {
        var data : MutableList<CommunityData>  = mutableListOf()
        for (index in 0 until 6) {
            var postTitle = "제목제목"
            var postBody = "안녕하세요. 김주영입니다. 오늘은 노션 배우기 세 번째 시간인데"
            var hashtag = "$index 번째 작성자"
            var likeCount = "101"
            var commentCount = "2"
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

