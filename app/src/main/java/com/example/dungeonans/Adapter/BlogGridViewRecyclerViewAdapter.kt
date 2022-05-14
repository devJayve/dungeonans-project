package com.example.dungeonans.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dungeonans.BlogData
import com.example.dungeonans.R
import com.example.dungeonans.Holder.BlogItemViewHolder

class BlogGridViewRecyclerViewAdapter : RecyclerView.Adapter<BlogItemViewHolder>() {

    private var blogList = ArrayList<BlogData>()

    //뷰홀더와 레이아웃 연걸
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogItemViewHolder {

        return BlogItemViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.layout_blog_item, parent, false)
        )
    }

    // 뷰가 bind 되었을 때 뷰홀더에 넘겨줌
    override fun onBindViewHolder(holder: BlogItemViewHolder, position: Int) {
        holder.bindWithView(this.blogList[position])

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }

    // 외부에서 Adapter에 데이터 배열을 넣어줌.
    fun submitList(blogList: ArrayList<BlogData>) {
        this.blogList = blogList
    }



    // 보여줄 목록의 개수
    override fun getItemCount(): Int {
        return blogList.size
    }

    //를릭 리스너
    private lateinit var itemClickListener: SearchProfileRVAdapter.ItemClickListener
    fun setItemClickListener(itemClickListener: SearchProfileRVAdapter.ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

}