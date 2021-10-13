package com.zaich.toko_restmag.pegawai.model

import java.io.Serializable

data class ProductIntent(
    val id: Int,
    val productName: String,
    val desc: String,
    val price: Int
) : Serializable
