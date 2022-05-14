package com.example.dungeonans.Utils

object Constants {
    const val TAG : String = "로그"
}

enum class SEARCH_TYPE {
    BLOG,
    PROFILE
}

enum class RESPONSE_STATUS {
    OKAY,
    FAIL,
    NO_CONTENT
}



object COMMENT_TYPE {
    const val comment_type1 = 1
    const val comment_type2 = 2
}




object API {
    const val BASE_URL : String = "https://api.unsplash.com/"

    const val CLIENT_ID : String = "dCBMxCFSPbneaVSavlu8363Xl66oaQsez-WSPSBBA3g"

    const val SEARCH_BLOGS : String = "search/photos"
    const val SEARCH_PROFILES : String = "search/users"

}