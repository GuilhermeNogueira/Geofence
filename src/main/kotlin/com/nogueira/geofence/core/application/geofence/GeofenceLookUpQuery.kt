package com.nogueira.geofence.core.application.geofence

import com.nogueira.geofence.core.domain.Geofence

data class GeofenceResponse(val distance: Double, val geofence: Geofence)

data class GeofenceLookUpQuery(val geofences: Collection<GeofenceResponse>)