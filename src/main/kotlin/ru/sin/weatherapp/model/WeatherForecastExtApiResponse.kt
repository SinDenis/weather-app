package ru.sin.weatherapp.model

import com.fasterxml.jackson.annotation.JsonProperty

data class WeatherForecastExtApiResponse(val forecast: Forecast)

data class Forecast(@JsonProperty("forecastday") val forecastDays: List<ForecastDay>)

data class ForecastDay(@JsonProperty("hour") val forecastHours: List<ForeCastHour>)

data class ForeCastHour(@JsonProperty("time_epoch") val time: Long, @JsonProperty("temp_c") val temperature: Double)