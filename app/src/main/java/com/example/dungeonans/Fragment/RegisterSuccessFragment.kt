package com.example.dungeonans.Fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dungeonans.Activity.RegisterActivity
import com.example.dungeonans.R

class RegisterSuccessFragment : Fragment() {

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
        val view = inflater.inflate(R.layout.fragment_register_complete, container, false)




        return view
    }
}