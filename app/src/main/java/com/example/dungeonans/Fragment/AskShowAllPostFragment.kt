package com.example.dungeonans.Fragment

import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dungeonans.Activity.MainActivity
import com.example.dungeonans.Adapter.AskRVAdapter
import com.example.dungeonans.Adapter.CommunityRVAdapter
import com.example.dungeonans.DataClass.*
import com.example.dungeonans.R
import com.example.dungeonans.Retrofit.RetrofitClient
import com.example.dungeonans.Space.LinearSpacingItemDecoration
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AskShowAllPostFragment : Fragment() {

    //조수민 수정 - 파라미터를 저장할 공간 생성
    lateinit var parameter : String
    //
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.askpage_viewall_fragment,container,false)
        var postSetMode = requireArguments().getString("Value")
        Log.d("tag",postSetMode.toString())
        parameter = postSetMode.toString()

        renderUi(view,0)
        setSpinner(view)

        return view
    }

    //조수민 수정 : Api 적용
    private fun renderUi(view: View , start_index : Int) {
        var retrofit = RetrofitClient.initClient()
        var data = board_req_format(start_index,6)


        //조수민 수정 : 만약 parameter 가 0 즉 전체 질문 보기라면
        if (parameter == "0") {

            var sendData = retrofit.create(RetrofitClient.GetQnAPostApi::class.java)

            sendData.sendBoardReq(data).enqueue(object : Callback<QnAPostData> {
                override fun onFailure(call: Call<QnAPostData>, t: Throwable) {
                    Toast.makeText(context, "서버 연결이 불안정합니다", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<QnAPostData>, response: Response<QnAPostData>) {
                    var postingList = response.body()!!.posting_list
                    // 조수민 수정 : 게시물이 6개 미만이면 오류가 뜨기 때문에 try 써야됨
                    try {
                        var setData: MutableList<AskData> = setData2(6, postingList)
                        var recyclerView: RecyclerView =
                            view.findViewById(R.id.askAllPostPageRecyclerView)
                        var adapter = AskRVAdapter()
                        adapter.listData = setData
                        recyclerView.adapter = adapter
                        recyclerView.layoutManager = LinearLayoutManager(context)
                        var space = LinearSpacingItemDecoration(20)
                        recyclerView.addItemDecoration(space)
                    }
                    catch (e:IndexOutOfBoundsException){

                    }
                }
            })
        }

        else if (parameter == "1") {

            var sendData = retrofit.create(RetrofitClient.GetUnAnsweredApi::class.java)

            sendData.sendBoardReq(data).enqueue(object : Callback<QnAPostData> {
                override fun onFailure(call: Call<QnAPostData>, t: Throwable) {
                    Toast.makeText(context, "서버 연결이 불안정합니다", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<QnAPostData>, response: Response<QnAPostData>) {
                    var postingList = response.body()!!.posting_list
                    // 조수민 수정 : 게시물이 6개 미만이면 오류가 뜨기 때문에 try 써야됨
                    try {
                        var setData: MutableList<AskData> = setData2(6, postingList)
                        var recyclerView: RecyclerView =
                            view.findViewById(R.id.askAllPostPageRecyclerView)
                        var adapter = AskRVAdapter()
                        adapter.listData = setData
                        recyclerView.adapter = adapter
                        recyclerView.layoutManager = LinearLayoutManager(context)
                        var space = LinearSpacingItemDecoration(20)
                        recyclerView.addItemDecoration(space)
                    }
                    catch (e:IndexOutOfBoundsException){

                    }
                }
            })
        }

        else if (parameter == "2") {

            var sendData = retrofit.create(RetrofitClient.GetClosedApi::class.java)

            sendData.sendBoardReq(data).enqueue(object : Callback<QnAPostData> {
                override fun onFailure(call: Call<QnAPostData>, t: Throwable) {
                    Toast.makeText(context, "서버 연결이 불안정합니다", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<QnAPostData>, response: Response<QnAPostData>) {
                    var postingList = response.body()!!.posting_list
                    // 조수민 수정 : 게시물이 6개 미만이면 오류가 뜨기 때문에 try 써야됨ㅓ
                    try {
                        var setData: MutableList<AskData> = setData2(6, postingList)
                        var recyclerView: RecyclerView =
                            view.findViewById(R.id.askAllPostPageRecyclerView)
                        var adapter = AskRVAdapter()
                        adapter.listData = setData
                        recyclerView.adapter = adapter
                        recyclerView.layoutManager = LinearLayoutManager(context)
                        var space = LinearSpacingItemDecoration(20)
                        recyclerView.addItemDecoration(space)
                    }
                    catch (e:IndexOutOfBoundsException){

                    }
                }
            })
        }
    }

//        var recyclerView : RecyclerView = view.findViewById(R.id.askAllPostPageRecyclerView)
////        var data : MutableList<AskData> = setData()
//        var adapter = AskCardViewAdapter()
//        adapter.setItemClickListener(object : AskCardViewAdapter.OnItemClickListener{
//            override fun onClick(v: View, position: Int) {
//                var mainActivity = context as MainActivity
//                mainActivity.showAskPost(position)
//                Log.d("tag",position.toString())
//            }
//        })
//        adapter.listData = data
//        recyclerView.adapter = adapter
//        recyclerView.layoutManager = LinearLayoutManager(context)
//        var space = LinearSpacingItemDecoration(20)
//        recyclerView.addItemDecoration(space)



    private fun setSpinner(view:View) {
        val spinner = view.findViewById<Spinner>(R.id.setPostTypeSpinner)
        val spinnerItem = resources.getStringArray(R.array.setPostType)
        val myAdapter = object : ArrayAdapter<String>(requireContext(),android.R.layout.simple_spinner_dropdown_item,spinnerItem) {
            override fun getDropDownView(position: Int,convertView: View?,parent: ViewGroup): View {
                val view: TextView = super.getDropDownView(position,convertView,parent) as TextView
                view.setTextSize(TypedValue.COMPLEX_UNIT_SP,10f)
                return view
            }
        }
        spinner.adapter = myAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,view: View,position: Int,id: Long) {
                //아이템이 클릭 되면 맨 위부터 position 0번부터 순서대로 동작하게 됩니다.
                when (position) {
                    0 -> {
                    }
                    1 -> {
                    }
                    else -> {
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }

    private fun setData() : MutableList<AskData> {
        var data : MutableList<AskData>  = mutableListOf()
        for (index in 0 until 6) {
            var askUserImage = R.drawable.profile_base_icon
            var askUserName = "김주영"
            var askUserNickname = "(@김주영사랑해)"
            var askPostTitle = "김주영사랑해리얼로"
            var askPostBody = "안녕하세요 저는 김주영을 사랑합니다"
            var askStatusImage = R.drawable.unanswered_icon
            var askPostLikeCount = "999"
            var askPostCommentCount = "999"
            var listData = AskData(askUserImage,askUserName,askUserNickname,askPostTitle,askPostBody,askStatusImage,askPostLikeCount,askPostCommentCount)
            data.add(listData)
        }
        return data
    }

    //조수민 수정 api 에서 들어온 데이터에 맞춰서 작성. 이미지는 어떻게 할 건지 고민해봐야할듯?
    private fun setData2(postCount: Int,postingData : List<posting_format_res>) : MutableList<AskData> {
        var data : MutableList<AskData>  = mutableListOf()
        for (index in 0 until postCount) {
            var askUserImage = index
            var askUserName = postingData[index].name
            var userNickName = postingData[index].nickname
            var postTitle = postingData[index].title
            var postBody = postingData[index].content
            var askStatusImage = index;
            var likeCount = postingData[index].like_num.toString()
            var commentCount = postingData[index].comment_num.toString()
            var listData = AskData(askUserImage,askUserName,userNickName,postTitle,postBody,askStatusImage
            ,likeCount,commentCount)
            data.add(listData)
        }
        return data
    }
    //
}