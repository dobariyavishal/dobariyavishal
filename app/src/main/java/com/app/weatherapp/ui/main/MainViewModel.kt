package com.app.weatherapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.weatherapp.data.WeatherInfoResponse
import com.app.weatherapp.network.ApiService
import com.app.weatherapp.network.NetworkConnect
import com.app.weatherapp.network.Resource
import com.app.weatherapp.ui.base.BaseViewModel
import com.app.weatherapp.util.LoadingDialog
import com.app.weatherapp.util.ValidationStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val apiService: ApiService,
    private val networkConnect: NetworkConnect,
) : BaseViewModel<MainNavigator>() {
    private val weatherLiveData: MutableLiveData<Resource<WeatherInfoResponse>> =
        MutableLiveData()
    val weatherObserver: LiveData<Resource<WeatherInfoResponse>> get() = weatherLiveData


    fun getWeatherInfo(cityName: String) {
        if (networkConnect.isConnectedToNetwork()) {
            var response: Response<WeatherInfoResponse>?
            viewModelScope.launch {
                weatherLiveData.value = Resource.loading(null)
                try {
                    withContext(Dispatchers.IO) {
                        response = apiService.getWeatherInfo(
                            cityName,
                            "en",
                            "json",
                            "a8a37db71ea612cdd8c0e13c23416a7a"
                        )
                    }
                    withContext(Dispatchers.Main) {
                        response?.run {
                            weatherLiveData.value = baseDataSource.getResult { this }
                        }
                    }
                } catch (e: IOException) {
                    LoadingDialog.hideLoadDialog()
                    showMessage(ValidationStatus.INTERNET_CONNECTION)
                }
            }
        } else {
            showMessage(ValidationStatus.INTERNET_CONNECTION)
        }
    }

}