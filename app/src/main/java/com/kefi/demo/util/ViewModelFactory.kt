package com.kefi.demo.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kefi.demo.commonModule.login.viewModel.LoginViewModel
import com.kefi.demo.commonModule.reportingSMP.viewmodel.ReportingSMPViewModel
import com.kefi.demo.repository.MainRepository


class ViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)){
            return LoginViewModel() as T
        } else if (modelClass.isAssignableFrom(ReportingSMPViewModel::class.java)) {
            return ReportingSMPViewModel(MainRepository()) as T
        }
        throw IllegalArgumentException("Unknown class name")


    }
}