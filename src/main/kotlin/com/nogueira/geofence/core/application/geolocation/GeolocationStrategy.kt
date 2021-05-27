package com.nogueira.geofence.core.application.geolocation

import com.nogueira.geofence.roundTo
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

/**
 *
 */
interface GeolocationStrategy {
    fun process(command: GeolocationStrategyCommand): GeolocationStrategyQuery
}

class DefaultGeolocationStrategy : GeolocationStrategy {

    override fun process(command: GeolocationStrategyCommand): GeolocationStrategyQuery {

        val currentLat = command.currentLocation.lat
        val currentLng = command.currentLocation.lng

        val targetLat = command.geofence.location.lat
        val targetLng = command.geofence.location.lng

        val radius = command.geofence.radius

        val distance = calculateDistance(currentLat, currentLng, targetLat, targetLng)
        //radius is in metter
        val distanceInMeters = distance * 1000

        val isWithin = distanceInMeters <= radius

        return GeolocationStrategyQuery(
            isWithin = isWithin,
            distance = distanceInMeters.roundTo(7),
            geofence = command.geofence
        )
    }

    /**
     * Calculates distance between two locations in KM
     */
    private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val theta = lon1 - lon2
        var dist = (sin(deg2rad(lat1)) * sin(deg2rad(lat2))
                + cos(deg2rad(lat1)) * cos(deg2rad(lat2)) * cos(deg2rad(theta)))
        dist = acos(dist)
        dist = rad2deg(dist)
        dist *= 60 * 1.1515

        dist *= 1.609344
        return dist
    }

    private fun deg2rad(deg: Double): Double {
        return deg * Math.PI / 180.0
    }

    private fun rad2deg(rad: Double): Double {
        return rad * 180.0 / Math.PI
    }
}
