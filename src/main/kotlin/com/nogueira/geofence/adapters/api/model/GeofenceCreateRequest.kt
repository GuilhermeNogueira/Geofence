package com.nogueira.geofence.adapters.api.model

import com.nogueira.geofence.core.domain.Geofence
import com.nogueira.geofence.core.domain.Point

data class GeofenceCreateRequest(
    val radius: Int,
    val name: String,
    val location: Point
) {
    companion object {
        fun GeofenceCreateRequest.toGeofence() =
            Geofence(null, this.radius, location, this.name)
    }
}
