package com.example.dungeonans.recylcerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dungeonans.App
import com.example.dungeonans.BlogData
import com.example.dungeonans.R
import kotlinx.android.synthetic.main.layout_blog_item.view.*


class BlogItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val blogImgView = itemView.blog_img
    private val photoLikesCountText = itemView.cardViewLike




    // 데이터와 뷰 결합
    fun bindWithView(blogItem : BlogData) {
        photoLikesCountText.text = blogItem.likeCount.toString()

        // 이미지 설정
        Glide.with(App.instance)
            .load(blogItem.thumbnail)
            .override(300,80)
            .fitCenter()
            .into(blogImgView)
    }

}