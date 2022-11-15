package com.kefi.demo.util

import android.content.Context
import android.content.SharedPreferences

class PrefManager(context: Context) {
    private val sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(TAG_NAME, Context.MODE_PRIVATE)
    }
    companion object {
        private const val TAG_NAME = "csApp.prefs"
        private const val IS_Editing = "isEditing"
        private const val Editing_Status = "editingStatus"
        private const val Token = "token"
        private const val ISLOGIN = "isLogin"
    }
    private fun putString(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    private fun putLong(key: String, value: Long) {
        val editor = sharedPreferences.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    private fun putInt(key: String, value: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    private fun putBoolean(key: String, value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }


    fun clearData() {
        sharedPreferences.edit().clear().apply()
    }
    /*var editingStatus: String?
    get() = sharedPreferences.getString(Editing_Status, Status.LOADING.toString())
    set(status) = putString(Editing_Status, status!!)*/

    var gettoken: String?
        get() = sharedPreferences.getString(Token, "")
        set(token) = putString(Token, token!!)

    var isLogin: Boolean?
        get() = sharedPreferences.getBoolean(ISLOGIN, false)
        set(status) = putBoolean(ISLOGIN, status!!)
}