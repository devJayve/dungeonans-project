package com.example.dungeonans.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dungeonans.Adapter.CommentCardViewAdapter
import com.example.dungeonans.DataClass.CommentData
import com.example.dungeonans.R
import com.example.dungeonans.Space.LinearSpacingItemDecoration

class MyAllCommentShowFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.profile_allmycomment_fragment, container, false)
        renderUi(view)
        return view
    }
    private fun renderUi(view: View) {
        var recyclerView : RecyclerView = view.findViewById(R.id.commentRecyclerView)
        var data : MutableList<CommentData> = setData()
        var adapter = CommentCardViewAdapter()
        adapter.listData = data
        recyclerView.adapter = adapter
        LinearLayoutManager(context).also { recyclerView.layoutManager = it }

        var space = LinearSpacingItemDecoration(10)
        recyclerView.addItemDecoration(space)
    }

    private fun setData() : MutableList<CommentData> {
        var data : MutableList<CommentData>  = mutableListOf()
        for (index in 0 until 20) {
            var postBody = "안녕하세요. 김주영입니다. 오늘은 노션 배우기 세 번째 시간인데"
            var listData = CommentData(postBody)
            data.add(listData)
        }
        return data
    }
}
