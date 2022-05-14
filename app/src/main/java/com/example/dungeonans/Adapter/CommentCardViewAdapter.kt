package com.example.dungeonans.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dungeonans.DataClass.AskData
import com.example.dungeonans.DataClass.CommentData
import com.example.dungeonans.Holder.CommentHolder
import com.example.dungeonans.Holder.Holder
import com.example.dungeonans.R

class CommentCardViewAdapter: RecyclerView.Adapter<CommentHolder>() { // RecyclerView.Adapter를 사용하기 위해 상속
    var listData = mutableListOf<CommentData>() // 리스트 데이터를 전달받을 변수

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comment_cardview,parent,false)
        return CommentHolder(view)
    }

    override fun onBindViewHolder(holder: CommentHolder, position: Int) {
        val data = listData.get(position)
        holder.setValue(data)
    }
}