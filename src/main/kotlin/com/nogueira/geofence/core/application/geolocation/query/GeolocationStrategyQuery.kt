package com.nogueira.geofence.core.application.geolocation.query

import com.nogueira.geofence.core.application.geofence.query.GeofenceResponse
import com.nogueira.geofence.core.domain.geofence.Geofence

data class GeolocationStrategyQuery(val isWithin: Boolean, val distance: Double, val geofence: Geofence) {
    companion object {
        fun GeolocationStrategyQuery.toGeofenceResponse() = GeofenceResponse(this.distance, this.geofence)
    }
}