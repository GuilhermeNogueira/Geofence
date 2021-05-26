package com.nogueira.geofence

import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

data class NearbyGeofenceCommand(
    val currentLat: Double,
    val currentLng: Double,
    val source: Collection<Geofence>
) {

}

data class GeofenceResponse(val distance: Double, val geofence: Geofence)

data class GeofenceLookUpResponse(val geofences: Collection<GeofenceResponse>)

interface GeofenceLookUpHandler {
    fun findNearby(command: NearbyGeofenceCommand): GeofenceLookUpResponse
}

interface FindNearByStrategy {
    fun isWithin(command: FindNearbyStrategyCommand): NearbyStrategyDistanceResponse

    data class FindNearbyStrategyCommand(val currentLocation: Point, val geofence: Geofence)
}

data class NearbyStrategyDistanceResponse(val isWithin: Boolean, val distance: Double, val geofence: Geofence)

class DefaultFindNearbyStrategy : FindNearByStrategy {

    override fun isWithin(command: FindNearByStrategy.FindNearbyStrategyCommand): NearbyStrategyDistanceResponse {

        val currentLat = command.currentLocation.lat
        val currentLng = command.currentLocation.lng

        val targetLat = command.geofence.location.lat
        val targetLng = command.geofence.location.lng

        val radius = command.geofence.radius

        val distance = calculateDistance(currentLat, currentLng, targetLat, targetLng)
        //radius is in metter
        val distanceInMeter = distance * 1000

        val isWithin = distanceInMeter <= radius

        return NearbyStrategyDistanceResponse(
            isWithin = isWithin,
            distance = distanceInMeter,
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

//class GeofenceLookUpHandlerImpl : GeofenceLookUpHandler {
//
//
//    override fun findNearby(command: NearbyGeofenceCommand): GeofenceLookUpResponse {
//        val availableGeofences = command.source
//        val currentLocation = Point(command.currentLat, command.currentLng)
//
//    }
//
//}