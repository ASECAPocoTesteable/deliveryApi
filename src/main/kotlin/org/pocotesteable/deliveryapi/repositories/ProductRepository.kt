package org.pocotesteable.deliveryapi.repositories

import org.pocotesteable.deliveryapi.entities.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, Long> {

    fun findAllByPurchaseOrderId(purchaseOrderId: Long): List<Product>
}
