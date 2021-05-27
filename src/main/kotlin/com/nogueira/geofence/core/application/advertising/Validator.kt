package com.nogueira.geofence.core.application.advertising

import com.nogueira.geofence.core.domain.Advertising
import mu.KotlinLogging
import org.slf4j.Logger
import org.springframework.stereotype.Component
import java.net.ConnectException
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.function.Predicate

interface AdvertisingValidator {
    fun isValid(advertising: Advertising): Boolean
}

@Component
class DefaultAdvertisingValidator(
    private val client: HttpClient
) : AdvertisingValidator {

    private val validStatusPredicate = Predicate<Int> {
        return@Predicate (it in 200..299)
    }

    /**
     * Makes a request to given url and checks if the results is valid (200 OK)
     */
    override fun isValid(advertising: Advertising): Boolean {

        val url = advertising.href

        return try {
            val uri = URI.create(url)

            val request = HttpRequest.newBuilder()
                .uri(uri)
                .build()

            val response = client.send(request, HttpResponse.BodyHandlers.ofString());

            validStatusPredicate.test(response.statusCode())

        } catch (ex: IllegalArgumentException) {
            LOGGER.error("invalid url format", ex)
            false
        } catch (ex: ConnectException) {
            LOGGER.error("invalid url, not reached", ex)
            false
        }
    }

    private companion object {
        val LOGGER: Logger = KotlinLogging.logger {}
    }

}