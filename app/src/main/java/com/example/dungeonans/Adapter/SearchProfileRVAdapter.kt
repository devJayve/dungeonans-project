package com.example.dungeonans.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dungeonans.DataClass.SearchProfileData
import com.example.dungeonans.Holder.ProfileItemViewHolder
import com.example.dungeonans.R

class SearchProfileRVAdapter : RecyclerView.Adapter<ProfileItemViewHolder>() {

    private var profileList = ArrayList<SearchProfileData>()

    // 뷰홀더와 레이아웃 연결
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileItemViewHolder {

        return ProfileItemViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_search_user, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ProfileItemViewHolder, position: Int){
        holder.bind(this.profileList[position])

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it,position)
        }
    }

    override fun getItemCount(): Int {
        return profileList.size
    }

    fun submitList(profileList : ArrayList<SearchProfileData>) {
        this.profileList = profileList
    }

    interface ItemClickListener{
        fun onClick(view: View, position: Int)
    }

    //를릭 리스너
    private lateinit var itemClickListener: ItemClickListener
    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

}