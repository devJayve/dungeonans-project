package com.example.dungeonans.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dungeonans.DataClass.BlogData
import com.example.dungeonans.BlogHolder.BlogHolder
import com.example.dungeonans.R

class BlogCardViewAdapter : RecyclerView.Adapter<BlogHolder>() { // RecyclerView.Adapter를 사용하기 위해 상속
    var listData = mutableListOf<BlogData>() // 리스트 데이터를 전달받을 변수
    var width = 0
    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.blog_cardview,parent,false)

        var cardViewBody = view.findViewById<TextView>(R.id.cardViewBody)
        cardViewBody.viewTreeObserver.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                width = cardViewBody.width
                cardViewBody.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })

        view.layoutParams.height = 650
        return BlogHolder(view,width)
    }

    override fun onBindViewHolder(holder: BlogHolder, position: Int) {
//        // 클릭 이벤트 처리
//        holder.itemView.findViewById<ImageView>(R.id.replyCommentBtn).setOnClickListener{
//            itemClickListener.onClick(it,position)
//        }

        val data = listData[position]
        holder.setBlogPostValue(data)
    }

    // (2) 리스너 인터페이스
    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }
    // (3) 외부에서 클릭 시 이벤트 설정
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
    // (4) setItemClickListener로 설정한 함수 실행
    private lateinit var itemClickListener : OnItemClickListener
}