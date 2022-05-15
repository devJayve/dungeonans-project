package com.example.dungeonans.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dungeonans.DataClass.AskData
import com.example.dungeonans.DataClass.BlogData
import com.example.dungeonans.DataClass.CommunityData
import com.example.dungeonans.Holder.Holder
import com.example.dungeonans.R


class AskRVAdapter : RecyclerView.Adapter<Holder>() { // RecyclerView.Adapter를 사용하기 위해 상속

    var listData = mutableListOf<AskData>() // 리스트 데이터를 전달받을 변수

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ask_allpost_cardview,parent,false)
        return Holder(view)
    }

    fun submitList(askList : ArrayList<AskData>) {
        this.listData = askList
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val data = listData.get(position)
        holder.setAskPostValue(data)
    }
}