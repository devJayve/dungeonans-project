package com.example.dungeonans.Fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.dungeonans.R

class AskFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.ask_head_fragment,container,false)
        childFragmentManager.beginTransaction().replace(R.id.askPageHeadLayout,AskDefaultFragment()).commit()
        return view
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        var callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (!childFragmentManager.findFragmentById(R.id.askPageHeadLayout).toString().contains("AskDefaultFragment")) {
                    childFragmentManager.beginTransaction().replace(R.id.askPageHeadLayout,AskDefaultFragment()).commit()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    fun changeFragment(fragment : Fragment, parameter : String) {
        var bundle = Bundle()
        bundle.putString("Value",parameter)
        fragment.arguments = bundle
        childFragmentManager.beginTransaction().replace(R.id.askPageHeadLayout,fragment).commit()
    }
}