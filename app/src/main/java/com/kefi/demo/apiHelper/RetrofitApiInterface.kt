package com.kefi.demo.apiHelper



import com.kefi.demo.commonModule.reportingSMP.model.ReportingSMPMain
import com.google.gson.JsonObject


import io.reactivex.Observable
import retrofit2.http.*

interface RetrofitApiInterface {


    @POST("smp-fetch-students?format=json")
    fun  requestReportSMP(@Header("Authorization")authorization:String,@Body jsonObject: JsonObject ):Observable<ReportingSMPMain>



}
