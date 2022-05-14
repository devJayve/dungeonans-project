package com.example.dungeonans.Utils

import android.content.Context
import android.util.Log
import com.example.dungeonans.App
import com.example.dungeonans.DataClass.SearchData
import com.google.gson.Gson

object PrefManager {
    private const val SHARED_SEARCH_HISTORY = "shared_search_history"
    private const val KEY_SEARCH_HISTORY = "key_search_history"
    private const val SHARED_ACCOUNT = "shared_account"
    private const val KEY_ACCOUNT = "key_account"

    fun storeUserToken(userToken: String) {
        // 쉐어드 가져오기
        val shared = App.instance.getSharedPreferences(SHARED_ACCOUNT, Context.MODE_PRIVATE)

        // 쉐어드 에디터 가져오기
        val editor = shared.edit()
        editor.putString(KEY_ACCOUNT, userToken)
        editor.apply()
    }

    fun getUserToken(): String {
        val shared = App.instance.getSharedPreferences(SHARED_ACCOUNT, Context.MODE_PRIVATE)

        return shared.getString(KEY_ACCOUNT, "").toString()
    }

    fun deleteUserToken() {
        // 쉐어드 가져오기
        val shared = App.instance.getSharedPreferences(SHARED_ACCOUNT, Context.MODE_PRIVATE)

        val editor = shared.edit()
        editor.remove(KEY_ACCOUNT)
        editor.apply()
    }



    // 검색 목록 저장
    fun storeSearchHistoryList(searchHistoryList: MutableList<SearchData>) {

        // 매개변수로 들어온 배열을 -> 문자열로 변환
        val searchHistoryListString : String = Gson().toJson(searchHistoryList)
        Log.d("TAG", "searchHistoryListString : $searchHistoryListString")

        // 쉐어드 가져오기
        val shared = App.instance.getSharedPreferences(SHARED_SEARCH_HISTORY, Context.MODE_PRIVATE)

        // 쉐어드 에디터 가져오기
        val editor = shared.edit()
        editor.putString(KEY_SEARCH_HISTORY, searchHistoryListString)
        editor.apply()
    }

    // 검색 목록 가져오기
    fun getSearchHistoryList() : MutableList<SearchData> {

        // 쉐어드 가져오기
        val shared = App.instance.getSharedPreferences(SHARED_SEARCH_HISTORY, Context.MODE_PRIVATE)

        val storedSearchHistoryListString = shared.getString(KEY_SEARCH_HISTORY,"")

        var storedSearchHistoryList = ArrayList<SearchData>()

        if (storedSearchHistoryListString!!.isNotEmpty()) {

            // 저장된 문자열을 객체 배열로 변경
            storedSearchHistoryList = Gson().fromJson(storedSearchHistoryListString, Array<SearchData>::class.java).toMutableList() as ArrayList<SearchData>
        }

        return storedSearchHistoryList
    }
}