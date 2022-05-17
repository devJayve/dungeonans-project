package com.example.dungeonans.Fragment

import GridSpacingItemDecoration
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dungeonans.Activity.ProfilePostActivity
import com.example.dungeonans.Adapter.BlogCardViewAdapter
import com.example.dungeonans.DataClass.BlogData
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.dungeonans.Activity.MainActivity
import com.example.dungeonans.Activity.UserProfileEditActivity
import com.example.dungeonans.DataClass.LoginResponse
import com.example.dungeonans.DataClass.ProfileData
import com.example.dungeonans.R
import com.example.dungeonans.Retrofit.RetrofitClient
import com.example.dungeonans.Utils.PrefManager
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback


class MyProfileFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.myprofilepage_fragment,container,false)

        getProfileInfo(view)

        var settingBtn = view.findViewById<ImageButton>(R.id.settingBtn)
        settingBtn.setOnClickListener{
            val bottomDrawer = BottomSheetFragment()
            bottomDrawer.show(parentFragmentManager,BottomSheetFragment.TAG)
        }

        var instaBtn = view.findViewById<Button>(R.id.instaBtn)
        instaBtn.setOnClickListener{
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/yongjunkimmm/"))
            startActivity(intent)
        }

        var gitBtn = view.findViewById<Button>(R.id.gitBtn)
        gitBtn.setOnClickListener{
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/yongkingg"))
            startActivity(intent)
        }

        var faceBookBtn = view.findViewById<Button>(R.id.faceBookBtn)
        faceBookBtn.setOnClickListener {

        }

        var gitGrassBtn = view.findViewById<Button>(R.id.gitGrassBtn)
        gitGrassBtn.setOnClickListener{

        }

        var showAllMyPostBtn : Button = view.findViewById(R.id.showAllMyPostBtn)
        showAllMyPostBtn.setOnClickListener{
            var intent = Intent(context, ProfilePostActivity::class.java)
            intent.putExtra("key","1")
            startActivity(intent)
        }

        var showAllMyCommentBtn : Button = view.findViewById(R.id.showAllMyCommentBtn)
        showAllMyCommentBtn.setOnClickListener{
            var intent = Intent(context, ProfilePostActivity::class.java)
            intent.putExtra("key","2")
            startActivity(intent)
        }

        var showAllMyBlogBtn : Button = view.findViewById(R.id.showAllMyBlogBtn)
        showAllMyBlogBtn.setOnClickListener{
            var intent = Intent(context, ProfilePostActivity::class.java)
            intent.putExtra("key","3")
            startActivity(intent)
        }

        val editProfileBtn : Button = view.findViewById(R.id.editProfileBtn)
        editProfileBtn.setOnClickListener {
            val intent = Intent(context, UserProfileEditActivity::class.java)
            startActivity(intent)
        }


        renderUi(view)
        return view
    }

    private fun getProfileInfo(view : View) {
        var retrofit = RetrofitClient.initClient()
        var getProfileInfo = retrofit.create(RetrofitClient.ProfileApi::class.java)
        var getToken = PrefManager.getUserToken()
        getProfileInfo.getMyProfile(getToken).enqueue(object : retrofit2.Callback<ProfileData> {
            override fun onFailure(call: Call<ProfileData>, t: Throwable) {
                Toast.makeText(context,"서버 연결이 불안정합니다.",Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<ProfileData>, response: Response<ProfileData>) {
                val nameTextView = view.findViewById<TextView>(R.id.nameTextView)
                nameTextView.text = response.body()!!.profile_list[0].name
                val nicknameTextView = view.findViewById<TextView>(R.id.nicknameTextView)
                nicknameTextView.text = response.body()!!.profile_list[0].nickname
            }
        })
    }

    private fun renderUi(view: View) {
        var recyclerView : RecyclerView = view.findViewById(R.id.profilePageRecyclerView)
        var data : MutableList<BlogData> = setData()
        var adapter = BlogCardViewAdapter()
        adapter.listData = data
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(context,2)
        recyclerView.addItemDecoration(GridSpacingItemDecoration(2,60,false))
    }

    private fun setData() : MutableList<BlogData> {
        var data : MutableList<BlogData>  = mutableListOf()
        for (index in 0 until 4) {
            var cardViewTitle = "$index"
            var cardViewBody = "김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해 김주영 진짜 사랑해"
            var cardViewWriter = "$index 번째 작성자"
            var cardViewProfile = R.drawable.profile_base_icon
            var cardViewLanguageTag = "Notion"
            var listData = BlogData(cardViewTitle,cardViewBody,cardViewWriter,cardViewProfile,cardViewLanguageTag)
            data.add(listData)
        }
        return data
    }
}
