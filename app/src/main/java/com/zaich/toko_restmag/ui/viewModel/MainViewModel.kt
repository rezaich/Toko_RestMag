package com.zaich.toko_restmag.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zaich.toko_restmag.server.ApiClient
import com.zaich.toko_restmag.server.ApiInterface
import com.zaich.toko_restmag.model.LoginResponse

class LoginViewModel:ViewModel() {
    private val serverInterface: ApiInterface = ApiClient().getApiClient()!!.create(ApiInterface::class.java)
    private val userLogin = MutableLiveData<LoginResponse>()
}