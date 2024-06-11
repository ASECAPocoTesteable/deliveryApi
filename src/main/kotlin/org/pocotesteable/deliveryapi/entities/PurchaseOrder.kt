package org.pocotesteable.deliveryapi.entities

import jakarta.persistence.*

@Entity
class PurchaseOrder(
    @ManyToOne
    var delivery: Delivery? = null,

    var userAddress: String,

    @OneToOne(cascade = [CascadeType.ALL])
    var status: Status,

    var warehouseDirection: String,

    @OneToMany(mappedBy = "purchaseOrder", cascade = [CascadeType.ALL])
    var products: List<Product> = emptyList(),

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
}
