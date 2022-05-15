package com.example.dungeonans.Fragment

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dungeonans.Adapter.AskRVAdapter
import com.example.dungeonans.DataClass.AskData
import com.example.dungeonans.R
import com.example.dungeonans.Space.LinearSpacingItemDecoration


class AskShowAllPostFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.askpage_viewall_fragment,container,false)
        var postSetMode = requireArguments().getString("Value")
        renderUi(view)
        setSpinner(view)

        return view
    }

    private fun renderUi(view: View) {
        var recyclerView : RecyclerView = view.findViewById(R.id.askAllPostPageRecyclerView)
        var data : MutableList<AskData> = setData()
        var adapter = AskRVAdapter()
        adapter.listData = data
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        var space = LinearSpacingItemDecoration(20)
        recyclerView.addItemDecoration(space)
    }

    private fun setSpinner(view:View) {
        val spinner = view.findViewById<Spinner>(R.id.setPostTypeSpinner)
        val spinnerItem = resources.getStringArray(R.array.setPostType)
        val myAdapter = object : ArrayAdapter<String>(requireContext(),android.R.layout.simple_spinner_dropdown_item,spinnerItem) {
            override fun getDropDownView(position: Int,convertView: View?,parent: ViewGroup): View {
                val view: TextView = super.getDropDownView(position,convertView,parent) as TextView
                view.setTextSize(TypedValue.COMPLEX_UNIT_SP,10f)
                return view
            }
        }
        spinner.adapter = myAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,view: View,position: Int,id: Long) {
                //아이템이 클릭 되면 맨 위부터 position 0번부터 순서대로 동작하게 됩니다.
                when (position) {
                    0 -> {
                    }
                    1 -> {
                    }
                    else -> {
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }

    private fun setData() : MutableList<AskData> {
        var data : MutableList<AskData>  = mutableListOf()
        for (index in 0 until 6) {
            var askUserImage = R.drawable.profile_base_icon
            var askUserName = "김주영"
            var askUserNickname = "(@김주영사랑해)"
            var askPostTitle = "김주영사랑해리얼로"
            var askPostBody = "안녕하세요 저는 김주영을 사랑합니다"
            var askStatusImage = R.drawable.unanswered_icon
            var askPostLikeCount = "999"
            var askPostCommentCount = "999"
            var listData = AskData(askUserImage,askUserName,askUserNickname,askPostTitle,askPostBody,askStatusImage,askPostLikeCount,askPostCommentCount)
            data.add(listData)
        }
        return data
    }
}