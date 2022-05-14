package com.example.dungeonans.Retrofit

import android.util.Log
import com.example.dungeonans.BlogData
import com.example.dungeonans.Utils.API
import com.example.dungeonans.Utils.Constants.TAG
import com.example.dungeonans.Utils.RESPONSE_STATUS
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat

class RetrofitManager {

    companion object {
        val instance = RetrofitManager()
    }

    // 레트로핏 인터페이스 가져오기
    private val iRetrofit : RetrofitInterface? = RetrofitClient.getClient(API.BASE_URL)?.create(RetrofitInterface::class.java)


    // 사진 검색 api 호출
    fun searchBlogs(searchTerm: String?, completion: (RESPONSE_STATUS, ArrayList<BlogData>?) -> Unit){

        val term = searchTerm.let {
            it
        }?: ""

//        val term = searchTerm ?: ""

        val call = iRetrofit?.searchPhotos(searchTerm = term).let {
            it
        }?: return
//        val call = iRetrofit?.searchPhotos(searchTerm = term) ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement>{

            // 응답 실패시
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {

                completion(RESPONSE_STATUS.FAIL, null)
            }

            // 응답 성공시
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {

                when(response.code()) {
                    200 -> {
                        response.body()?.let {
                            var parsedBlogDataArray = ArrayList<BlogData>()
                            val body = it.asJsonObject // response.body가 있을 때
                            val results = body.getAsJsonArray("results")
                            val total = body.get("total").asInt

                            if (total == 0) { // 검색 데이터가 없을 때
                                completion(RESPONSE_STATUS.NO_CONTENT, null)
                            } else {
                                results.forEach { resultItem ->
                                    val resultItemObject = resultItem.asJsonObject

                                    // Blog data에 맞게 변환
                                    val user = resultItemObject.get("user").asJsonObject
                                    val userName = user.get("username").asString
                                    val likeCount = resultItemObject.get("likes").asInt
                                    val thumbnailLink = resultItemObject.get("urls").asJsonObject.get("thumb").asString
                                    val createdAt = resultItemObject.get("created_at").asString
                                    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                                    val formatter = SimpleDateFormat("yyyy년\nMM월 dd일")
                                    val outputDataString = formatter.format(parser.parse(createdAt))

                                    val blogItem = BlogData(author = userName,
                                        likeCount = likeCount,
                                        thumbnail = thumbnailLink,
                                        createdAt = createdAt)
                                    parsedBlogDataArray.add(blogItem)
                                }

                                completion(RESPONSE_STATUS.OKAY , parsedBlogDataArray)
                            }
                        }
                    }
                }
            }

        })
    }
}