package com.zaich.toko_restmag.model

import com.google.gson.annotations.SerializedName

data class MenuModel (
    val name : String,
    val price : Int,
    val description : String,
   val category_id : Int,
        )