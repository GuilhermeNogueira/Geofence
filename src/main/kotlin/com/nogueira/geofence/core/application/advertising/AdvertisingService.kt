package com.nogueira.geofence.core.application.advertising

import com.nogueira.geofence.adapters.api.error.InvalidAdvertisingUrlException
import com.nogueira.geofence.adapters.db.AdvertisingRepository
import com.nogueira.geofence.core.application.geofence.GeofenceLookUpProcessor
import com.nogueira.geofence.core.application.geofence.GeofenceLookUpQuery
import com.nogueira.geofence.core.application.geofence.GeofenceService
import com.nogueira.geofence.core.domain.Advertising
import com.nogueira.geofence.core.domain.Advertising.Companion.toEntity
import com.nogueira.geofence.core.domain.Point
import com.nogueira.geofence.core.domain.entity.AdvertisingEntity
import com.nogueira.geofence.core.domain.entity.AdvertisingEntity.Companion.toAdvertising
import com.nogueira.geofence.core.domain.entity.GeofenceEntity.Companion.toGeofence
import org.springframework.stereotype.Service

/**
 *
 */
@Service
class AdvertisingService(
    val repository: AdvertisingRepository,
    val validator: AdvertisingValidator,
    val geofenceService: GeofenceService
) {

    fun save(advertising: Advertising): Advertising {
        val valid = validator.isValid(advertising)

        if (!valid) {
            throw InvalidAdvertisingUrlException()
        }

        return repository.save(advertising.toEntity()).toAdvertising()
    }

    fun findById(id: Long): Advertising? = repository.findById(id).map { it.toAdvertising() }.orElse(null)

    fun query(currentLocation: Point): AdvertisingQuery {

        val availableGeofences = geofenceService.findAll()

        val queryResult =
            geofenceService.query(currentLocation, GeofenceLookUpProcessor.createDefault(availableGeofences))

        return AdvertisingQuery(
            when {
                queryResult.isEmpty() -> {
                    emptySet()
                }
                else -> {
                    val ads = repository.findAdvertisingEntitiesByLocationIdIn(queryResult.getIds())
                    process(queryResult, ads)
                }
            }
        )
    }

    private fun process(geofenceLookUpQuery: GeofenceLookUpQuery, ads: Set<AdvertisingEntity>) = ads.map {
        val distance = geofenceLookUpQuery.getDistance(it.location.id!!)!!
        AdvertisingResponse(it.id, it.name, it.location.toGeofence(), it.href, distance)
    }.toSet()

}
