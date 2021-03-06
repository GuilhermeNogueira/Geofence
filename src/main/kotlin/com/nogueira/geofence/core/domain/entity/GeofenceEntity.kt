package com.nogueira.geofence.core.domain.entity

import com.nogueira.geofence.core.domain.Geofence
import com.nogueira.geofence.core.domain.Point
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
data class GeofenceEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val radius: Int,

    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    val location: PointEntity
) {
    companion object {
        fun GeofenceEntity.toGeofence() =
            Geofence(this.id, this.radius, Point(this.location.lat, this.location.lng), this.name)
    }
}