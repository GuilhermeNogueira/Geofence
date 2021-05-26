package com.nogueira.geofence.core.application.geofence.command

import com.nogueira.geofence.core.domain.geofence.Geofence
import com.nogueira.geofence.core.domain.geofence.Point

data class GeofenceLookUpCommand(
    val currentLocation: Point,
    val source: Collection<Geofence>
)
