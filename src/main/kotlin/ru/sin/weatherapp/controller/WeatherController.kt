package ru.sin.weatherapp.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.sin.weatherapp.service.WeatherService
import java.time.Duration
import java.time.Instant

@RestController
@RequestMapping("/api/v1")
class WeatherController(private val weatherService: WeatherService) {

    @GetMapping("current")
    suspend fun getCurrent(@RequestParam("city") city: String): WeatherResponse = weatherService.getCurrent(city)

    @GetMapping("forecast")
    suspend fun getForecast(
        @RequestParam("city") city: String,
        @RequestParam("dt") timestamp: Long
    ): WeatherResponse = weatherService.getForecast(city, validateDate(timestamp))

}

private fun validateDate(timestamp: Long): Long {
    val days = Duration.between(Instant.ofEpochSecond(timestamp), Instant.now()).toDays()
    if (days >= 10) {
        throw IllegalArgumentException("Invalid timestamp, timestamp should be in period of next 10 days")
    }
    return timestamp
}