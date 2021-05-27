package com.nogueira.geofence.core.application.geofence

import com.nogueira.geofence.adapters.db.GeofenceRepository
import com.nogueira.geofence.core.domain.Geofence
import com.nogueira.geofence.core.domain.Geofence.Companion.toEntity
import com.nogueira.geofence.core.domain.entity.GeofenceEntity.Companion.toGeofence
import org.springframework.stereotype.Service

@Service
class GeofenceService(
    private val repository: GeofenceRepository
) {

    fun save(geofence: Geofence): Geofence {
        val entity = geofence.toEntity()
        return repository.save(entity).toGeofence()
    }

    fun findAll(): Collection<Geofence> {
        return repository.findAll().map { it.toGeofence() }
    }

    fun findById(id: Long): Geofence? = repository.findById(id).map { it.toGeofence() }.orElse(null)
}

