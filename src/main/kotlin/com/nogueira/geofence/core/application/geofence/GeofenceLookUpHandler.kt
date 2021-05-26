package com.nogueira.geofence.core.application.geofence

import com.nogueira.geofence.core.application.geofence.command.GeofenceLookUpCommand
import com.nogueira.geofence.core.application.geofence.query.GeofenceLookUpQuery
import com.nogueira.geofence.core.application.geolocation.GeolocationStrategy
import com.nogueira.geofence.core.application.geolocation.command.GeolocationStrategyCommand
import com.nogueira.geofence.core.application.geolocation.query.GeolocationStrategyQuery
import com.nogueira.geofence.core.application.geolocation.query.GeolocationStrategyQuery.Companion.toGeofenceResponse
import com.nogueira.geofence.core.domain.geofence.Point
import com.nogueira.geofence.core.domain.geofence.Geofence

/**
 *
 */
interface GeofenceLookUpHandler {
    fun process(command: GeofenceLookUpCommand): GeofenceLookUpQuery
}

class GeofenceLookUpHandlerImpl(
    private val strategy: GeolocationStrategy
) : GeofenceLookUpHandler {

    override fun process(command: GeofenceLookUpCommand): GeofenceLookUpQuery {
        val availableGeofences = command.source
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
