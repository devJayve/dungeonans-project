package com.example.dungeonans.Fragment

import android.content.Intent
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dungeonans.Activity.UserProfileActivity
import com.example.dungeonans.Adapter.AskRVAdapter
import com.example.dungeonans.DataClass.AskData
import com.example.dungeonans.DataClass.QnAPostData
import com.example.dungeonans.DataClass.board_req_format
import com.example.dungeonans.DataClass.posting_format_res
import com.example.dungeonans.R
import com.example.dungeonans.Retrofit.RetrofitClient
import com.example.dungeonans.Space.LinearSpacingItemDecoration
import kotlinx.android.synthetic.main.answer_fragment.view.*
import kotlinx.android.synthetic.main.ask_default_fragment.view.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.*
import java.lang.reflect.TypeVariable

class AskDefaultFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.ask_default_fragment,container,false)

//        var unAnsweredLoading : ProgressBar = view.findViewById(R.id.unAnsweredLoading)
//        var allPostLoading : ProgressBar = view.findViewById(R.id.allPostLoading)
//        var answeredLoading : ProgressBar = view.findViewById(R.id.answeredLoading)

        var showAllPostBtn : Button = view.findViewById(R.id.showAllPostBtn)
        showAllPostBtn.setOnClickListener{
            changeFragmentLogic(AskShowAllPostFragment(), "0")
        }

        var showUnAnsweredBtn : Button = view.findViewById(R.id.showUnAnsweredPostBtn)
        showUnAnsweredBtn.setOnClickListener{
            changeFragmentLogic(AskShowAllPostFragment(), "1")
        }

        var showAnsweredBtn : Button = view.findViewById(R.id.showAnsweredPostBtn)
        showAnsweredBtn.setOnClickListener{
            changeFragmentLogic(AskShowAllPostFragment(), "2")
        }
        renderUi(view)

//        runBlocking {
//            var render = launch {
//                Log.d("tag",serverConnected.toString())
//            }
//            for(index in 0 until 3) {
//                if (!(0 in serverConnected)) {
//                    allPostLoading.visibility = View.GONE
//                    unAnsweredLoading.visibility = View.GONE
//                    answeredLoading.visibility = View.GONE
//                } else {
//                    if (serverConnected[0] == 0) {
//                        allPostLoading.visibility = View.VISIBLE
//                    } else if (serverConnected[1] == 0) {
//                        unAnsweredLoading.visibility = View.VISIBLE
//                    } else {
//                        answeredLoading.visibility = View.VISIBLE
//                    }
//                }
//            }
//        }

        return view
    }

    private fun changeFragmentLogic(fragment : Fragment, parameter : String) {
        var parentFragment = parentFragment as AskFragment
        parentFragment.changeFragment(fragment, parameter)
    }

    private fun renderUi(view: View) {

        var retrofit = RetrofitClient.initClient()
        var data = board_req_format(0,2)

        var getAllPost = retrofit.create(RetrofitClient.GetQnAPostApi::class.java)
        getAllPost.sendBoardReq(data).enqueue(object : Callback<QnAPostData> {
            override fun onFailure(call: Call<QnAPostData>, t: Throwable) {
                Toast.makeText(context, "서버 연결이 불안정합니다", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<QnAPostData>, response: Response<QnAPostData>) {
                var allPost_1 : ConstraintLayout = view.findViewById(R.id.allPost_1)
                allPost_1.setOnClickListener{
                    var parameter = "0"
                    var position = 1
                }
                var allPost_2 : ConstraintLayout = view.findViewById(R.id.allPost_2)
                allPost_2.setOnClickListener{
                    var parameter = "0"
                    var position = 2
                }

                var firstPostData = response.body()!!.posting_list[0]
                var allPost_1_title : TextView = view.findViewById(R.id.allPost_1_title)
                allPost_1_title.text = firstPostData.title
                var allPost_1_content : TextView = view.findViewById(R.id.allPost_1_content)
                allPost_1_content.text = firstPostData.content
                var allPost_1_likecount : TextView = view.findViewById(R.id.allPost_1_likecount)
                allPost_1_likecount.text = firstPostData.like_num.toString()
                var allPost_1_commentcount : TextView = view.findViewById(R.id.allPost_1_commentcount)
                allPost_1_commentcount.text = firstPostData.comment_num.toString()

                var secondPostData = response.body()!!.posting_list[1]
                var allPost_2_title : TextView = view.findViewById(R.id.allPost_2_title)
                allPost_2_title.text = secondPostData.title
                var allPost_2_content : TextView = view.findViewById(R.id.allPost_2_content)
                allPost_2_content.text = secondPostData.content
                var allPost_2_likecount : TextView = view.findViewById(R.id.allPost_2_likecount)
                allPost_2_likecount.text = secondPostData.like_num.toString()
                var allPost_2_commentcount : TextView = view.findViewById(R.id.allPost_2_commentcount)
                allPost_2_commentcount.text = secondPostData.comment_num.toString()
            }
        })

        var getAnsweredPost = retrofit.create(RetrofitClient.GetUnAnsweredApi::class.java)
        getAnsweredPost.sendBoardReq(data).enqueue(object : Callback<QnAPostData> {
            override fun onFailure(call: Call<QnAPostData>, t: Throwable) {
                Toast.makeText(context, "서버 연결이 불안정합니다", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<QnAPostData>, response: Response<QnAPostData>) {
                var unAnsweredPost_1 : ConstraintLayout = view.findViewById(R.id.unAnsweredPost_1)
                unAnsweredPost_1.setOnClickListener{
                    var parameter = "0"
                    var position = 1
                }

                var unAnsweredPost_2 : ConstraintLayout = view.findViewById(R.id.unAnsweredPost_2)
                unAnsweredPost_2.setOnClickListener{
                    var parameter = "0"
                    var position = 2
                }

                var firstPostData = response.body()!!.posting_list[0]
                var unansweredpost_1_title : TextView = view.findViewById(R.id.unAnsweredPost_1_title)
                unansweredpost_1_title.text = firstPostData.title
                var unansweredpost_1_content : TextView = view.findViewById(R.id.unAnsweredPost_1_content)
                unansweredpost_1_content.text = firstPostData.content
                var unansweredpost_1_likenum : TextView = view.findViewById(R.id.unAnsweredPost_1_likecount)
                unansweredpost_1_likenum.text = firstPostData.like_num.toString()
                var unansweredpost_1_commentnum : TextView = view.findViewById(R.id.unAnsweredPost_1_commentcount)
                unansweredpost_1_commentnum.text = firstPostData.comment_num.toString()

                var secondPostData = response.body()!!.posting_list[1]
                var unansweredpost_2_title : TextView = view.findViewById(R.id.unAnsweredPost_2_title)
                unansweredpost_2_title.text = secondPostData.title
                var unansweredpost_2_content : TextView = view.findViewById(R.id.unAnsweredPost_2_content)
                unansweredpost_2_content.text = secondPostData.content
                var unansweredpost_2_likecount : TextView = view.findViewById(R.id.unAnsweredPost_2_likecount)
                unansweredpost_2_likecount.text = secondPostData.like_num.toString()
                var unansweredpost_2_commentcount : TextView = view.findViewById(R.id.unAnsweredPost_2_commentcount)
                unansweredpost_2_commentcount.text = secondPostData.comment_num.toString()
            }
        })

        var getUnAnsweredPost = retrofit.create(RetrofitClient.GetClosedApi::class.java)
        getUnAnsweredPost.sendBoardReq(data).enqueue(object : Callback<QnAPostData> {
            override fun onFailure(call: Call<QnAPostData>, t: Throwable) {
                Toast.makeText(context, "서버 연결이 불안정합니다", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<QnAPostData>, response: Response<QnAPostData>) {
                var answeredPost_1 : ConstraintLayout = view.findViewById(R.id.answeredPost_1)
                answeredPost_1.setOnClickListener{
                    var parameter = "0"
                    var position = 1
                }
                var answeredPost_2 : ConstraintLayout = view.findViewById(R.id.answeredPost_2)
                answeredPost_2.setOnClickListener{
                    var parameter = "0"
                    var position = 2
                }
                var firstPostData = response.body()!!.posting_list[0]
                var answeredpost_1_title : TextView = view.findViewById(R.id.answeredPost_1_title)
                answeredpost_1_title.text = firstPostData.title
                var answeredpost_1_content : TextView = view.findViewById(R.id.answeredPost_1_content)
                answeredpost_1_content.text = firstPostData.content
                var answeredpost_1_likecount : TextView = view.findViewById(R.id.answeredPost_1_likecount)
                answeredpost_1_likecount.text = firstPostData.like_num.toString()
                var answeredpost_1_commentcount : TextView = view.findViewById(R.id.answeredPost_1_commentcount)
                answeredpost_1_commentcount.text = firstPostData.comment_num.toString()

                var secondPostData = response.body()!!.posting_list[1]
                var answeredpost_2_title : TextView = view.findViewById(R.id.answeredPost_2_title)
                answeredpost_2_title.text = secondPostData.title
                var answeredpost_2_content : TextView = view.findViewById(R.id.answeredPost_2_content)
                answeredpost_2_content.text = secondPostData.content
                var answeredpost_2_likecount : TextView = view.findViewById(R.id.answeredPost_2_likecount)
                answeredpost_2_likecount.text = secondPostData.like_num.toString()
                var answeredpost_2_commentcount : TextView = view.findViewById(R.id.answeredPost_2_commentcount)
                answeredpost_2_commentcount.text = secondPostData.comment_num.toString()

//                var mainLoading : ProgressBar = view.findViewById(R.id.mainLoading)
//                mainLoading.visibility = View.GONE
            }
        })

        var bestUserLayout : LinearLayout = view.findViewById(R.id.bestUserLayout)
        for (index in 0 until 5) {
            var userCardView = layoutInflater.inflate(R.layout.best_user_cardview,null)
            userCardView.setOnClickListener {
                var intent = Intent(context, UserProfileActivity::class.java)
                startActivity(intent)
            }
            bestUserLayout.addView(userCardView)
        }
    }
}