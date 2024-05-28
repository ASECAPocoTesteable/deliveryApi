package org.pocotesteable.deliveryapi.entities

import jakarta.persistence.*

@Entity
data class Status(
    var state: String,

    var description: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
}
