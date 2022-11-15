package com.kefi.demo.commonModule.reportingSMP.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import com.kefi.demo.commonModule.reportingSMP.model.ReportingSMPMain
import com.kefi.demo.commonModule.reportingSMP.model.RequestReportingSMP
import com.kefi.demo.repository.MainRepository
import com.kefi.demo.util.Resource
import com.kefi.demo.util.SingleLiveEvent
import com.kefi.demo.util.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class ReportingSMPViewModel (private val mainRepository: MainRepository) :ViewModel(){

    private val reportingData = SingleLiveEvent<Resource<ReportingSMPMain>>()

    private val compositeDisposable = CompositeDisposable()


    @SuppressLint("CheckResult")
    fun fetchReportingSMPData(token:String,model: RequestReportingSMP) {

        reportingData.postValue(Resource.loading(null))
        compositeDisposable.add(
                mainRepository.requestReportSMP(token, Utils.convertModelToJson(model)).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { userDatas -> reportingData.postValue(Resource.success(userDatas)) },
                                { throwable ->
                                    reportingData.postValue(Resource.error("Something went to wrong", null))

                                }
                        )
        )
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }


    fun ReportingData(): SingleLiveEvent<Resource<ReportingSMPMain>> {
        return reportingData

    }


}