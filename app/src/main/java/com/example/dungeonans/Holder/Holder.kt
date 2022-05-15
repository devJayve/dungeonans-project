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
    private val postTitleTextView = itemView.findViewById<TextView>(R.id.postTitleTextView)
    private val postBodyTextView = itemView.findViewById<TextView>(R.id.postBodyTextView)
    private val postLikeTextView = itemView.findViewById<TextView>(R.id.postLikeTextView)
    private val postCommentTextView = itemView.findViewById<TextView>(R.id.postCommentTextView)

    // 질문 포스팅 관련
    private val askUserImage = itemView.findViewById<ImageView>(R.id.askUserProfileImage)
    private val askUserName = itemView.findViewById<TextView>(R.id.askUserName)
    private val askUserNickname = itemView.findViewById<TextView>(R.id.askUserNickname)
    private val askPostTitle = itemView.findViewById<TextView>(R.id.askPostTitle)
    private val askPostBody = itemView.findViewById<TextView>(R.id.askPostBody)
    private val askStatusImage = itemView.findViewById<ImageView>(R.id.askStatusImage)
    private val askPostLikeCount = itemView.findViewById<TextView>(R.id.askPostLikeCount)
    private val askCommentCount = itemView.findViewById<TextView>(R.id.askCommentCount)

    fun setCommunityPostValue(communityData: CommunityData) {
        postTitleTextView.text = communityData.postTitle        // 커뮤니티 글 제목
        postBodyTextView.text = communityData.postBody          // 커뮤니티 글 내용
        postLikeTextView.text = communityData.likeCount         // 좋아요 수
        postCommentTextView.text = communityData.commentCount   // 댓글 수
    }

    fun setAskPostValue(listData: AskData) {
//        askUserImage.setBackgroundResource(listData.askUserImage)
        askUserName.text = listData.askUserName
        askUserNickname.text = listData.askUserNickname
        askPostTitle.text = listData.askPostTitle
        askPostBody.text = listData.askPostBody
//        askStatusImage.setBackgroundResource(listData.askStatusImage)
        askPostLikeCount.text = listData.askPostLikeCount
        askCommentCount.text = listData.askCommentCount
    }
}
//    fun setAskPostValue(askData : AskData) {
//        askUserImage.setBackgroundResource(askData.askUserImage)     // 프로필 이미지
//        askUserName.text = askData.askUserName                       // 유저 이름
//        askUserNickname.text = askData.askUserNickname               // 유저 닉네임
//        askPostTitle.text = askData.askPostTitle                     // 질문 글 제목
//        askPostBody.text = askData.askPostBody                       // 질문 글 내용
//        askStatusImage.setBackgroundResource(askData.askStatusImage) // 질문 답변 상태
//        askPostLikeCount.text = askData.askPostLikeCount             // 좋아요 수
//        askCommentCount.text = askData.askCommentCount               // 댓글 수
//    }
