package com.nogueira.geofence.core.application.geofence

import com.nogueira.geofence.core.domain.Geofence
import com.nogueira.geofence.core.domain.Point

data class GeofenceLookUpCommand(
    val currentLocation: Point,
    val source: Collection<Geofence>
)
