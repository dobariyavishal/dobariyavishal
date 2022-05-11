package com.app.weatherapp.network

import com.app.weatherapp.data.WeatherInfoResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET(APIConfig.GET_WEATHER_DATA)
    suspend fun getWeatherInfo(
        @Query( "q") cityName: String,
        @Query( "lang") lang: String,
        @Query( "mode") mode: String,
        @Query( "appid") appid: String,
    ): Response<WeatherInfoResponse>

}