package com.example.dungeonans.Fragment

import GridSpacingItemDecoration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dungeonans.Activity.MainActivity
import com.example.dungeonans.Adapter.BlogCardViewAdapter
import com.example.dungeonans.DataClass.BlogData
import com.example.dungeonans.DataClass.PostData
import com.example.dungeonans.DataClass.board_req_format
import com.example.dungeonans.DataClass.posting_format_res
import com.example.dungeonans.R
import com.example.dungeonans.Retrofit.RetrofitClient
import retrofit2.Response

class BlogFragment : Fragment() {
    lateinit var setData : MutableList<BlogData>
    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.blogpage_fragment,container,false)
        renderUi(view)
        return view
    }

    private fun renderUi(view: View) {
        var postDefaultCount = 6
        var retrofit = RetrofitClient.initClient()
        var reqData = board_req_format(0,postDefaultCount)
        var sendBlogData = retrofit.create(RetrofitClient.BlogApi::class.java)
        sendBlogData.sendBoardReq(reqData).enqueue(object : retrofit2.Callback<PostData> {
            override fun onFailure(call: retrofit2.Call<PostData>, t: Throwable) {
            }
            override fun onResponse(call: retrofit2.Call<PostData>, response: Response<PostData>) {
                Log.d("blog",response.body()!!.success.toString())
                var blogData = response.body()!!.posting_list
                Log.d("blog",blogData.toString())
                var recyclerView : RecyclerView = view.findViewById(R.id.recyclerview)
                try {
                    setData = setData(postDefaultCount,blogData)
                } catch (e:IndexOutOfBoundsException) {
                    setData = setData(blogData.count(),blogData)
                } finally {
                    var adapter = BlogCardViewAdapter()
                    adapter.setItemClickListener(object : BlogCardViewAdapter.OnItemClickListener {
                        override fun onClick(v: View, position: Int) {
                            var mainActivity = context as MainActivity
                            mainActivity.showProfile()
                        }
                    })
                    adapter.listData = setData
                    recyclerView.adapter = adapter
                    recyclerView.layoutManager = GridLayoutManager(context,2)
                    recyclerView.addItemDecoration(GridSpacingItemDecoration(2,60,false))
                }
            }
        })
    }

    private fun setData(postCount : Int, blogData : List<posting_format_res>) : MutableList<BlogData> {
        var data : MutableList<BlogData>  = mutableListOf()
        for (index in 0 until postCount) {
            var cardViewTitle = blogData[index].title
            var cardViewBody = blogData[index].content
            var cardViewWriter = "by " + blogData[index].name
            var cardViewProfile = R.drawable.profile_base_icon
            var cardViewLanguageTag = "Notion"
            var listData = BlogData(cardViewTitle,cardViewBody,cardViewWriter,cardViewProfile,cardViewLanguageTag)
            data.add(listData)
        }
        return data
    }
}