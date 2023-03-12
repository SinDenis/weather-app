package ru.sin.weatherapp.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig {
    @Bean
    fun webClient(@Value("\${weather.url}") weatherUrl: String) = WebClient.create(weatherUrl)
}