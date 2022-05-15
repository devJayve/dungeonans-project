package com.example.dungeonans.Holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dungeonans.DataClass.AskData
import com.example.dungeonans.DataClass.BlogData
import com.example.dungeonans.DataClass.CommunityData
import com.example.dungeonans.R

class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    // 커뮤니티 포스팅 관련
    var postTitleTextView = itemView?.findViewById<TextView>(R.id.postTitleTextView)
    var postBodyTextView = itemView?.findViewById<TextView>(R.id.postBodyTextView)
    var postLikeTextView = itemView?.findViewById<TextView>(R.id.postLikeTextView)
    var postCommentTextView = itemView?.findViewById<TextView>(R.id.postCommentTextView)

    fun setCommunityPostValue(listData : CommunityData) {
        postTitleTextView.text = listData.postTitle
        postBodyTextView.text = listData.postBody
        postLikeTextView.text = listData.likeCount
        postCommentTextView.text = listData.commentCount
    }
    //

    // 질문 포스팅 관련
    var askUserImage = itemView?.findViewById<ImageView>(R.id.askUserProfileImage)
    var askUserName = itemView?.findViewById<TextView>(R.id.askUserName)
    var askUserNickname = itemView?.findViewById<TextView>(R.id.askUserNickname)
    var askPostTitle = itemView?.findViewById<TextView>(R.id.askPostTitle)
    var askPostBody = itemView?.findViewById<TextView>(R.id.askPostBody)
    var askStatusImage = itemView?.findViewById<ImageView>(R.id.askStatusImage)
    var askPostLikeCount = itemView?.findViewById<TextView>(R.id.askPostLikeCount)
    var askCommentCount = itemView?.findViewById<TextView>(R.id.askCommentCount)

    fun setAskPostValue(listData : AskData) {
//        askUserImage.setBackgroundResource(listData.askUserImage)
        askUserName.text = listData.askUserName
        askUserNickname.text = listData.askUserNickname
        askPostTitle.text = listData.askPostTitle
        askPostBody.text = listData.askPostBody
//        askStatusImage.setBackgroundResource(listData.askStatusImage)
        askPostLikeCount.text = listData.askPostLikeCount
        askCommentCount.text = listData.askCommentCount
    }
    //
}