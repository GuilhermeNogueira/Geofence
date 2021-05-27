package com.nogueira.geofence.core.application.geofence

import com.nogueira.geofence.core.application.geolocation.DefaultGeolocationStrategy
import com.nogueira.geofence.core.application.geolocation.GeolocationStrategy
import com.nogueira.geofence.core.application.geolocation.GeolocationStrategyCommand
import com.nogueira.geofence.core.application.geolocation.GeolocationStrategyQuery
import com.nogueira.geofence.core.application.geolocation.GeolocationStrategyQuery.Companion.toGeofenceResponse
import com.nogueira.geofence.core.domain.Geofence
import com.nogueira.geofence.core.domain.Point

/**
 *
 */
interface GeofenceLookUpProcessor {

    /**
     * Process given [GeofenceLookUpCommand] and returns a collection of nearby [Geofence] encapsulated in [GeofenceLookUpQuery]
     */
    fun process(): GeofenceLookUpQuery

    class SimpleGeofenceLookUpProcessor(
        private val strategy: GeolocationStrategy,
        private val currentLocation: Point,
        private val availableGeofences: Collection<Geofence>
    ) : GeofenceLookUpProcessor {

        override fun process(): GeofenceLookUpQuery {

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

    // factory
    companion object factory {

        /**
         * Creates a [GeofenceLookUpProcessor] using a default [GeolocationStrategy] ([DefaultGeolocationStrategy])
         */
        fun createDefault(currentLocation: Point, availableGeofences: Collection<Geofence>): GeofenceLookUpProcessor {
            return SimpleGeofenceLookUpProcessor(DefaultGeolocationStrategy(), currentLocation, availableGeofences)
        }

        /**
         * Creates a [GeofenceLookUpProcessor] using given [GeolocationStrategy]
         */
        fun create(
            strategy: GeolocationStrategy,
            currentLocation: Point,
            availableGeofences: Collection<Geofence>
        ): GeofenceLookUpProcessor {
            return SimpleGeofenceLookUpProcessor(strategy, currentLocation, availableGeofences)
        }
    }

}


