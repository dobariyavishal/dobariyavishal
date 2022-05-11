package com.app.weatherapp.data
import com.google.gson.annotations.SerializedName


data class WeatherInfoResponse(
    @SerializedName("cod")
    var cod: String? = null,
    @SerializedName("count")
    var count: Int? = null,
    @SerializedName("list")
    var list: List<ListData>? = null,
    @SerializedName("message")
    var message: String? = null
)

data class  ListData(
    @SerializedName("clouds")
    var clouds: Clouds? = null,
    @SerializedName("coord")
    var coord: Coord? = null,
    @SerializedName("dt")
    var dt: Int? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("main")
    var main: Main? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("rain")
    var rain: Any? = null,
    @SerializedName("snow")
    var snow: Any? = null,
    @SerializedName("sys")
    var sys: Sys? = null,
    @SerializedName("weather")
    var weather: List<Weather>? = null,
    @SerializedName("wind")
    var wind: Wind? = null
)

data class Clouds(
    @SerializedName("all")
    var all: Int? = null
)

data class Coord(
    @SerializedName("lat")
    var lat: Double? = null,
    @SerializedName("lon")
    var lon: Double? = null
)

data class Main(
    @SerializedName("feels_like")
    var feelsLike: Double? = null,
    @SerializedName("humidity")
    var humidity: Int? = null,
    @SerializedName("pressure")
    var pressure: Int? = null,
    @SerializedName("temp")
    var temp: Double? = null,
    @SerializedName("temp_max")
    var tempMax: Double? = null,
    @SerializedName("temp_min")
    var tempMin: Double? = null
)

data class Sys(
    @SerializedName("country")
    var country: String? = null
)

data class Weather(
    @SerializedName("description")
    var description: String? = null,
    @SerializedName("icon")
    var icon: String? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("main")
    var main: String? = null
)

data class Wind(
    @SerializedName("deg")
    var deg: Int? = null,
    @SerializedName("speed")
    var speed: Double? = null
)