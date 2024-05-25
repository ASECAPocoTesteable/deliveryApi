package org.pocotesteable.deliveryapi.entities

import jakarta.persistence.*
import java.util.*

@Entity
data class Status(
    var state: String,

    var description: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: UUID? = null
}
