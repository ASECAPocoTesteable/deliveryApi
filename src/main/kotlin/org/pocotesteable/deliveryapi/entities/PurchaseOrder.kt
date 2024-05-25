package org.pocotesteable.deliveryapi.entities

import jakarta.persistence.*
import java.util.*

@Entity
class PurchaseOrder(
    @OneToOne
    var delivery: Delivery? = null,

    var userAddress: String,

    @OneToOne
    var status: Status,

) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: UUID? = null
}
