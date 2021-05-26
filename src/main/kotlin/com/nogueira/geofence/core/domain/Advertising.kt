package com.nogueira.geofence.core.domain

data class Advertising(
    val id: Int,
    val name: String,
    val geofence: Geofence,
    val href: String
)