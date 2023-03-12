package ru.sin.weatherapp.model

import com.fasterxml.jackson.annotation.JsonProperty

data class WeatherCurrentExtApiResponse(val current: Current)
data class Current(@JsonProperty("temp_c") val temperature: Double)
