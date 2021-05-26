package com.nogueira.geofence.core.application.geofence

import com.nogueira.geofence.core.domain.Geofence

data class GeofenceResponse(val distance: Double, val geofence: Geofence)

data class GeofenceLookUpQuery(private val geofences: Collection<GeofenceResponse>) {

    private val geofenceMap: Map<Long, Double> = geofences.associateBy({ it.geofence.id!! }, { it.distance })

    fun getIds() = geofenceMap.keys

    fun getDistance(id: Long) = geofenceMap[id]

    fun isEmpty() = geofences.isEmpty()

    fun size() = geofences.size
}