package com.example.dungeonans.BlogHolder
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dungeonans.Activity.UserProfileActivity
import com.example.dungeonans.DataClass.AskData
import com.example.dungeonans.DataClass.BlogData
import com.example.dungeonans.DataClass.CommunityData
import com.example.dungeonans.Fragment.BlogFragment
import com.example.dungeonans.R

class BlogHolder(itemView: View, width : Int) : RecyclerView.ViewHolder(itemView) {
    var width = width
    var titleTextView = itemView?.findViewById<TextView>(R.id.cardViewTitle)
    var writerTextView = itemView?.findViewById<TextView>(R.id.cardViewWriter)
    var bodyTextView = itemView?.findViewById<TextView>(R.id.cardViewBody)
    var profileImageView = itemView?.findViewById<ImageView>(R.id.cardViewProfile)
    var cardViewLanguageTag = itemView?.findViewById<TextView>(R.id.cardViewLanguageTag)

    fun setBlogPostValue(listData : BlogData) {
        if (width < 600) {
            bodyTextView.maxLines = 3
        } else if (width < 1000) {
            bodyTextView.maxLines = 12
        }
        titleTextView.text = listData.cardViewTitle
        writerTextView.text = listData.cardViewWriter
        bodyTextView.text = listData.cardViewBody
        profileImageView.setBackgroundResource(listData.cardViewProfile)
        cardViewLanguageTag.text = listData.languageTag
    }
}