package com.nogueira.geofence.core.domain

import com.nogueira.geofence.core.domain.Point.Companion.toEntity
import com.nogueira.geofence.core.domain.entity.GeofenceEntity

data class Geofence(
    val id: Long? = null,
    val radius: Int,
    val location: Point,
    val name: String
) {

    companion object {
        fun Geofence.toEntity() = GeofenceEntity(this.id, this.name, this.radius, this.location.toEntity())
    }
}