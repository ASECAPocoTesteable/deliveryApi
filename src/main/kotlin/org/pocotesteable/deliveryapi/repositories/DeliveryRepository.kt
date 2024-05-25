package org.pocotesteable.deliveryapi.repositories

import org.pocotesteable.deliveryapi.entities.Delivery
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface DeliveryRepository : JpaRepository<Delivery, UUID>
