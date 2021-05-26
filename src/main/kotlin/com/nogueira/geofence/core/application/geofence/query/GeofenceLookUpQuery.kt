package com.nogueira.geofence.core.application.geofence.query

import com.nogueira.geofence.core.domain.geofence.Geofence

data class GeofenceResponse(val distance: Double, val geofence: Geofence)

data class GeofenceLookUpQuery(val geofences: Collection<GeofenceResponse>)