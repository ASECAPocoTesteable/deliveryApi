package org.pocotesteable.deliveryapi.entities

import jakarta.persistence.*

@Entity
class PurchaseOrder(
    @ManyToOne
    var delivery: Delivery? = null,

    var userAddress: String,

    @OneToOne
    var status: Status,

    var warehouseDirection: String,

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
}
