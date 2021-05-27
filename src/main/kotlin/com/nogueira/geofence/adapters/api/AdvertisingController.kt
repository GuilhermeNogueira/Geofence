package com.nogueira.geofence.adapters.api

import com.nogueira.geofence.adapters.api.model.AdvertisingCreateRequest
import com.nogueira.geofence.adapters.api.model.AdvertisingCreateRequest.Companion.toAdvertising
import com.nogueira.geofence.core.application.advertising.AdvertisingQuery
import com.nogueira.geofence.core.application.advertising.AdvertisingService
import com.nogueira.geofence.core.application.geofence.GeofenceLookUpProcessor
import com.nogueira.geofence.core.application.geofence.GeofenceService
import com.nogueira.geofence.core.domain.Advertising
import com.nogueira.geofence.core.domain.Point
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/advertising")
class AdvertisingController(
    private val geofenceService: GeofenceService,
    private val service: AdvertisingService
) {

    @PostMapping
    fun create(
        @RequestBody req: AdvertisingCreateRequest
    ): ResponseEntity<Advertising> {

        return when (val geofence = geofenceService.findById(req.geofenceId)) {
            null -> ResponseEntity.notFound().build()
            else -> {
                val advertising = req.toAdvertising(geofence)
                return ResponseEntity.status(HttpStatus.CREATED).body(service.save(advertising))
            }
        }
    }

    @GetMapping("/{id}")
    fun get(@PathVariable("id") id: Long): ResponseEntity<Advertising> {
        return when (val ad = service.findById(id)) {
            null -> ResponseEntity.notFound().build()
            else -> ResponseEntity.ok(ad)
        }
    }

    @GetMapping("/query")
    fun query(
        @RequestParam("lat") lat: Double,
        @RequestParam("lng") lng: Double
    ): ResponseEntity<AdvertisingQuery> {

        val geofences = geofenceService.findAll()

        val processor = GeofenceLookUpProcessor.createDefault(Point(lat, lng), geofences)

        return ResponseEntity.ok(service.query(processor))
    }
}