package com.kefi.demo.repository

import com.kefi.demo.apiHelper.RetrofitApiService
import com.kefi.demo.commonModule.reportingSMP.model.ReportingSMPMain
import com.google.gson.JsonObject


import io.reactivex.Observable


class MainRepository() {
    fun requestReportSMP(authorization: String,jsonObject: JsonObject): Observable<ReportingSMPMain> {
        return RetrofitApiService.retrofitApiService?.requestReportSMP(authorization,jsonObject)!!
    }

}