package com.example.dungeonans.Activity

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebView.WebViewTransport
import android.webkit.WebViewClient
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dungeonans.R


class AskApplyActivity :AppCompatActivity() {

    lateinit var content : String
    lateinit var name : String
    lateinit var nickname : String
    lateinit var title : String
    lateinit var date : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.ask_apply_fragment)
        content = intent.getStringExtra("posting").toString()
        name = intent.getStringExtra("name").toString()
        nickname = intent.getStringExtra("nickname").toString()
        title = intent.getStringExtra("title").toString()
        date = intent.getStringExtra("date").toString()

        var askPostWebView = findViewById<WebView>(R.id.ask_webview)
        var applyWebView = findViewById<WebView>(R.id.body_webview)
        var writerName = findViewById<TextView>(R.id.writerName)
        var writerNickName = findViewById<TextView>(R.id.writerNickName)
        var writerDate = findViewById<TextView>(R.id.writeDate)
        var writeTitle = findViewById<TextView>(R.id.title_to_answer)

        writerName.text = name
        writerNickName.text = nickname
        writeTitle.text = title
        writerDate.text = date

        var askWebViewUrl = "file:///android_asset/ask_post.html"
        var applyWebViewUrl = "file:///android_asset/auto_highlight.html"

        askPostWebView.settings.javaScriptEnabled = true  //자바스크립트 사용 가능
        askPostWebView.settings.domStorageEnabled = true  //저장공간 이용 가능
        askPostWebView.settings.allowContentAccess = true
        askPostWebView.settings.allowFileAccess = true  //파일 접근 가능

        applyWebView.settings.javaScriptEnabled = true
        applyWebView.settings.domStorageEnabled = true
        applyWebView.settings.allowContentAccess = true
        applyWebView.settings.allowFileAccess = true

        class WebBrideg(private val mContext: Context) {
            @JavascriptInterface
            fun getmywidth(): Float {
                var width: Float = 0.0f
                var linear = findViewById<LinearLayout>(R.id.body_box2)
                width = linear.width.toFloat()
                return width
            }

            @JavascriptInterface
            fun showToast(code: String) {
                Toast.makeText(mContext, code, Toast.LENGTH_SHORT).show()
            }

            @JavascriptInterface
            fun getwidth(px: Float): Float {
                var resources = resources
                val metrics: DisplayMetrics = resources.getDisplayMetrics()
                val dp = px * (DisplayMetrics.DENSITY_DEFAULT / metrics.densityDpi.toFloat())
                return dp
            }

            @JavascriptInterface
            fun getText(innerHTML: String, changeText: String) {
                var myString = innerHTML
                var myText = changeText
                Log.d("qwe", myString)
                Log.d("asdf", myText)

            }

            @JavascriptInterface
            fun getHtml(html: String) {
                //위 자바스크립트가 호출되면 여기로 html이 반환됨
                var source = html
                Log.e("html: ", source)

            }

            @JavascriptInterface
            fun gettest() {
                Toast.makeText(mContext, "Asdfasdf", Toast.LENGTH_SHORT).show()
            }

        }

        applyWebView.addJavascriptInterface(WebBrideg(this), "Android2")
        askPostWebView.addJavascriptInterface(WebBrideg(this), "Android2")

        askPostWebView.setWebViewClient(object : WebViewClient() {
            override fun onPageFinished(view: WebView, weburl: String) {
                askPostWebView.loadUrl("javascript:update_mycode("+ '"' + content +'"'+")")
                askPostWebView.loadUrl("javascript:myupdate2()")
                askPostWebView.loadUrl("javascript:updateBox()")
                applyWebView.loadUrl("javascript:myupdate2()")
//                updateBox
            }
        })


        askPostWebView.loadUrl(askWebViewUrl)
        applyWebView.loadUrl(applyWebViewUrl)
    }
}