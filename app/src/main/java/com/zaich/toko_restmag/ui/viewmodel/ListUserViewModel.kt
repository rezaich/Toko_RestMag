package com.zaich.toko_restmag.ui.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zaich.toko_restmag.model.UserModel
import com.zaich.toko_restmag.server.ApiClient
import com.zaich.toko_restmag.server.ApiInterface

class ListUserViewModel:ViewModel() {
    private val serverInterface: ApiInterface = ApiClient().getApiClient()!!.create(ApiInterface::class.java)
    private val users = MutableLiveData<UserModel>()
    lateinit var sharedPref: SharedPreferences
    var token: String = ""

    fun setUsers(userModel: UserModel){

    }

}