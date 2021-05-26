package com.nogueira.geofence.core.domain.geofence

data class Advertising(
    val id: Int,
    val name: String,
    val geofence: Geofence,
    val href: String
)