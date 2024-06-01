package org.pocotesteable.deliveryapi.entities

import jakarta.persistence.*

@Entity
data class Product(
    var name: String,
    var quantity: Int,
    @ManyToOne
    var purchaseOrder: PurchaseOrder,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
}
