package com.nogueira.geofence.core.domain

import com.nogueira.geofence.core.domain.entity.PointEntity

data class Point(val lat: Double, val lng: Double) {

    companion object {
        fun Point.toEntity() = PointEntity(null, this.lat, this.lng)
        fun fromEntity(entity: PointEntity) = Point(entity.lat, entity.lng)
    }
}