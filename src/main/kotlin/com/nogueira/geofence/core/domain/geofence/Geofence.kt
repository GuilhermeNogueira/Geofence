package com.nogueira.geofence.core.domain.geofence

data class Geofence(
    val id: Int? = null,
    val radius: Int,
    val location: Point,
    val name: String
)