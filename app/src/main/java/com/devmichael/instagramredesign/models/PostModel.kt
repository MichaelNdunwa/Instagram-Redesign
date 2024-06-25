package com.devmichael.instagramredesign.models

data class PostModel(
    var picture: Int,
    var profileName: String,
    var location: String,
    var date: String,
    var post: Int,
    var caption: String,
    var liked: Boolean = false,
    var bookmarked: Boolean = false
)
