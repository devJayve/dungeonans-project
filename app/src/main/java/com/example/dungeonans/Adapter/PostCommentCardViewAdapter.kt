package com.example.dungeonans.Adapter

import android.content.Intent
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dungeonans.Activity.UserProfileActivity
import com.example.dungeonans.DataClass.PostCommentData
import com.example.dungeonans.DataClass.comment_type1
import com.example.dungeonans.R

class PostCommentCardViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var listData = mutableListOf<PostCommentData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecyclerView.ViewHolder {
        val view : View?
        return when(viewType) {
            comment_type1 -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.post_comment_cardview, parent,false)
                CommentHolder(view)
            }
            else -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.recomment_cardview, parent,false)
                ReCommentHolder(view)
            }
        }
    }
    override fun getItemCount(): Int = listData.size

    override fun getItemViewType(position: Int): Int {
        return listData[position].type
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(listData[position].type) {
            comment_type1 -> {
                holder.itemView.findViewById<ImageView>(R.id.replyCommentBtn).setOnClickListener{
                itemClickListener.commentClick(it,position)
            }
            holder.itemView.findViewById<ImageView>(R.id.commentThumbsUp).setOnClickListener{
                itemClickListener.likeClick(it,position)
                holder.itemView.findViewById<TextView>(R.id.likeCount).text = (Integer.parseInt(holder.itemView.findViewById<TextView>(R.id.likeCount).text.toString()) + 1).toString()
            }
            holder.itemView.findViewById<ImageView>(R.id.commentWriterProfile).setOnClickListener{
                itemClickListener.profileClick(it,position)
            }

            val data = listData[position]
            (holder as CommentHolder).setValue(data)
            }

            else -> {
                holder.itemView.findViewById<ImageView>(R.id.reCommentThumbsUp).setOnClickListener{
                    itemClickListener.likeClick(it,position)
                    holder.itemView.findViewById<TextView>(R.id.reLikeCount).text = (Integer.parseInt(holder.itemView.findViewById<TextView>(R.id.reLikeCount).text.toString()) + 1).toString()
                }
                val data = listData[position]
                (holder as ReCommentHolder).setValue(data)
            }
        }
    }

    inner class CommentHolder(view: View) : RecyclerView.ViewHolder(view) {
        var commentWriterProfile : ImageView = itemView.findViewById(R.id.commentWriterProfile)
        var commentWriterName : TextView = itemView.findViewById(R.id.commentWriterName)
        var commentWriterNickName : TextView = itemView.findViewById(R.id.commentWriterNickName)
        var commmentWriteTime : TextView = itemView.findViewById(R.id.commentWriteTime)
        var commentBody : TextView = itemView.findViewById(R.id.commentBody)

        fun setValue(listData : PostCommentData) {
            commentWriterProfile.setBackgroundResource(listData.commentWriteProfile)
            commentWriterName.text = listData.commentWriterName
            commentWriterNickName.text = listData.commentWriterNickname
            commmentWriteTime.text = listData.commentWriteTime
            commentBody.text = listData.commentBody
        }
    }
    inner class ReCommentHolder(view: View) : RecyclerView.ViewHolder(view) {
        var commentWriterProfile : ImageView = itemView.findViewById(R.id.reCommentWriterProfile)
        var commentWriterName : TextView = itemView.findViewById(R.id.reCommentWriterName)
        var commentWriterNickName : TextView = itemView.findViewById(R.id.reCommentWriterNickName)
        var commmentWriteTime : TextView = itemView.findViewById(R.id.reCommentWriteTime)
        var commentBody : TextView = itemView.findViewById(R.id.reCommentBody)

        fun setValue(listData : PostCommentData) {
            commentWriterProfile.setBackgroundResource(listData.commentWriteProfile)
            commentWriterName.text = listData.commentWriterName
            commentWriterNickName.text = listData.commentWriterNickname
            commmentWriteTime.text = listData.commentWriteTime
            commentBody.text = listData.commentBody
        }
    }

    interface OnItemClickListener {
        fun commentClick(v: View, position: Int)
        fun likeClick(v: View,position: Int)
        fun profileClick(v: View,position: Int)
    }
    // (3) 외부에서 클릭 시 이벤트 설정
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
    // (4) setItemClickListener로 설정한 함수 실행
    private lateinit var itemClickListener : OnItemClickListener
}