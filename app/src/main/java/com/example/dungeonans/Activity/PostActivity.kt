package com.example.dungeonans.Activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dungeonans.Adapter.PostAnswerCardViewAdapter
import com.example.dungeonans.Adapter.PostCommentCardViewAdapter
import com.example.dungeonans.DataClass.*
import com.example.dungeonans.R
import com.example.dungeonans.Retrofit.RetrofitClient
import kotlinx.android.synthetic.main.myprofilepage_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


// 답변은 답변만 보여주고, 답변의 점점점을 누르면 답변 밑에 달린 모든 댓글들 다 볼 수 있게 처리,,

class PostActivity : AppCompatActivity() {
    var commentData : MutableList<PostCommentData> = mutableListOf()
    var reCommentData : MutableList<PostReCommentData> = mutableListOf()
    var answerData : MutableList<AnswerData> = mutableListOf()

    var inputMode : Int = 0

    private var doubleBackToExitPressedOnce = false

    // 리사이클러뷰 포지션 초기화
    var setRecyclerView = 0
    var commentPosition = 0

    // 댓글, 답변 개수 초기화
    var commentItemCount = 0
    lateinit var recyclerView : RecyclerView
    lateinit var commentEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ask_post_fragment)

        commentEditText = findViewById(R.id.commentEditText)
        commentEditText.setOnClickListener{
            commentEditText.requestFocus()
        }

        var backBtn : ImageView = findViewById(R.id.backBtn)
        backBtn.setOnClickListener{
            finish()
        }
        var writeCommentBtn : ImageButton = findViewById(R.id.writeCommentBtn)
        writeCommentBtn.setOnClickListener {
            if (commentEditText.text.toString() == "") {
                Toast.makeText(this,"댓글을 입력하세요",Toast.LENGTH_SHORT).show()
            } else {
                Log.d("tag",commentEditText.text.toString())
                var bodyValue = commentEditText.text.toString()
                putComment(bodyValue,commentEditText)
                commentEditText.text.clear()
                commentEditText.clearFocus()
                var manager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(commentEditText.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
                commentPosition = recyclerView.adapter!!.itemCount
                commentEditText.hint = "댓글을 입력하세요"

                var retrofit = RetrofitClient.initClient()

                var putComment = retrofit.create(RetrofitClient.PostCommentApi::class.java)
                putComment.postComment("sdfsdfsdfdfsdfsdfdsf",put_comment_req("1","커피우유맛있다")).enqueue(object : retrofit2.Callback<NoneData> {
                    override fun onFailure(call: Call<NoneData>, t: Throwable) {
                        Log.d("tag","fail")
                    }
                    override fun onResponse(call: Call<NoneData>, response: Response<NoneData>) {
                        Log.d("tag","success")
                        Log.d("tag",response.message())
                    }
                })
            }
        }

        var answerBtn : Button = findViewById(R.id.answerBtn)
        answerBtn.setOnClickListener{
            commentEditText.hint = "답변을 입력하세요"
            commentEditText.requestFocus()
            var manager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            manager.showSoftInput(commentEditText, InputMethodManager.SHOW_IMPLICIT)
            setRecyclerView = 1
        }

        var writerProfileImageView : ImageView = findViewById(R.id.writerProfileImageView)
        writerProfileImageView.setOnClickListener{
            var intent = Intent(this, UserProfileActivity::class.java)
            startActivity(intent)
        }

        renderCommentUi()
        renderAnswerUi()
    }

    private fun renderAnswerUi() {
        recyclerView = findViewById(R.id.postAnswerRecyclerView)
        var data : MutableList<AnswerData> = setAnswerData()
        var adapter = PostAnswerCardViewAdapter()
        adapter.setItemClickListener(object : PostAnswerCardViewAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                var intent = Intent(this@PostActivity,AnswerActivity::class.java)
                startActivity(intent)
            }

            override fun profileClick(v: View, position: Int) {
                var intent = Intent(this@PostActivity,UserProfileActivity::class.java)
                startActivity(intent)
            }
        })

        adapter.listData = data
        recyclerView.adapter = adapter
        LinearLayoutManager(this).also { recyclerView.layoutManager = it }

    }

    private fun setAnswerData() : MutableList<AnswerData> {
        for (index in 0 until 2) {
            var commentWriteProfile = R.drawable.profile_base_icon
            var commentWriterName = "${index}번째 작성자"
            var commentWriterNickname = "(@yongkingg)"
            var commentWriteTime = "03/21 12:45"
            var commentBody = "안녕하세요 안녕하세요 안녕하세요 안녕하세요 안녕하세요"
            var listData = AnswerData(commentWriteProfile,commentWriterName,commentWriterNickname,commentWriteTime,commentBody)
            answerData.add(listData)
        }
        return answerData
    }
    private fun renderCommentUi() {
        var retrofit = RetrofitClient.initClient()
        var getComment = retrofit.create(RetrofitClient.GetCommentApi::class.java)
        getComment.getComment(1).enqueue(object : retrofit2.Callback<Comment> {
            override fun onFailure(call: Call<Comment>, t: Throwable) {
                Log.d("tag",t.toString())
            }
            override fun onResponse(call: Call<Comment>, response: Response<Comment>) {
                Log.d("tag",response.body().toString())
            }
        })

        recyclerView = findViewById(R.id.postCommentRecyclerView)
        var data : MutableList<PostCommentData> = setCommentData()
        var adapter = PostCommentCardViewAdapter()
        adapter.setItemClickListener(object : PostCommentCardViewAdapter.OnItemClickListener {
            override fun commentClick(v: View, position: Int) {
                commentPosition = position
                setRecyclerView = 0
                commentEditText.requestFocus()
                commentEditText.hint = "대댓글을 작성해보세요"
                var manager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                manager.showSoftInput(commentEditText, InputMethodManager.SHOW_IMPLICIT)
            }

            override fun likeClick(v: View, position: Int) {
            }
            override fun profileClick(v: View, position: Int) {
                var intent = Intent(this@PostActivity,UserProfileActivity::class.java)
                startActivity(intent)
            }
        })
        adapter.listData = data
        recyclerView.adapter = adapter
        LinearLayoutManager(this).also { recyclerView.layoutManager = it }
        commentPosition = recyclerView.adapter!!.itemCount
        commentItemCount = recyclerView.adapter!!.itemCount
    }
    private fun putComment(body: String, commentEditText : EditText) {
        recyclerView = findViewById(R.id.postCommentRecyclerView)
        // 대댓글
        if (recyclerView.adapter!!.itemCount != commentPosition) {
            var data : MutableList<PostCommentData> = putCommentValue(comment_type2,body,commentPosition)
            var adapter = PostCommentCardViewAdapter()

            adapter.setItemClickListener(object : PostCommentCardViewAdapter.OnItemClickListener {
                override fun commentClick(v: View, position: Int) {
                    commentPosition = position
                    setRecyclerView = 0
                    commentEditText.requestFocus()
                    commentEditText.hint = "대댓글을 작성해보세요"
                    var manager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    manager.showSoftInput(commentEditText, InputMethodManager.SHOW_IMPLICIT)
                }
                override fun likeClick(v: View, position: Int) {
                }

                override fun profileClick(v: View, position: Int) {
                    var intent = Intent(this@PostActivity,UserProfileActivity::class.java)
                    startActivity(intent)
                }
            })
            adapter.listData = data
            adapter.notifyItemInserted(commentPosition)
            recyclerView.adapter = adapter
            // 댓글
        } else {
            var data : MutableList<PostCommentData> = putCommentValue(comment_type1,body,commentPosition)
            var adapter = PostCommentCardViewAdapter()

            adapter.setItemClickListener(object : PostCommentCardViewAdapter.OnItemClickListener {
                override fun commentClick(v: View, position: Int) {
                    commentPosition = position
                    setRecyclerView = 0
                    commentEditText.requestFocus()
                    commentEditText.hint = "대댓글을 작성해보세요"
                    var manager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    manager.showSoftInput(commentEditText, InputMethodManager.SHOW_IMPLICIT)
                }
                override fun likeClick(v: View, position: Int) {
                }
                override fun profileClick(v: View, position: Int) {
                    var intent = Intent(this@PostActivity,UserProfileActivity::class.java)
                    startActivity(intent)
                }
            })
            adapter.listData = data
            adapter.notifyItemInserted(commentPosition)
            recyclerView.adapter = adapter
        }
    }

    private fun setCommentData() : MutableList<PostCommentData> {
        for (index in 0 until 6) {
            var commentWriteProfile = R.drawable.profile_base_icon
            var commentWriterName = "${index}번째 작성자"
            var commentWriterNickname = "(@yongkingg)"
            var commentWriteTime = "03/21 12:45"
            var commentBody = "안녕하세요 안녕하세요 안녕하세요 안녕하세요 안녕하세요"
            var like = 0
            var listData = PostCommentData(comment_type1,commentWriteProfile,commentWriterName,commentWriterNickname,commentWriteTime,commentBody,like)
            commentData.add(listData)
        }
        return commentData
    }

    private fun putCommentValue(type : Int, body: String, position : Int) : MutableList<PostCommentData> {
        var commentWriteProfile = R.drawable.profile_base_icon
        var commentWriterName = "번째 작성자"
        var commentWriterNickname = "(@yongkingg)"
        var commentWriteTime = "03/21 12:45"
        var commentBody = body
        var like = 0
        var listData = PostCommentData(type,commentWriteProfile,commentWriterName,commentWriterNickname,commentWriteTime,commentBody,like)

        try {
            commentData.add(position+1,listData)
        } catch (e : IndexOutOfBoundsException) {
            commentData.add(position,listData)
        }
        return commentData
    }

    // 뒤로가기 버튼 초기화
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce = true
        commentEditText.hint = "댓글을 입력하세요"
        setRecyclerView = 0
        commentEditText.clearFocus()
        commentPosition = recyclerView.adapter!!.itemCount
        var manager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(commentEditText.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        Handler(Looper.getMainLooper()).postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }
}