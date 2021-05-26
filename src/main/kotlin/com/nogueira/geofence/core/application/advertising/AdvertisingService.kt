package com.nogueira.geofence.core.application.advertising

import com.nogueira.geofence.adapters.api.error.InvalidAdvertisingUrlException
import com.nogueira.geofence.adapters.db.AdvertisingRepository
import com.nogueira.geofence.core.domain.Advertising
import com.nogueira.geofence.core.domain.Advertising.Companion.toEntity
import com.nogueira.geofence.core.domain.entity.AdvertisingEntity.Companion.toAdvertising
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
}