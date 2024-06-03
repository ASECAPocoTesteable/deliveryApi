package org.pocotesteable.deliveryapi.repositories

import org.pocotesteable.deliveryapi.entities.PurchaseOrder
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface OrderRepository : JpaRepository<PurchaseOrder, Long> {
    @Query("SELECT o FROM PurchaseOrder o WHERE o.delivery.id = :deliveryId")
    fun findByDeliveryId(deliveryId: Long): List<PurchaseOrder>
}
