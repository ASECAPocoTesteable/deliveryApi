package org.pocotesteable.deliveryapi.entities

import jakarta.persistence.*

@Entity
data class Delivery(
    var name: String,
    var isAvailable: Boolean,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
}