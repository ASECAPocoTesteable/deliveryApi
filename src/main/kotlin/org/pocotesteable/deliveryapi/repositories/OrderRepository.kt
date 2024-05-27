package org.pocotesteable.deliveryapi.repositories

import org.pocotesteable.deliveryapi.entities.PurchaseOrder
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<PurchaseOrder, Long>
