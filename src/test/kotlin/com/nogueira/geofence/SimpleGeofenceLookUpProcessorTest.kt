package com.nogueira.geofence

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import com.nogueira.geofence.core.application.geofence.GeofenceLookUpCommand
import com.nogueira.geofence.core.application.geofence.GeofenceLookUpProcessor
import com.nogueira.geofence.core.application.geolocation.GeolocationStrategy
import com.nogueira.geofence.core.application.geolocation.GeolocationStrategyCommand
import com.nogueira.geofence.core.application.geolocation.GeolocationStrategyQuery
import com.nogueira.geofence.core.domain.Geofence
import com.nogueira.geofence.core.domain.Point
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock

internal class SimpleGeofenceLookUpProcessorTest {

    @Test
    fun shouldFilterNearbyGeofences() {

        val mockStrategy = mock<GeolocationStrategy>() {
            on {
                process(any())
            } doReturn notWithinResponse
        }

        val subject = GeofenceLookUpProcessor.create(mockStrategy, listOf(mockGeofence, mockGeofence2))

        val cmd = GeofenceLookUpCommand(withinPoint)

        val response = subject.process(cmd)

        assertThat(response.isEmpty()).isTrue()
    }

    @Test
    fun shouldFilterNearbyWhenMatch() {

        val mockStrategy = mock<GeolocationStrategy>() {
            on {
                process(eq(withinCmd))
            } doReturn withinResponse
        }

        val subject = GeofenceLookUpProcessor.create(mockStrategy, listOf(mockGeofence))

        val cmd = GeofenceLookUpCommand(withinPoint)

        val response = subject.process(cmd)

        assertThat(response.size()).isEqualTo(1)
    }

    private companion object {

        val withinPoint = Point(-22.814103, -47.200600)

        val mockGeofence = Geofence(
            id = null,
            radius = 1000,
            location = Point(-22.814103, -47.200600),
            name = "Test"
        )

        val mockGeofence2 = Geofence(
            id = null,
            radius = 1000,
            location = Point(-22.814103, -47.200600),
            name = "Test"
        )

        val withinCmd = GeolocationStrategyCommand(withinPoint, mockGeofence)

        val notWithinResponse = GeolocationStrategyQuery(
            isWithin = false,
            distance = mockGeofence.radius + 1.1,
            geofence = mockGeofence
        )

        val withinResponse = GeolocationStrategyQuery(
            isWithin = true,
            distance = mockGeofence.radius - 1.1,
            geofence = mockGeofence
        )
    }
}