package com.example.dungeonans.Utils

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.text.SimpleDateFormat
import java.util.*

fun String?.isJsonObject():Boolean {
    return this?.startsWith("{") == true && this.endsWith("}")
}

// 문자열이 제이슨 배열인지
fun String?.isJsonArray() : Boolean {
    return this?.startsWith("[") == true && this.endsWith("]")
}

// 날짜 포멧
@SuppressLint("SimpleDateFormat")
fun Date.toString() : String {
    val format = SimpleDateFormat("HH:mm:ss")
    return format.format(this)
}


// 에딧 텍스트에 대한 익스텐션
fun EditText.onMyTextChanged(completion: (Editable?) -> Unit){
    this.addTextChangedListener(object: TextWatcher {

        override fun afterTextChanged(editable: Editable?) {
            completion(editable)
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

    })
}

