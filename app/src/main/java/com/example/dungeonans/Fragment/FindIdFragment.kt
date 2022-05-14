package com.example.dungeonans.Fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.dungeonans.Activity.FindAccountActivity
import com.example.dungeonans.Activity.LoginActivity
import com.example.dungeonans.Activity.MainActivity
import com.example.dungeonans.R

class FindIdFragment : Fragment() {

    private lateinit var findActivity : FindAccountActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        findActivity = activity as FindAccountActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.findid_fragment_layout, container, false)

        // Edit Text
        val nameET = view.findViewById<EditText>(R.id.findIdNameET)
        val emailET = view.findViewById<EditText>(R.id.findIdEmailET)

        // button
        val backPageBtn = view.findViewById<ImageButton>(R.id.backPageBtn)
        val findIdBtn = view.findViewById<Button>(R.id.findIdBtn)

        findIdBtn.setOnClickListener {
            findIdEvent(nameET, emailET)
        }

        backPageBtn.setOnClickListener {
            moveBackPageEvent()
        }

        return view
    }

    private fun findIdEvent(nameET: EditText?, emailET: EditText?) {
        val nameString = nameET?.text.toString()
        val emailString = emailET?.text.toString()

        if (nameString.isEmpty() || emailString.isEmpty()) {
            Toast.makeText(findActivity,"이름 또는 이메일을 입력해주세요", Toast.LENGTH_SHORT).show()
        }
        else {
            connectLoginApi()
        }
    }

    private fun moveBackPageEvent() {
        findActivity.moveBackPage()
    }

    fun connectLoginApi() {

    }
}