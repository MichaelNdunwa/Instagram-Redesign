package com.devmichael.instagramredesign.models

data class UserModel(
    var username: String = "",
    var profileImage: String = "",
    var fullName: String = "",
    var followers: Int = 0,
    //TODO: Add more properties as needed
    var following: Int = 0,
    var posts: Int = 0,
    var bio: String = "",
    var dateOfBirth: String = "",
    var email: String = "",
    var uid: String = "",
    var followButton: String = ""
)