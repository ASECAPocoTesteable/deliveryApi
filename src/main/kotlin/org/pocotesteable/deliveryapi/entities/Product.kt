package org.pocotesteable.deliveryapi.entities

import jakarta.persistence.*
import java.util.*

@Entity
data class Product(
    var name: String,
    var quantity: Int,
    var warehouse_address: String,
    @ManyToOne
    var purchaseOrder: PurchaseOrder,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: UUID? = null
}
