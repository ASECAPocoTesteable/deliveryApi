package org.pocotesteable.deliveryapi.entities

import jakarta.persistence.*

@Entity
class Delivery(
    var isAvailable: Boolean,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
}
