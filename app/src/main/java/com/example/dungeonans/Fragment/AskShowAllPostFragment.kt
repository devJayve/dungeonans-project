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
import com.example.dungeonans.Adapter.AskCardViewAdapter
import com.example.dungeonans.Adapter.CommunityCardViewAdapter
import com.example.dungeonans.DataClass.AskData
import com.example.dungeonans.DataClass.CommunityData
import com.example.dungeonans.DataClass.CommunityPostData
import com.example.dungeonans.DataClass.board_req_format
import com.example.dungeonans.R
import com.example.dungeonans.Retrofit.RetrofitClient
import com.example.dungeonans.Space.LinearSpacingItemDecoration
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AskShowAllPostFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.askpage_viewall_fragment,container,false)
        var postSetMode = requireArguments().getString("Value")

        renderUi(view)
        setSpinner(view)

        return view
    }

    private fun renderUi(view: View) {
        var retrofit = RetrofitClient.initClient()
        var sendData = retrofit.create(RetrofitClient.GetUnAnsweredQnaPostApi::class.java)

        var recyclerView : RecyclerView = view.findViewById(R.id.askAllPostPageRecyclerView)
        var data : MutableList<AskData> = setData()
        var adapter = AskCardViewAdapter()
        adapter.setItemClickListener(object : AskCardViewAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int) {
                var mainActivity = context as MainActivity
                mainActivity.showAskPost(position)
                Log.d("tag",position.toString())
            }
        })
        adapter.listData = data
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        var space = LinearSpacingItemDecoration(20)
        recyclerView.addItemDecoration(space)
    }

//    private fun renderPost(view: View, start_index : Int) {
//        var mainLayout : LinearLayout = view.findViewById(R.id.mainLayout)
//        var communityPageRecyclerView : RecyclerView = view.findViewById(R.id.communityPageRecyclerView)
//        communityPageRecyclerView.visibility = View.GONE
//
//        var retrofit = RetrofitClient.initClient()
//        var data = board_req_format(start_index,6)
//        var getCommunityPostApi = retrofit.create(RetrofitClient.GetCommunityPostAPI::class.java)
//        getCommunityPostApi.sendBoardReq(data).enqueue(object : Callback<CommunityPostData> {
//            override fun onFailure(call: Call<CommunityPostData>, t: Throwable) {
//                Toast.makeText(context,"서버 연결이 불안정합니다",Toast.LENGTH_SHORT).show()
//            }
//            override fun onResponse(call: Call<CommunityPostData>, response: Response<CommunityPostData>) {
//                var recyclerView : RecyclerView = view.findViewById(R.id.communityPageRecyclerView)
//                var postingList = response.body()!!.posting_list
//
//                //조수민 수정 : 전체 posting_format_res 를 받고, for 문 돌려서 index 가 1인것 찾고, 저 위 선언해놓았던 배열에 넣어주기
//                for (i in 0..response.body()!!.posting_list.size-1){
//                    var (board_index, posting_index, name, id, nickname,
//                        title, content,data,like_num,comment_num, board_tag,row_number) = response.body()!!.posting_list[i]
//                    if (board_index == 1){
//                        communityPostingList.add(response.body()!!.posting_list[i])
//                    }
//                }
//                //
//
//                //조수민 수정 : setData 에 위에 배열 삽입
//                var sendData : MutableList<CommunityData> = setData(6,communityPostingList)
//                //
//                var adapter = CommunityCardViewAdapter()
//                adapter.setItemClickListener(object : CommunityCardViewAdapter.OnItemClickListener {
//                    override fun postClick(v: View, position: Int) {
//                        var mainActivity = context as MainActivity
//                        Log.d("클릭됨!",this.toString())
//                        mainActivity.showPost()
//                    }
//                })
//                adapter.listData = sendData
//                recyclerView.adapter = adapter
//                LinearLayoutManager(context).also { recyclerView.layoutManager = it }
//                var space = LinearSpacingItemDecoration(10)
//                recyclerView.addItemDecoration(space)
//                mainLayout.visibility = View.VISIBLE
//                communityPageRecyclerView.visibility = View.VISIBLE
//            }
//        })

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
}