package com.nogueira.geofence.adapters.db

import com.nogueira.geofence.core.domain.entity.GeofenceEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GeofenceRepository : JpaRepository<GeofenceEntity, Int> {
}