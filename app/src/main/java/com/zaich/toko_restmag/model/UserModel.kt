package com.zaich.toko_restmag.model

data class UserModel(
    val name : String ,
    val user_name : String,
    val password : String,
    val is_admin: String
)

data class Users(
    val id : Int,
    val user_name : String,
)

data class UserProfile(
    val name : String ,
    val address : String ,
    val phone : String,
)