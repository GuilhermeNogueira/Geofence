package com.nogueira.geofence.core.application.geofence

import com.nogueira.geofence.adapters.db.GeofenceRepository
import com.nogueira.geofence.core.domain.Geofence
import com.nogueira.geofence.core.domain.Geofence.Companion.toEntity
import org.springframework.stereotype.Service

@Service
class GeofenceService(
    private val repository: GeofenceRepository
) {

    fun save(geofence: Geofence): Geofence {
        val entity = geofence.toEntity()
        return Geofence.fromEntity(repository.save(entity))
    }

    fun findAll(): Collection<Geofence> {
        return repository.findAll().map { Geofence.fromEntity(it) }
    }

}