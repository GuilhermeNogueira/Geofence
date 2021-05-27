package com.nogueira.geofence.core.application.advertising

import com.nogueira.geofence.adapters.api.error.InvalidAdvertisingUrlException
import com.nogueira.geofence.adapters.db.AdvertisingRepository
import com.nogueira.geofence.core.application.geofence.GeofenceLookUpProcessor
import com.nogueira.geofence.core.application.geofence.GeofenceLookUpQuery
import com.nogueira.geofence.core.domain.Advertising
import com.nogueira.geofence.core.domain.Advertising.Companion.toEntity
import com.nogueira.geofence.core.domain.entity.AdvertisingEntity
import com.nogueira.geofence.core.domain.entity.AdvertisingEntity.Companion.toAdvertising
import com.nogueira.geofence.core.domain.entity.GeofenceEntity.Companion.toGeofence
import org.springframework.stereotype.Service

@Service
class AdvertisingService(
    val repository: AdvertisingRepository,
    val validator: AdvertisingValidator
) {

    fun save(advertising: Advertising): Advertising {
        val valid = validator.isValid(advertising)

        if (!valid) {
            throw InvalidAdvertisingUrlException()
        }

        return repository.save(advertising.toEntity()).toAdvertising()
    }

    fun findById(id: Long): Advertising? = repository.findById(id).map { it.toAdvertising() }.orElse(null)

    /**
     * Query [Advertisements][Advertising] within given [geofences][GeofenceLookUpQuery].
     *
     * @see [GeofenceLookUpProcessor.process]
     *
     * @return Advertisements within geofence otherwise empty
     */
    fun query(processor: GeofenceLookUpProcessor): AdvertisingQuery {

        val queryResult = processor.process()

        return AdvertisingQuery(
            when {
                queryResult.isEmpty() -> {
                    emptySet()
                }
                else -> {
                    val advertisements = repository.findAdvertisingEntitiesByLocationIdIn(queryResult.getIds())
                    process(queryResult, advertisements)
                }
            }
        )
    }

    private fun process(
        geofenceLookUpQuery: GeofenceLookUpQuery,
        advertisements: Set<AdvertisingEntity>
    ): Set<AdvertisingResponse> {

        return advertisements
            .map {
                val distance = geofenceLookUpQuery.getDistance(it.location.id!!)!!
                AdvertisingResponse(it.id, it.name, it.location.toGeofence(), it.href, distance)
            }.toSet()
    }

}
