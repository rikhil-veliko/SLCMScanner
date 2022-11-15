package com.kefi.demo.commonModule.login.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.kefi.demo.R
import com.kefi.demo.basecomponet.BaseActivity
import com.kefi.demo.basecomponet.BaseFragment
import com.kefi.demo.commonModule.login.viewModel.LoginViewModel
import com.kefi.demo.util.ViewModelFactory
import kotlinx.android.synthetic.main.frag_login.view.*

class Login :BaseFragment(), View.OnClickListener {

    lateinit var navController: NavController
    lateinit var rootView: View
    lateinit var viewModel: LoginViewModel

    lateinit var username: EditText
    lateinit var password: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViewModel()

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        rootView = inflater.inflate(R.layout.frag_login, null)
        setUpUI(rootView)

        val navHostFragment = requireActivity().supportFragmentManager
            .findFragmentById(R.id.frame) as NavHostFragment
        navController = navHostFragment.navController

        return rootView
    }

    private fun setUpUI(rootView:View) {


        rootView.btnLogin.setOnClickListener(this)
        username = rootView.txtUserName
        password = rootView.txtPassword

    }
    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, ViewModelFactory()).get(LoginViewModel::class.java)

    }
    override fun onClick(v: View?) {

        val id = v?.id
        when (id) {

            R.id.btnLogin ->{

                val response: String = viewModel.ValidateLogin(
                    requireActivity() as BaseActivity,
                    username.text.toString().trim(),
                    password.text.toString().trim()
                )

                if (response.equals("")) {

                    showToast(" Login Successfully")

                    Navigation.findNavController(rootView)
                        .navigate(R.id.action_login_to_answerSheetScan)

                    } else{

                    showToast(response)

                    }



            }
        }

    }

}