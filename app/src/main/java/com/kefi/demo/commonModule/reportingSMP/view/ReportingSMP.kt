package com.kefi.demo.commonModule.reportingSMP.view


import android.app.Activity
import android.app.Dialog
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.kefi.demo.R
import com.kefi.demo.basecomponet.BaseFragment
import com.kefi.demo.commonModule.reportingSMP.model.ReportingSMPMain
import com.kefi.demo.commonModule.reportingSMP.model.RequestReportingSMP
import com.kefi.demo.commonModule.reportingSMP.viewmodel.ReportingSMPViewModel
import com.kefi.demo.util.PrefManager
import com.kefi.demo.util.Status
import com.kefi.demo.util.Utils
import com.kefi.demo.util.ViewModelFactory
import com.kefi.demo.variabiles.Constants
import com.kefi.myapplication.variabiles.Keys
import kotlinx.android.synthetic.main.frag_reporting_smp.view.*


class ReportingSMP: BaseFragment() {

    lateinit var rootView: View
    lateinit var prefmanager: PrefManager
    lateinit var viewModel: ReportingSMPViewModel


    lateinit var navController: NavController
    lateinit var reportProgress: ProgressBar
    lateinit var prfManager: PrefManager

    lateinit var CollegeName: TextView
    lateinit var CandidateName: TextView
    lateinit var CandidateCode: TextView
    lateinit var YearofAdmission: TextView
    lateinit var CourseName: TextView
    lateinit var Time: TextView
    lateinit var Semester: TextView
    lateinit var CourseCode: TextView
    lateinit var ExamName: TextView




    var dfm: String = ""
    var dfm_Id:String=""
    var smpReason: String = ""
    var candidateId: String = ""

    var isValidQR: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViewModel()
    }


    private fun setupViewModel() {

        viewModel = ViewModelProvider(this, ViewModelFactory()).get(ReportingSMPViewModel::class.java)

    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        rootView = inflater.inflate(R.layout.frag_reporting_smp, null)

        val navHostFragment = requireActivity().supportFragmentManager
                .findFragmentById(R.id.frame) as NavHostFragment
        navController = navHostFragment.navController


        if (arguments != null) {


            dfm = requireArguments().getString("BarcodeValue", "")!!
        }

        setUpUI(rootView)
        setupObserver()
        if (Utils.isNetworkConnected(
                        requireActivity(),
                        Constants.reportingSMP,
                        navController
                )) {
            reportProgress.visibility = View.VISIBLE

            val submitModel = RequestReportingSMP()

            submitModel.dfm = dfm

            viewModel.fetchReportingSMPData(prefmanager.gettoken!!,submitModel)

        }else{
            showToast("No network available now, please try again later")

        }

        return rootView
    }

    private fun setupObserver() {
        viewModel.ReportingData().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    reportProgress.visibility = View.GONE

                    it.data?.let { reportingData ->
                        renderData(reportingData)
                    }
                }
                Status.LOADING -> {
                    reportProgress.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    //Handle Error
                    reportProgress.visibility = View.GONE
                    Keys.requestErrorCode = 10
                    val bundle = Bundle()
                }
            }
        })
    }

    private fun renderData(data: ReportingSMPMain) {

        if (data.success) {

            rootView.cvReportSmp.visibility =View.VISIBLE
            candidateId = data.data!!.candidateId
            dfm_Id=data.data!!.dfmId

            if (!data.data!!.college.isNullOrEmpty()) {
                CollegeName.setText(data.data!!.college)
            }
            if (!data.data!!.candidateName.isNullOrEmpty()) {
                CandidateName.setText(data.data!!.candidateName)
            }
            if (!data.data!!.candidateCode.isNullOrEmpty()) {
                CandidateCode.setText(data.data!!.candidateCode)
            }
          if (!data.data!!.qpcode.isNullOrEmpty()) {
                CourseName.setText(data.data!!.qpcode)
            }

            /*  if (!data.data!!.semester.isNullOrEmpty()) {
                Semester.setText(data.data!!.semester)
            }*/

          /*  if (!data.data!!.time.isNullOrEmpty()) {
                Time.setText(data.data!!.time)
            }
            if (!data.data!!.courseId.isNullOrEmpty()) {
                CourseCode.setText(data.data!!.courseId)
            }
            if (!data.data!!.yearOfAdmission.isNullOrEmpty()) {
                YearofAdmission.setText(data.data!!.yearOfAdmission)
            }
            if (!data.data!!.exam.isNullOrEmpty()) {
                ExamName.setText(data.data!!.exam)
            }*/


         }else{
            showToast(data.message)

            showAcceptPopup(
                    "Invalid QRCode",
                    "ok",requireActivity())
        }

    }



    fun showAcceptPopup(
            message: String, okButton: String,
            activity: Activity,

            ) {

        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.popup_for_all)
        dialog.setCanceledOnTouchOutside(true)
        val txtMessage = dialog.findViewById<View>(R.id.txt_message) as TextView
        txtMessage.text = message

        val txtAccept = dialog.findViewById<View>(R.id.txtAccept) as TextView
        txtAccept.text = okButton


        val ll_ok_btn = dialog.findViewById<View>(R.id.ll_ok_btn) as LinearLayout

        ll_ok_btn.setOnClickListener {

            Navigation.findNavController(rootView)
                    .navigate(R.id.action_reportingSMP_to_answerSheetScan)
            dialog.dismiss()
        }
        dialog.show()

    }

    private fun setUpUI(rootView: View?) {

        prefmanager = PrefManager(requireContext())

        CollegeName = rootView!!.tvCollegeName
        CandidateName = rootView.tvCandidateName
        CandidateCode = rootView.tvCandidateCode
        YearofAdmission = rootView.tvYearofAdmission
        CourseName = rootView.tvCourseName

        Semester = rootView.tvSemester
        CourseCode = rootView.tvCourseCode
        ExamName = rootView.tvExam
        Time = rootView.tvTime



        reportProgress = rootView.reportProgress
        prfManager = PrefManager(requireContext())




    }


}