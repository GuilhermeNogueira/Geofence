package com.nogueira.geofence.core.application.geofence

import com.nogueira.geofence.adapters.db.GeofenceRepository
import org.springframework.stereotype.Service

@Service
class GeofenceService(
    private val repository: GeofenceRepository
) {
}