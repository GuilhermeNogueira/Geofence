package com.nogueira.geofence.core.application.geolocation

import com.nogueira.geofence.core.domain.Point
import com.nogueira.geofence.core.domain.Geofence

data class GeolocationStrategyCommand(val currentLocation: Point, val geofence: Geofence)