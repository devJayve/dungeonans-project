package com.example.dungeonans.DataClass

import com.google.gson.annotations.SerializedName

data class NoneData(
    @SerializedName("success") var success: Boolean,
    @SerializedName("errmsg") var errmsg: String
)

data class ClickedPostData(
    @SerializedName("success") var success : Boolean,
    @SerializedName("errmsg") var errmsg : String,
    @SerializedName("posting") var posting: List<posting_format_res>
)

data class board_req_format(
    @SerializedName("start_index") var start_index  : Int,
    @SerializedName("post_cnt") var post_cnt : Int,
)

data class send_post_cnt(
    @SerializedName("post_cnt") var post_cnt : Int
)

data class CommunityPostData(
    var success : Boolean,
    var errmsg : String,
    var posting_list : List<posting_format_res>,
    var end_index : Int,
    var board_tag_list : List<TagTextData>
)

data class CommunityHotPostData(
    var success : Boolean,
    var errmsg: String,
    var posting_list: List<posting_format_res>
)

data class Comment(
    var success: Boolean,
    var errmsg: String,
    var comment_list : List<comment_format_res>
)

data class QnAPostData(
    var success : Boolean,
    var errmsg: String,
    var posting_list: List<posting_format_res>
)

data class BlogPostData(
    var success: Boolean,
    var errmsg: String,
    var posting_list: List<posting_format_res>,
    var end_index: Int
)

data class GetCommunityPostByTag(
    var success: Boolean,
    var errmsg: String,
    var posting_list: List<posting_format_res>,
    var end_index: Int
)

data class posting_format_res(
    var board_index : Int,
    var posting_index : Int,
    var name : String,
    var id : String,
    var nickname : String,
    var title : String,
    var content : String,
    var date : String,
    var like_num : Int,
    var comment_num : Int,
    var board_tag : Int,
    var row_number : String
)

data class TagTextData(
    var tagText : String
)

data class language_tag(
    var python : Boolean,
    var kotlin : Boolean,
    var javascript : Boolean,
    var html : Boolean,
    var cpp : Boolean,
    var clang : Boolean,
    var java : Boolean,
    var swift : Boolean,
    var sql : Boolean,
    var shell : Boolean
)

data class comment_format_req(
    var board_index: Int,
    var posting_index: Int,
    var content : String,
    var nested_comment_index : Int
)

data class comment_format_res(
    var board_index: Int,
    var posting_index: Int,
    var comment_index:Int,
    var name : String,
    var nickname: String,
    var content: String,
    var like_num: Int,
    var nested_comment_index: Int // 대댓글 갯수
)

data class put_comment_req(
    var posting_index: String,
    var content : String
)


data class LoginData(
    val id: String,
    val pw : String
)

data class LoginResponse(
    val errmsg: String,
    val success : Boolean,
    val token : String
)

