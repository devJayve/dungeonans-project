package com.example.dungeonans.Fragment

import GridSpacingItemDecoration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dungeonans.Activity.MainActivity
import com.example.dungeonans.Adapter.BlogCardViewAdapter
import com.example.dungeonans.DataClass.BlogData
import com.example.dungeonans.R

class MyAllBlogShowFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.blogpage_fragment,container,false)
        renderUi(view)
        return view
    }

    private fun renderUi(view: View) {
        var recyclerView : RecyclerView = view.findViewById(R.id.recyclerview)
        var data : MutableList<BlogData> = setData()
        var adapter = BlogCardViewAdapter()
        adapter.setItemClickListener(object : BlogCardViewAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                var mainActivity = context as MainActivity
                mainActivity.showProfile()
            }
        })

        adapter.listData = data
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(context,2)
        recyclerView.addItemDecoration(GridSpacingItemDecoration(2,60,false))
    }

    private fun setData() : MutableList<BlogData> {
        var data : MutableList<BlogData>  = mutableListOf()
        for (index in 0 until 20) {
            var cardViewTitle = "$index"
            var cardViewBody = "김주영 진짜 사랑해 김주영 진짜" +
                    " 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해 " +
                    "김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해"+
                    "김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해"+
                    "김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해"+
                    "김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해"+
                    "김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해" +
                    "김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해" +
                    "김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해" +
                    "김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해"
            var cardViewWriter = "$index 번째 작성자"
            var cardViewProfile = R.drawable.profile_base_icon
            var cardViewLanguageTag = "Notion"
            var listData = BlogData(cardViewTitle,cardViewBody,cardViewWriter,cardViewProfile,cardViewLanguageTag)
            data.add(listData)
        }
        return data
    }
}