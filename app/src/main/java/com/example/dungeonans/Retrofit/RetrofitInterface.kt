package com.example.dungeonans.Retrofit

import com.example.dungeonans.Utils.API
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {

        // https://www.unsplash.com/search/photos/?query=""

        @GET(API.SEARCH_BLOGS)
        fun searchPhotos(@Query("query") searchTerm: String) : Call<JsonElement>

        @GET(API.SEARCH_PROFILES)
        fun searchUsers(@Query("query") searchTerm: String) : Call<JsonElement>

}