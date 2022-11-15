package com.kefi.demo.basecomponet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.kefi.demo.R

class BaseActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.frame) as NavHostFragment
        navController = navHostFragment.navController
    }
}