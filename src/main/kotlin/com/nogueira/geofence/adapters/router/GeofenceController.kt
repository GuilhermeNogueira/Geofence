package com.nogueira.geofence.adapters.router

import com.nogueira.geofence.adapters.router.model.GeofenceCreateRequest
import com.nogueira.geofence.adapters.router.model.GeofenceCreateRequest.Companion.toGeofence
import com.nogueira.geofence.core.application.geofence.GeofenceService
import com.nogueira.geofence.core.domain.Geofence
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/geofence")
class GeofenceController(
    private val service: GeofenceService
) {

    @PostMapping
    fun create(@RequestBody req: GeofenceCreateRequest): ResponseEntity<Geofence> {

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(req.toGeofence()))

    }
}