package com.nogueira.geofence.core.application.geolocation

import com.nogueira.geofence.core.application.geofence.GeofenceResponse
import com.nogueira.geofence.core.domain.Geofence

data class GeolocationStrategyQuery(val isWithin: Boolean, val distance: Double, val geofence: Geofence) {
    companion object {
        fun GeolocationStrategyQuery.toGeofenceResponse() = GeofenceResponse(this.distance, this.geofence)
    }
}