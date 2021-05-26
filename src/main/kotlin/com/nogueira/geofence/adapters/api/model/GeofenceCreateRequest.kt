package com.nogueira.geofence.adapters.api.model

import com.nogueira.geofence.core.domain.Advertising
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


data class AdvertisingCreateRequest(
    val name: String,
    val href: String
) {

    companion object {
        fun AdvertisingCreateRequest.toAdvertising(geofence: Geofence) =
            Advertising(null, this.name, geofence, this.href)
    }
}