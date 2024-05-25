package org.pocotesteable.deliveryapi.entities

import jakarta.persistence.*
import java.util.*

@Entity
data class Delivery(
    var name: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: UUID? = null
}
