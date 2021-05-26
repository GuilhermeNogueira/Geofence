package com.nogueira.geofence

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import java.net.http.HttpClient
import java.time.Duration

@SpringBootApplication
class GeofenceApplication {
    @Bean
    fun objectMapper(): ObjectMapper {
        val mapper: ObjectMapper = jacksonObjectMapper()
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        return mapper
    }

    @Bean
    fun httpClient(): HttpClient = HttpClient //we could configure it from configuration file, for now keeping hardcoded
        .newBuilder()
        .connectTimeout(Duration.ofSeconds(2))
        .build()
}

fun main(args: Array<String>) {
    runApplication<GeofenceApplication>(*args)
}
