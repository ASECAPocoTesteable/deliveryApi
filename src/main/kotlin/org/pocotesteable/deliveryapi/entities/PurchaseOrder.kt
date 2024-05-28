package org.pocotesteable.deliveryapi.entities

import jakarta.persistence.*

@Entity
class PurchaseOrder(
    @OneToOne
    var delivery: Delivery? = null,

    var userAddress: String,

    @OneToOne
    var status: Status,

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
}
