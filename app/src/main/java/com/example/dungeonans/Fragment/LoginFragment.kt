package com.example.dungeonans.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.dungeonans.R

class LoginFragment : Fragment() {
//    private lateinit var loginActivity : LoginActivity
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//
//        loginActivity = activity as LoginActivity
//    }
    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.login_activity_layout, container, false)

        // Button
        val loginBtn = view?.findViewById<Button>(R.id.loginBtn)
        //val naverLoginBtn = view.findViewById<ImageButton>(R.id.naverLoginBtn)
        val registerBtn = view?.findViewById<Button>(R.id.registerBtn)
        val findAccountBtn = view?.findViewById<Button>(R.id.findAccountBtn)

        // Edit Text
        val loginIdET = view?.findViewById<EditText>(R.id.idET)?.text.toString()
        val loginPwET = view?.findViewById<EditText>(R.id.pwET)?.text.toString()

        // 네이버 로그인 인스턴스
        var clientId = R.string.naver_client_id.toString()
        var clientSecret = R.string.naver_client_secret.toString()
        var clientName = R.string.app_name.toString()

        //NaverIdLoginSDK.initialize(loginActivity, "i5ZfZ60c1qMiGOnHcYtF", "_Yd130jp5Z", clientName)

//        // 로그인 버튼 클릭
//        loginBtn?.setOnClickListener {
//            pushLoginEvent(loginIdET, loginPwET) // 로그인
//        }
//
////        naverLoginBtn.setOnClickListener {
////            authenticateNaverLogin()
////        }
//
//        registerBtn?.setOnClickListener {
//            loginActivity.registerEvent() // 회원가입
//        }
//
//        findAccountBtn?.setOnClickListener {
//            findExistingAccountEvent() // 계정찾기 다이어로그
//        }

    return view
    }


//    private fun pushLoginEvent(id : String, pw : String) {
//        if (id.isEmpty() || pw.isEmpty()) { // 로그인 아이디, 비밀번호 공백 시
//            loginActivity.showToastEvent("아이디 또는 비밀번호를 다시 확인해주세요." , true)
//        }
//        else {
//            connectLoginApi(id, pw) // login api 연결
//        }
//    }
//
//    private fun authenticateNaverLogin() {
//         //OAuthLoginCallback을 authenticate() 메서드 호출 시 파라미터로 전달하거나 NidOAuthLoginButton 객체에 등록하면 인증이 종료되는 것을 확인할 수 있습니다.
//        val oauthLoginCallback = object : OAuthLoginCallback {
//            override fun onSuccess() {
//                // 네이버 로그인 인증이 성공했을 때 수행할 코드 추가
////                binding.tvAccessToken.text = NaverIdLoginSDK.getAccessToken()
////                binding.tvRefreshToken.text = NaverIdLoginSDK.getRefreshToken()
////                binding.tvExpires.text = NaverIdLoginSDK.getExpiresAt().toString()
////                binding.tvType.text = NaverIdLoginSDK.getTokenType()
////                binding.tvState.text = NaverIdLoginSDK.getState().toString()
//                Log.d(TAG,"네이버 소셜로그인 성공")
//            }
//            override fun onFailure(httpStatus: Int, message: String) {
//                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
//                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
//                Toast.makeText(context,"errorCode:$errorCode, errorDesc:$errorDescription",Toast.LENGTH_SHORT).show()
//            }
//            override fun onError(errorCode: Int, message: String) {
//                onFailure(errorCode, message)
//            }
//        }
//
//        NaverIdLoginSDK.authenticate(loginActivity, oauthLoginCallback)
//
//    }
//
//    fun connectLoginApi(id : String, pw : String) {
//        loginActivity.loginEvent() // 로그인
//
//    }
//
}
