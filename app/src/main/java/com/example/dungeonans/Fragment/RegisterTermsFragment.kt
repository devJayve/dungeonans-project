package com.example.dungeonans.Fragment

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.example.dungeonans.Activity.RegisterActivity
import com.example.dungeonans.R

class RegisterTermsFragment : Fragment() {

    private lateinit var registerActivity : RegisterActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        registerActivity = activity as RegisterActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register_terms, container, false)

        // button
        val backPageBtn = view.findViewById<ImageButton>(R.id.backPageBtn)
        val nextPageBtn = view.findViewById<Button>(R.id.nextPageBtn)
        val term1DetailBtn = view.findViewById<Button>(R.id.term1DetailBtn)
        val term2DetailBtn = view.findViewById<Button>(R.id.term2DetailBtn)

        // checkbox
        val term1CheckBox = view.findViewById<CheckBox>(R.id.term1CheckBox)
        val term2CheckBox = view.findViewById<CheckBox>(R.id.term2CheckBox)
        val term3CheckBox = view.findViewById<CheckBox>(R.id.term3CheckBox)
        val term4CheckBox = view.findViewById<CheckBox>(R.id.term4CheckBox)
        val termCheckboxList =
            arrayListOf<CheckBox>(term1CheckBox, term2CheckBox, term3CheckBox, term4CheckBox)


        // click listener
        backPageBtn.setOnClickListener {
            moveBackPageEvent() // 뒤로가기
        }

        nextPageBtn.setOnClickListener {
            moveNextPageEvent() // 다음 페이지
        }

        term1DetailBtn.setOnClickListener {
            showTermDetailEvent(0)
        }

        term2DetailBtn.setOnClickListener {
            showTermDetailEvent(1)
        }

            return view
        }

    private fun moveBackPageEvent() {
        val backPageDialog = AlertDialog.Builder(registerActivity)
            .setMessage("회원가입을 그만두시겠습니까?")
            .setPositiveButton("네", DialogInterface.OnClickListener{ _, _ ->
                registerActivity.transFragEvent(10) })
            .setNegativeButton("계속 할래요", null)
            .show()
    }

    private fun moveNextPageEvent() {
        // 약관 CheckBox
        val term1CheckBox = view?.findViewById<CheckBox>(R.id.term1CheckBox)
        val term2CheckBox = view?.findViewById<CheckBox>(R.id.term2CheckBox)
        val term3CheckBox = view?.findViewById<CheckBox>(R.id.term3CheckBox)

        if (!term1CheckBox!!.isChecked || !term2CheckBox!!.isChecked || !term3CheckBox!!.isChecked) {
            registerActivity.showToastEvent("필수 약관에 동의해주세요.", true)
        } else {
            registerActivity.transFragEvent(1)
        }
    }

    private fun showTermDetailEvent(detailNum : Int) {
        // 약관 상세 다이얼로그 띄우기
        when(detailNum) {
            1 -> Log.d("Tag","")
            2 -> Log.d("Tag", "")
        }
    }
}