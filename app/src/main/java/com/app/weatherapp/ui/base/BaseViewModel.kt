package com.app.weatherapp.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.weatherapp.network.BaseDataSource
import com.app.weatherapp.util.ValidationStatus
import javax.inject.Inject

open class BaseViewModel<N> : ViewModel() {
    @Inject
    lateinit var baseDataSource: BaseDataSource
    var navigator: N? = null
    val validationObserver = MutableLiveData<ValidationStatus>()
    fun showMessage(status: ValidationStatus) {
        validationObserver.value = status
    }
}