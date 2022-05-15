package com.example.dungeonans.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
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
        holder.itemView.findViewById<CardView>(R.id.askAllPostCardView).setOnClickListener{
            Log.d("dkssud!",it.toString())
            Log.d("dkssud!",listData.toString())
//            itemClickListener.onClick(it,position)

        }
        val data = listData.get(position)
        holder.setAskPostValue(data)
    }

    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }
    // (3) 외부에서 클릭 시 이벤트 설정
    fun setItemClickListener(onItemClickListener: AskRVAdapter.OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
    // (4) setItemClickListener로 설정한 함수 실행
    private lateinit var itemClickListener : AskRVAdapter.OnItemClickListener


}