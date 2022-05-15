package com.example.dungeonans.Fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.dungeonans.Activity.FindAccountActivity
import com.example.dungeonans.R
import com.raycoarana.codeinputview.CodeInputView


class FindPwFragment : Fragment() {

    private lateinit var findActivity : FindAccountActivity
    private lateinit var mCountDownTimer : CountDownTimer
    private var isRunning = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        findActivity = activity as FindAccountActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container : ViewGroup?,
        savedInstanceState : Bundle?
    ) : View {
        val view = inflater.inflate(R.layout.findpw_fragment_layout, container, false)

        // button
        val sendCodeBtn = view?.findViewById<Button>(R.id.sendCodeBtn)
        val backPageBtn = view.findViewById<ImageButton>(R.id.backPageBtn)

        //EditText
        val idET = view?.findViewById<EditText>(R.id.findPwIdET)
        val emailET = view?.findViewById<EditText>(R.id.findPwEmailET)

        //codeInput
        var mailCodeInput = view.findViewById<CodeInputView>(R.id.codeInputView)


        //listener
        sendCodeBtn?.setOnClickListener {
            sendCodeEvent(idET, emailET) // 이메일로 인증 코드 전송
        }

        backPageBtn.setOnClickListener {
            moveBackPageEvent()
        }

        mailCodeInput?.addOnCompleteListener {
            setMailCodeView(mailCodeInput)
        }


        return view
    }

    private fun sendCodeEvent(idET : EditText?, emailET: EditText?) {
        val mailCodeLayout = view?.findViewById<LinearLayout>(R.id.codeInputLayout)
        val idString = idET?.text.toString()
        val emailString = emailET?.text.toString()

        if (idString.isEmpty() || emailString.isEmpty()) {
            Toast.makeText(findActivity, "아이디 또는 이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(findActivity, "인증번호가 전송되었습니다.", Toast.LENGTH_SHORT).show()
            mailCodeLayout?.visibility = View.VISIBLE

            if (isRunning) {
                mCountDownTimer.cancel()
            }
            startCountDown()
        }

    }

    fun startCountDown() {
        val timeTV = view?.findViewById<TextView>(R.id.timeTV)
        val mailCodeLayout = view?.findViewById<LinearLayout>(R.id.codeInputLayout)

        isRunning = true

        mCountDownTimer = object : CountDownTimer(180000, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(time: Long) {
                val duration = time/1000
                val sec = duration % 60
                val min = duration / 60
                if (sec >= 10) {
                    timeTV?.text = "${min}:${sec}"
                }
                else {
                    timeTV?.text = "${min}:0${sec}"
                }
            }

            override fun onFinish() {
                isRunning = false
                mailCodeLayout?.visibility = View.INVISIBLE
            }
        }
        mCountDownTimer.start()
    }

    private fun moveBackPageEvent() {
        if (isRunning) {
            mCountDownTimer.cancel()
        }
        findActivity.moveBackPage()
    }

    private fun setMailCodeView(mailCodeInput : CodeInputView) {
        if(mailCodeInput.code.toInt() == 123456) {
            mailCodeInput.setAnimateOnComplete(true)
            findActivity.moveBackPage()
            Toast.makeText(findActivity, "이메일이 전송되었습니다.", Toast.LENGTH_SHORT).show()
        }
        else {
            mailCodeInput.setError("인증번호가 올바르지 않습니다.")
        }

        mailCodeInput.setEditable(true)
        mailCodeInput.code = ""
    }

    fun connectLoginApi() { // 인증번호 보내는 api 연결

    }

}