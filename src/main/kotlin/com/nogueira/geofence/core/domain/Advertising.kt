package com.nogueira.geofence.core.domain

import com.nogueira.geofence.core.domain.Geofence.Companion.toEntity
import com.nogueira.geofence.core.domain.entity.AdvertisingEntity

data class Advertising(
    val id: Long?,
    val name: String,
    val geofence: Geofence,
    val href: String
) {
    companion object {
        fun Advertising.toEntity() = AdvertisingEntity(this.id, this.name, this.href, this.geofence.toEntity())
    }
}