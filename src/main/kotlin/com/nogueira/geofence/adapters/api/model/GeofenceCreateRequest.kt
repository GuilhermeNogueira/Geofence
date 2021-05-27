package com.nogueira.geofence.adapters.api.model

import com.nogueira.geofence.core.domain.Advertising
import com.nogueira.geofence.core.domain.Geofence
import com.nogueira.geofence.core.domain.Point
import com.nogueira.geofence.roundTo

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

class PointRequest(lat: Double, lng: Double) {
    //rounding to 7 digits max
    val lat: Double = lat.roundTo(7)
    val lng: Double = lng.roundTo(7)
}


data class AdvertisingCreateRequest(
    val name: String,
    val geofenceId: Long,
    val href: String
) {

    companion object {
        fun AdvertisingCreateRequest.toAdvertising(geofence: Geofence) =
            Advertising(null, this.name, geofence, this.href)
    }
}