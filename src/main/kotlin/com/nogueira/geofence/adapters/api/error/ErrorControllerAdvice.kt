package com.nogueira.geofence.adapters.api.error

import mu.KotlinLogging
import org.slf4j.Logger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.server.ServerWebExchange

@ControllerAdvice
class ErrorControllerAdvice {

    @ExceptionHandler(Exception::class)
    fun handle(ex: Exception): ResponseEntity<String> {

        LOGGER.error("unknown expcetion", ex)
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("ops")
    }

    @ExceptionHandler(InvalidAdvertisingUrlException::class)
    fun handle(ex: InvalidAdvertisingUrlException): ResponseEntity<Any> {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(mapOf("error" to "invalid_url"))
    }

    private companion object {
        val LOGGER: Logger = KotlinLogging.logger {}
    }
}