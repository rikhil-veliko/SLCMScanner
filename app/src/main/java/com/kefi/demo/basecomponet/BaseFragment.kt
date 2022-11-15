package com.kefi.demo.basecomponet

import android.widget.Toast
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {

    fun showToast(Message: String) {
        Toast.makeText(activity, Message, Toast.LENGTH_LONG).show()
    }
}