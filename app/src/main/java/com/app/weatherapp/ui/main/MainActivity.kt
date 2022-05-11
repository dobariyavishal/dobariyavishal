package com.app.weatherapp.ui.main

import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.weatherapp.BR
import com.app.weatherapp.R
import com.app.weatherapp.data.ListData
import com.app.weatherapp.databinding.ActivityMainBinding
import com.app.weatherapp.network.Resource
import com.app.weatherapp.ui.base.BaseActivity
import com.app.weatherapp.ui.main.adapter.WeatherHistoryAdapter
import com.app.weatherapp.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), MainNavigator {
    var cityName = "surat"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWeatherData()
        initListener()
    }

    private fun getWeatherData() {
        mViewModel.getWeatherInfo(cityName)
    }

    private fun initListener() {
        binding.apply {
            ivSearch.setOnClickListener {
                searchView.visible()
            }
            ivRefresh.setOnClickListener {
                getWeatherData()
            }
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    cityName = query ?: ""
                    if (query.isNullOrEmpty()) {
                        ValidationStatus.EMPTY_CITY_NAME
                    } else {
                        searchView.hide()
                        searchView.setQuery("", false)
                        searchView.clearFocus()
                        getWeatherData()
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
    }

    override val layoutId: Int
        get() = R.layout.activity_main
    override val bindingVariable: Int
        get() = BR.viewModel

    override fun setObserver() {
        mViewModel.navigator = this
        mViewModel.validationObserver.observe(this) {
            Validation.showMessageDialog(this, it)
        }
        mViewModel.weatherObserver.observe(this) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    LoadingDialog.showLoadDialog(this)
                }
                Resource.Status.SUCCESS -> {
                    LoadingDialog.hideLoadDialog()
                    it.data?.let {
                        binding.apply {
                            it.list?.let { listData ->
                                if (listData.isEmpty()) {

                                } else {
                                    val weatherData = listData[0]
                                    data = weatherData
                                    // set weather image
                                    weatherData.weather?.let { weather ->
                                        ivWeather.loadImage(
                                            this@MainActivity,
                                            (weather[0].icon ?: "").getImagePath()
                                        )
                                    }
                                    // set weather temperature
                                    weatherData.main?.let {
                                        it.temp?.let {
                                            tvTemperature.text = it.temperature()
                                        }
                                    }
                                    // set weather history
                                    setAdapter(listData.subList(1, listData.size))
                                }
                            }
                        }
                    }
                }
                Resource.Status.ERROR -> {
                    LoadingDialog.hideLoadDialog()
                    showMessage(it.message ?: "")
                }
            }
        }
    }

    private fun setAdapter(list: List<ListData>) {
        val weatherHistoryAdapter = WeatherHistoryAdapter(this, list)
        binding.rvHistory.apply {
            adapter = weatherHistoryAdapter
            addItemDecoration(
                DividerItemDecoration(
                    baseContext,
                    LinearLayoutManager.VERTICAL
                )
            )
        }
    }
}