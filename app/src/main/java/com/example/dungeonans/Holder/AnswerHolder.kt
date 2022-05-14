package com.example.dungeonans.Holder

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dungeonans.DataClass.AnswerData
import com.example.dungeonans.R

class AnswerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var writerProfileImage = itemView?.findViewById<ImageView>(R.id.answerWriterProfileImage)
    var writerName = itemView?.findViewById<TextView>(R.id.answerWriterName)
    var writerNickName = itemView?.findViewById<TextView>(R.id.answerWriterNickName)
    var postTime = itemView?.findViewById<TextView>(R.id.answerPostTime)

    fun setValue(listData : AnswerData) {
        writerProfileImage.setBackgroundResource(R.drawable.profile_base_icon)
        writerName.text = listData.writerName
        writerNickName.text = listData.writerNickName
        postTime.text = listData.writeTime
    }
}