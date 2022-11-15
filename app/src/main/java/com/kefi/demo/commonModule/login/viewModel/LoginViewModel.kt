package com.kefi.demo.commonModule.login.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.kefi.demo.R
import com.kefi.demo.util.Validator

class LoginViewModel :ViewModel() {

    fun ValidateLogin(
        context: Context,
        userEmail :String,
        passWord:String

    ): String {
        var Valu: String = ""
        if (userEmail.length == 0) {
            Valu = "Please enter registered email"
        }else if (passWord.length == 0) {
            Valu = "Please enter a password"
        }else if (!Validator.isValidPasswordFormat(passWord)) {
            Valu = context.getString(R.string.password_warning)
        }
        return Valu
    }
}