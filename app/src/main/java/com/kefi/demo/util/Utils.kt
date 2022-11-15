package com.kefi.demo.util

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.navigation.NavController
import com.kefi.demo.variabiles.Constants
import com.kefi.myapplication.variabiles.Keys
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser


import java.security.MessageDigest

object Utils {
    fun convertModelToJson(submitModel: Any): JsonObject {
        val gson = Gson()
        val jsonString = gson.toJson(submitModel)
        val parser = JsonParser()
        return parser.parse(jsonString).asJsonObject
    }

    fun isNetworkConnected(
            context: Context?,
            currentPage: String,
            navController: NavController
    ): Boolean {
        if (context != null) {
            val connMgr = context
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connMgr.activeNetworkInfo
            return if (networkInfo != null && networkInfo.isConnected) {
                true
            } else {
                Keys.requestErrorCode = 9
                val bundle = Bundle()
                bundle.putString(Constants.pageName, currentPage)
                // navController.navigate(R.id.errorPage, bundle)
                false
            }
        } else {
            Keys.requestErrorCode = 9
            //navController.popBackStack(R.id.errorPage, true )

            val bundle = Bundle()
            bundle.putString(Constants.pageName, currentPage)
            //navController.navigate(R.id.errorPage, bundle)
            return false
        }
    }

    fun getSha512(data: String): String {

        val bytes = data.toByteArray()
        val md = MessageDigest.getInstance("SHA-512")
        val digest = md.digest(bytes)
        return digest.fold("", { str, it -> str + "%02x".format(it) })

    }

}