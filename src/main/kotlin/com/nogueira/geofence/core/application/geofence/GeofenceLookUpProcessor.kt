package com.nogueira.geofence.core.application.geofence

import com.nogueira.geofence.core.application.geolocation.GeolocationStrategy
import com.nogueira.geofence.core.application.geolocation.GeolocationStrategyCommand
import com.nogueira.geofence.core.application.geolocation.GeolocationStrategyQuery
import com.nogueira.geofence.core.application.geolocation.GeolocationStrategyQuery.Companion.toGeofenceResponse
import com.nogueira.geofence.core.domain.Geofence
import com.nogueira.geofence.core.domain.Point

/**
 *
 */
interface GeofenceLookUpHandler {
    fun process(command: GeofenceLookUpCommand): GeofenceLookUpQuery
}

class GeofenceLookUpHandlerImpl(
    private val strategy: GeolocationStrategy,
    private val availableGeofences: Collection<Geofence>
) : GeofenceLookUpHandler {

    override fun process(command: GeofenceLookUpCommand): GeofenceLookUpQuery {
        val currentLocation = Point(command.currentLocation.lat, command.currentLocation.lng)

        val nearbyGeofences = process(currentLocation, availableGeofences)

        return GeofenceLookUpQuery(
            geofences = nearbyGeofences.map { it.toGeofenceResponse() }
        )
    }

    private fun process(
        currentLocation: Point,
        geofence: Collection<Geofence>
    ): Collection<GeolocationStrategyQuery> {
        return geofence
            .map { GeolocationStrategyCommand(currentLocation, it) }
            .map { this.strategy.process(it) }
            .filter { it.isWithin } // filtering only geofences within the current location
    }

}
