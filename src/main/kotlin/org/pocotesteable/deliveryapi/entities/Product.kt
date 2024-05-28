package org.pocotesteable.deliveryapi.entities

import jakarta.persistence.*

@Entity
data class Product(
    var name: String,
    var quantity: Int,
    var warehouse_address: String,
    @ManyToOne
    var purchaseOrder: PurchaseOrder,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
}
