package com.kefi.demo.commonModule.Scanner

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.kefi.demo.R
import com.kefi.demo.basecomponet.BaseFragment
import com.kefi.demo.basecomponet.ScanningActivity

import com.kefi.myapplication.variabiles.Keys.Companion.requestScannerID
import com.kefi.demo.util.PrefManager
import kotlinx.android.synthetic.main.frag_answersheet_scan.view.*

class AnswerSheetScan: BaseFragment(),View.OnClickListener {
    lateinit var rootView: View
    private lateinit var navController: NavController
    private lateinit var getBarcode: ActivityResultLauncher<Intent>
    lateinit var prfManager: PrefManager

    var doubleBackToExitPressedOnce: Boolean = false
    var isValidQR: Boolean = false

    var qrCodeOne: String = ""
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        rootView = inflater.inflate(R.layout.frag_answersheet_scan, null)

        setUpUI(rootView)

        val callback = object : OnBackPressedCallback(
                true // default to enabled
        ) {

            override fun handleOnBackPressed() {
                if (doubleBackToExitPressedOnce) {
                    activity!!.finish()
                } else {
                    Toast.makeText(activity, getString(R.string.PressToExit), Toast.LENGTH_SHORT)
                            .show()
                    doubleBackToExitPressedOnce = true
                    Handler(Looper.getMainLooper()).postDelayed({
                        doubleBackToExitPressedOnce = false
                    }, 2000)
                }

            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
                viewLifecycleOwner, // LifecycleOwner
                callback
        )
        return rootView
    }

    override fun onClick(v: View?) {

        when (v!!.id) {
            R.id.imgQR -> {
                requestScannerID = 8
                val intent = Intent(activity, ScanningActivity::class.java)
                startActivityForResult(intent, 2)
            }

        }
    }

    private fun setUpUI(rootView: View) {
        rootView.imgQR.setOnClickListener(this)

    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {

            1 -> {
                val message = data?.getStringExtra("Error").toString()

                Toast.makeText(requireActivity(),message, Toast.LENGTH_LONG).show()
            }
            2 -> {
                val message = data?.getStringExtra("BarcodeValue").toString()
                if (requestScannerID == 8) {
                    qrCodeOne = message
                    if (qrCodeOne != "") {

                       // showToast(qrCodeOne)
                        var qrCodeOne=qrCodeOne
                        var bundle=Bundle()
                        bundle.putString("BarcodeValue",qrCodeOne)
                        Navigation.findNavController(rootView)
                                .navigate(R.id.action_answerSheetScan_to_reportingSMP,bundle)


                    }
                }
            }
        }
    }
}