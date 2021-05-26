package com.nogueira.geofence.core.domain

data class Geofence(
    val id: Long? = null,
    val radius: Int,
    val location: Point,
    val name: String
)