package com.example.dungeonans.Fragment

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.dungeonans.Activity.UserProfileEditActivity
import com.example.dungeonans.R
import com.example.dungeonans.databinding.FragmentEditStackBinding



class ProfileStackEditFragment : Fragment() {

    private var _binding: FragmentEditStackBinding? = null
    private val binding get() = _binding!!

    private lateinit var profileActivity : UserProfileEditActivity
    private val btnIdxArrayList = arrayListOf<Int>()
    private val customBtnList = arrayListOf<String>()


    override fun onAttach(context: Context) {
        super.onAttach(context)
        profileActivity = activity as UserProfileEditActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditStackBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.backBtn.setOnClickListener {
            //profileActivity.transFragEvent(0)
        }

        binding.addCustomStackBtn.setOnClickListener {  // 커스텀 스택 추가
            addCustomStack()
        }

        //val radioBtnId = binding.stackRadioGroup.checkedRadioButtonId
        //val radioBtn = binding.stackRadioGroup.findViewById<RadioButton>(radioBtnId)

        // binding.confirmStackBtn.isEnabled = radioBtn.isChecked

        //val index: Int = stack_radio_group.indexOfChild(stack_radio_group.findViewById<RadioButton>(stack_radio_group.checkedRadioButtonId))

//        binding.stackRadioGroup.setOnCheckedChangeListener { _, checkedId ->
//            Log.d("TAG", "$checkedId")
//            if (checkedId in btnIdxArrayList)
//                btnIdxArrayList.remove(checkedId)
//            else
//                btnIdxArrayList.add(checkedId)
//        }

        return view
    }

    private fun setCustomStack() {

        val radioButton = RadioButton(profileActivity)
        radioButton.text = ""
        radioButton.id = View.generateViewId()

//        binding.stackRadioGroup.addView(radioButton)

    }


    private fun addCustomStack() {
        val view = layoutInflater.inflate(R.layout.dialog_custom_stack, null)

        AlertDialog.Builder(profileActivity)
            .setCancelable(true)
            .setView(view)
            .show()

            .also { alertDialog ->
                if (alertDialog == null) {
                    return@also
                }


                val confirmBtn = view.findViewById<Button>(R.id.confirm_btn)
                val customStackET = view.findViewById<EditText>(R.id.custom_stack_et)


                customStackET?.addTextChangedListener(object : TextWatcher{
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        confirmBtn.isEnabled = customStackET.text != null
                    }

                    override fun afterTextChanged(p0: Editable?) = Unit
                })

                confirmBtn?.setOnClickListener {
                    Log.d("TAG","confirmBtn clicked")
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

