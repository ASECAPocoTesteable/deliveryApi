package org.pocotesteable.deliveryapi.repositories

import org.pocotesteable.deliveryapi.entities.Delivery
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface DeliveryRepository : JpaRepository<Delivery, Long> {
    @Query("SELECT d FROM Delivery d WHERE d.isAvailable = true")
    fun getFirstAvailable(): Optional<List<Delivery>>
}
