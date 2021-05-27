package com.nogueira.geofence.adapters.api.model

import com.nogueira.geofence.core.domain.Advertising
import com.nogueira.geofence.core.domain.Geofence

data class AdvertisingCreateRequest(
    val name: String,
    val geofenceId: Long,
    val href: String
) {

    companion object {
        fun AdvertisingCreateRequest.toAdvertising(geofence: Geofence) =
            Advertising(null, this.name, geofence, this.href)
    }
}