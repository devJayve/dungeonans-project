package com.example.dungeonans.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.example.dungeonans.R

class SetAccountFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.setting_account_page_fragment, container, false)

        var backBtn : ImageButton = view.findViewById(R.id.backBtn)
        backBtn.setOnClickListener{
            requireActivity().finish()
        }

        return view
    }
}