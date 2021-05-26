package com.nogueira.geofence.adapters.api

import com.nogueira.geofence.adapters.api.model.AdvertisingCreateRequest
import com.nogueira.geofence.adapters.api.model.AdvertisingCreateRequest.Companion.toAdvertising
import com.nogueira.geofence.adapters.api.model.GeofenceCreateRequest
import com.nogueira.geofence.adapters.api.model.GeofenceCreateRequest.Companion.toGeofence
import com.nogueira.geofence.core.application.advertising.AdvertisingService
import com.nogueira.geofence.core.application.geofence.GeofenceService
import com.nogueira.geofence.core.domain.Advertising
import com.nogueira.geofence.core.domain.Geofence
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/geofence")
class GeofenceController(
    private val service: GeofenceService,
    private val advertisingService: AdvertisingService
) {

    @PostMapping
    fun create(@RequestBody req: GeofenceCreateRequest): ResponseEntity<Geofence> {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(req.toGeofence()))
    }

    @PostMapping("/{geofence_id}/advertising")
    fun createAd(
        @RequestBody req: AdvertisingCreateRequest,
        @PathVariable("geofence_id") id: Long
    ): ResponseEntity<Advertising> {

        val geofence = service.findById(id)

        geofence?.let {
            val advertising = req.toAdvertising(it)
            return ResponseEntity.status(HttpStatus.CREATED).body(advertisingService.save(advertising))
        }

        return ResponseEntity.notFound().build()
    }
}