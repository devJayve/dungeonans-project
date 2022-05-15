package com.example.dungeonans.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.dungeonans.BlogData
import com.example.dungeonans.DataClass.CommunityData
import com.example.dungeonans.Holder.Holder
import com.example.dungeonans.R


class CommunityRVAdapter : RecyclerView.Adapter<Holder>() {

    var communityList = mutableListOf<CommunityData>()

    override fun getItemCount(): Int {
        return communityList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_cardview,parent,false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.itemView.findViewById<ConstraintLayout>(R.id.postLayout).setOnClickListener{
            itemClickListener.postClick(it,position)
        }
        val data = communityList.get(position)
        holder.setCommunityPostValue(data)
    }

    fun submitList(communityList : ArrayList<CommunityData>) {
        this.communityList = communityList
    }


    interface OnItemClickListener {
        fun postClick(v: View, position: Int)
    }
    // (3) 외부에서 클릭 시 이벤트 설정
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
    // (4) setItemClickListener로 설정한 함수 실행
    private lateinit var itemClickListener : OnItemClickListener

}