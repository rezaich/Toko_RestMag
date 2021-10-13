package com.zaich.toko_restmag.pegawai.model

data class CartModel(
    var productName: String,
    var priceOfProduct: Int,
    var totalProduct: Int,
    var daydate: String,
    var daytime: String
)
