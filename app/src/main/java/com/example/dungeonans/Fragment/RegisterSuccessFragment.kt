package com.example.dungeonans.Fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.example.dungeonans.Activity.LoginActivity
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

        val nextPageBtn = view.findViewById<ImageButton>(R.id.nextPageBtn)

        nextPageBtn.setOnClickListener {
            val intent = Intent(registerActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        return view
    }
}