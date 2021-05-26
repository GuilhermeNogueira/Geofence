package com.nogueira.geofence

class Geofence(
    val id: Int? = null,
    val radius: Int,
    val location: Point,
    val name: String
)

data class Point(val lat: Double, val lng: Double)