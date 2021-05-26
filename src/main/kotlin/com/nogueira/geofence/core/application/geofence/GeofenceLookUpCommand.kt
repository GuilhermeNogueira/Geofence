package com.nogueira.geofence.core.application.geofence

import com.nogueira.geofence.core.domain.Point

data class GeofenceLookUpCommand(
    val currentLocation: Point
)
