package com.example.dungeonans.Holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dungeonans.DataClass.AskData
import com.example.dungeonans.DataClass.BlogData
import com.example.dungeonans.DataClass.CommentData
import com.example.dungeonans.DataClass.CommunityData
import com.example.dungeonans.R

class CommentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var commentBody = itemView?.findViewById<TextView>(R.id.commentBody)

    fun setValue(listData : CommentData) {
        commentBody.text = listData.commentBody
    }
}