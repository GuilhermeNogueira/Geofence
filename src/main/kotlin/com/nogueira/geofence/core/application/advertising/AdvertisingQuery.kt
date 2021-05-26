package com.nogueira.geofence.core.application.advertising

import com.nogueira.geofence.core.domain.Geofence

data class AdvertisingResponse(
    val id: Long?,
    val name: String,
    val geofence: Geofence,
    val href: String,
    val distance: Double
)

data class AdvertisingQuery(val ads: Set<AdvertisingResponse>) {

    fun size() = ads.size

    fun isEmpty() = ads.isEmpty()
}