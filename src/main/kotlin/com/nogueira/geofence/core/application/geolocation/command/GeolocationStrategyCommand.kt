package com.nogueira.geofence.core.application.geolocation.command

import com.nogueira.geofence.core.domain.geofence.Point
import com.nogueira.geofence.core.domain.geofence.Geofence

data class GeolocationStrategyCommand(val currentLocation: Point, val geofence: Geofence)