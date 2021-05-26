package com.nogueira.geofence.core.domain.entity

import com.nogueira.geofence.core.domain.Advertising
import com.nogueira.geofence.core.domain.entity.GeofenceEntity.Companion.toGeofence
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
data class AdvertisingEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val href: String,

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    val location: GeofenceEntity
) {
    companion object {
        fun AdvertisingEntity.toAdvertising() =
            Advertising(this.id, this.name, this.location.toGeofence(), this.href)
    }
}