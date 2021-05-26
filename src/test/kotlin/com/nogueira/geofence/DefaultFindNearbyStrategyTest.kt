package com.nogueira.geofence

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import org.junit.jupiter.api.Test

internal class DefaultFindNearbyStrategyTest {

    @Test
    fun shouldCalculateDistanceProperly() {
        val subject = DefaultFindNearbyStrategy()


        val cmd = FindNearByStrategy.FindNearbyStrategyCommand(
            currentLocation = Point(-22.822611, -47.204467),
            geofence = mockGeofence
        )

        val response = subject.isWithin(cmd)

        //value extract from google maps
        assertThat(response.distance).isEqualTo(1025.6643875294428)
        assertThat(response.isWithin).isFalse()
        assertThat(response.geofence).isEqualTo(mockGeofence)
    }

    private companion object {
        val mockGeofence = Geofence(
            id = null,
            radius = 1000,
            location = Point(-22.814103, -47.200600),
            name = "Test"
        )
    }

}