package com.nogueira.geofence.adapters.db

import com.nogueira.geofence.core.domain.entity.AdvertisingEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AdvertisingRepository : JpaRepository<AdvertisingEntity, Long> {
}