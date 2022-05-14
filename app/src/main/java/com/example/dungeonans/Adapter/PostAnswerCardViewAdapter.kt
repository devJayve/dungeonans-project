package com.example.dungeonans.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.dungeonans.DataClass.AnswerData
import com.example.dungeonans.Holder.AnswerHolder
import com.example.dungeonans.R

class PostAnswerCardViewAdapter : RecyclerView.Adapter<AnswerHolder>() {
    var listData = mutableListOf<AnswerData>()
    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.answer_cardview,parent,false)
        return AnswerHolder(view)
    }

    override fun onBindViewHolder(holder: AnswerHolder, position: Int) {
        holder.itemView.findViewById<ImageView>(R.id.answerReplyCommentBtn).setOnClickListener{
            itemClickListener.onClick(it,position)
        }
        holder.itemView.findViewById<ImageView>(R.id.answerWriterProfileImage).setOnClickListener{
            itemClickListener.profileClick(it,position)
        }

        val data = listData[position]
        holder.setValue(data)
    }

    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
        fun profileClick(v: View,position: Int)
    }
    // (3) 외부에서 클릭 시 이벤트 설정
    fun setItemClickListener(onItemClickListener: PostAnswerCardViewAdapter.OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
    // (4) setItemClickListener로 설정한 함수 실행
    private lateinit var itemClickListener : PostAnswerCardViewAdapter.OnItemClickListener

}