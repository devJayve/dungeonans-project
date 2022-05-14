package com.example.dungeonans.Holder

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dungeonans.App
import com.example.dungeonans.DataClass.SearchProfileData
import com.example.dungeonans.R
import kotlinx.android.synthetic.main.item_search_user.view.*

class ProfileItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val userName = itemView.user_name_tv
    private val userNickName = itemView.nickname_tv
    private val userProfile = itemView.profile_iv

    @SuppressLint("SetTextI18n")
    fun bind(profileItem : SearchProfileData) {
        userName.text = profileItem.userName
        userNickName.text = "(@${profileItem.userNickName})"

        Glide.with(App.instance)
            .load(R.drawable.profile_base_icon)
            .override(100,100)
            .fitCenter()
            .into(userProfile)
    }
}