package com.example.dungeonans.Fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dungeonans.Activity.UserProfileEditActivity
import com.example.dungeonans.databinding.FragmentEditIntroduceBinding
import kotlinx.android.synthetic.main.fragment_edit_introduce.*

class ProfileIntroduceEditFragment : Fragment() {

    private var _binding: FragmentEditIntroduceBinding? = null
    private val binding get() = _binding!!

    private lateinit var profileActivity : UserProfileEditActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        profileActivity = activity as UserProfileEditActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditIntroduceBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.introduceET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.confirmIntroduceBtn.isEnabled = introduce_ET.length() > 0
            }

            override fun afterTextChanged(p0: Editable?) = Unit
        })

        binding.confirmIntroduceBtn.setOnClickListener {
            // 변경된 상태 메세지 서버에 보내주기
            profileActivity.transFragEvent(UserProfileEditFragment().apply {
                arguments = Bundle().apply {
                    putString("introduce", binding.introduceET.text.toString())
                }
            })

        }

        return view
    }
}