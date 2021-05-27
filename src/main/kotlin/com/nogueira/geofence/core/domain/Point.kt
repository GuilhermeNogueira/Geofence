package com.nogueira.geofence.core.domain

import com.nogueira.geofence.core.domain.entity.PointEntity
import com.nogueira.geofence.roundTo

class Point(lat: Double, lng: Double) {
    //rounding to 7 digits max
    val lat: Double = lat.roundTo(7)
    val lng: Double = lng.roundTo(7)

    companion object {
        fun Point.toEntity() = PointEntity(null, this.lat, this.lng)
    }
}