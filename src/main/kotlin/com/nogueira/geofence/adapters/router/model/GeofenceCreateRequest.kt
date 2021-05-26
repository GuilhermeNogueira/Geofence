package com.nogueira.geofence.adapters.router.model

import com.nogueira.geofence.core.domain.Geofence
import com.nogueira.geofence.core.domain.Point

data class GeofenceCreateRequest(
    val radius: Int,
    val name: String,
    val location: PointRequest
) {

    companion object {
        fun GeofenceCreateRequest.toGeofence() =
            Geofence(null, this.radius, Point(this.location.lat, this.location.lng), this.name)
    }
}

data class PointRequest(val lat: Double, val lng: Double)