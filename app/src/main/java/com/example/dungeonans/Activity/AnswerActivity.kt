package com.example.dungeonans.Activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dungeonans.Adapter.PostCommentCardViewAdapter
import com.example.dungeonans.DataClass.*
import com.example.dungeonans.R
import com.example.dungeonans.Retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback


// 답변은 답변만 보여주고, 답변의 점점점을 누르면 답변 밑에 달린 모든 댓글들 다 볼 수 있게 처리,,

class AnswerActivity : AppCompatActivity() {
    var commentData : MutableList<PostCommentData> = mutableListOf()
    private var doubleBackToExitPressedOnce = false
    // 리사이클러뷰 포지션 초기화
    var setRecyclerView = 0
    var commentPosition = 0

    // 댓글, 답변 개수 초기화
    var commentItemCount = 0
    lateinit var recyclerView : RecyclerView
    lateinit var commentEditText: EditText
    lateinit var askPostWebView : WebView
    var askWebViewUrl = "file:///android_asset/ask_post.html"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.answer_fragment)
        val posting_list: String? = intent.getStringExtra("posting_index")
        renderWebView(posting_list!!.toInt())

        askPostWebView = findViewById(R.id.askPostWebView)
        askPostWebView.settings.javaScriptEnabled = true
        askPostWebView.settings.domStorageEnabled = true
        askPostWebView.settings.allowContentAccess = true
        askPostWebView.settings.allowFileAccess = true

        commentEditText = findViewById(R.id.commentEditText)
        commentEditText.setOnClickListener{
            commentEditText.requestFocus()
        }

        class WebBrideg(private val mContext: Context) {

            @JavascriptInterface
            fun showToast(code: String) {
                Toast.makeText(mContext, code, Toast.LENGTH_SHORT).show()
            }

            @JavascriptInterface
            fun getwidth(px: Float): Float {
                var resources = resources
                val metrics: DisplayMetrics = resources.getDisplayMetrics()
                val dp = px * (DisplayMetrics.DENSITY_DEFAULT / metrics.densityDpi.toFloat())
                return dp
            }
        }

        askPostWebView.addJavascriptInterface(WebBrideg(this), "Android")


        // edittext 옆 버튼 이벤트 리스너
        var writeCommentBtn : ImageButton = findViewById(R.id.writeCommentBtn)
        writeCommentBtn.setOnClickListener {
            var bodyValue = commentEditText.text.toString()
            putComment(bodyValue,commentEditText)
            commentEditText.text.clear()
            commentEditText.clearFocus()
            var manager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(commentEditText.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            commentPosition = recyclerView.adapter!!.itemCount
            commentEditText.hint = "댓글을 입력하세요"
        }

        var backBtn : ImageView = findViewById(R.id.backBtn)
        backBtn.setOnClickListener{
            finish()
        }

        var answerWriterProfileImage : ImageView = findViewById(R.id.answerWriterProfileImage)
        answerWriterProfileImage.setOnClickListener{
            var intent = Intent(this,UserProfileActivity::class.java)
            startActivity(intent)
        }
        renderCommentUi(commentEditText)




    }


    private fun renderWebView(posting_index : Int){
        var retrofit = RetrofitClient.initClient()
        var sendData = retrofit.create(RetrofitClient.GetSpecificPostApi::class.java)
        sendData.getPost(posting_index).enqueue(object : retrofit2.Callback<ClickedPostData>{
            override fun onFailure(call: Call<ClickedPostData>, t: Throwable) {
                Toast.makeText(this@AnswerActivity, "서버 연결이 불안정합니다", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<ClickedPostData>,
                response: Response<ClickedPostData>
            ) {
                Log.d("이거입니당", response.body()!!.own.toString())
                Log.d("이거입니당", response.body()!!.posting.toString())
                askPostWebView.loadUrl(askWebViewUrl)
                askPostWebView.loadUrl("javascript:getPost('asdf')")
            }

        })
    }

    private fun renderCommentUi(commentEditText : EditText) {
        recyclerView = findViewById(R.id.answerPageRecyclerView)
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
                var intent = Intent(this@AnswerActivity,UserProfileActivity::class.java)
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
        recyclerView = findViewById(R.id.answerPageRecyclerView)
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
                    var intent = Intent(this@AnswerActivity,UserProfileActivity::class.java)
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
                    var intent = Intent(this@AnswerActivity,UserProfileActivity::class.java)
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