package com.nogueira.geofence

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.*

internal class DefaultFindNearbyStrategyTest {

    @Test
    fun shouldCalculateDistanceProperly() {

        val cmd = FindNearByStrategy.FindNearbyStrategyCommand(
            currentLocation = Point(-22.822611, -47.204467),
            geofence = mockGeofence
        )

        val response = subject.isWithin(cmd)

        //value extracted from google maps
        assertThat(response.distance).isEqualTo(1025.6643875294428)
        assertThat(response.isWithin).isFalse()
        assertThat(response.geofence).isEqualTo(mockGeofence)
    }

    @ParameterizedTest
    @MethodSource("insidePointsProvider")
    fun shouldBeInside(point: Point) {

        val cmd = FindNearByStrategy.FindNearbyStrategyCommand(
            currentLocation = point,
            geofence = mockGeofence
        )
        val response = subject.isWithin(cmd)
        assertThat(response.isWithin).isTrue()
        assertThat(response.distance <= mockGeofence.radius).isTrue()
    }

    @ParameterizedTest
    @MethodSource("outsidePointsProvider")
    fun shouldBeOutside(point: Point) {

        val cmd = FindNearByStrategy.FindNearbyStrategyCommand(
            currentLocation = point,
            geofence = mockGeofence
        )
        val response = subject.isWithin(cmd)
        assertThat(response.isWithin).isFalse()
        assertThat(response.distance > mockGeofence.radius).isTrue()
    }

    private companion object {

        val subject = DefaultFindNearbyStrategy()

        val mockGeofence = Geofence(
            id = null,
            radius = 1000,
            location = Point(-22.814103, -47.200600),
            name = "Test"
        )

        @JvmStatic
        @Suppress("unused")
        fun insidePointsProvider(): Stream<Arguments> = Stream.of(
            //from google maps
            Arguments.of(Point(-22.815423, -47.202913)),
            Arguments.of(Point(-22.819091, -47.203175)),
            Arguments.of(Point(-22.806049, -47.203818)),
            Arguments.of(Point(-22.822148, -47.204248)),
        )

        @JvmStatic
        @Suppress("unused")
        fun outsidePointsProvider(): Stream<Arguments> = Stream.of(
            //from google maps
            Arguments.of(Point(-22.818522, -47.251594)),
            Arguments.of(Point(-22.790103, -47.302503)),
            Arguments.of(Point(-22.684892, -46.963567)),
            Arguments.of(Point(-22.822457, -47.204393)),
        )
    }

}