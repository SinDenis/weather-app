package ru.sin.weatherapp.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import ru.sin.weatherapp.controller.WeatherResponse
import ru.sin.weatherapp.model.WeatherCurrentExtApiResponse
import ru.sin.weatherapp.model.WeatherForecastExtApiResponse
import java.lang.RuntimeException
import java.time.Instant
import java.time.temporal.ChronoUnit

@Service
class WeatherService(
    @Value("\${weather.apiKey}") private val apiKey: String,
    private val webClient: WebClient
) {

    suspend fun getCurrent(city: String): WeatherResponse {
        val response = webClient.get()
            .uri {
                it.path("/v1/current.json")
                    .queryParam("q", city)
                    .queryParam("api", "no")
                    .queryParam("key", apiKey)
                    .build()
            }
            .retrieve()
            .awaitBody<WeatherCurrentExtApiResponse>()
        return WeatherResponse(city, "celsius", response.current.temperature)
    }

    suspend fun getForecast(city: String, date: Long): WeatherResponse {
        val roundDateHour = Instant.ofEpochSecond(date).truncatedTo(ChronoUnit.HOURS).epochSecond
        val response = webClient.get()
            .uri {
                it.path("/v1/forecast.json")
                    .queryParam("q", city)
                    .queryParam("days", 10)
                    .queryParam("api", "no")
                    .queryParam("key", apiKey)
                    .build()
            }
            .retrieve()
            .awaitBody<WeatherForecastExtApiResponse>()
        val forecast = response.forecast.forecastDays
            .flatMap { it.forecastHours }
            .find { it.time == roundDateHour }
            ?.temperature
            ?: throw RuntimeException("Error while getting forecast")

        return WeatherResponse(city, "celsius", forecast)
    }

}