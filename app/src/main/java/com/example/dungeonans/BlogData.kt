package com.example.dungeonans

import java.io.Serializable

data class BlogData (var thumbnail: String?,
                     var author: String?,
                     var createdAt: String?,
                     var likeCount: Int?): Serializable {


}