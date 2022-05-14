package com.example.dungeonans.Activity

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dungeonans.R

class AskApplyActivity :AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.ask_apply_fragment)

        var askPostWebView = findViewById<WebView>(R.id.ask_webview)
        var applyWebView = findViewById<WebView>(R.id.body_webview)

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
            fun getwidth(): Float {
                var width: Float = 0.0f
                var linear = findViewById<LinearLayout>(R.id.body_box)
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

        applyWebView.addJavascriptInterface(WebBrideg(this), "Android")
        askPostWebView.addJavascriptInterface(WebBrideg(this), "Android")

        askPostWebView.loadUrl(askWebViewUrl)
        applyWebView.loadUrl(applyWebViewUrl)
    }
}