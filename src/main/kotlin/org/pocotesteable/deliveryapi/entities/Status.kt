package org.pocotesteable.deliveryapi.entities

import jakarta.persistence.*
import org.pocotesteable.deliveryapi.enums.State

@Entity
data class Status(
    var state: State,

    var description: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
}
