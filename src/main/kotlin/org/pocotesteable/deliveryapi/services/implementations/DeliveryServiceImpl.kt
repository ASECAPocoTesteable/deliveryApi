package org.pocotesteable.deliveryapi.services.implementations

import org.pocotesteable.deliveryapi.entities.Delivery
import org.pocotesteable.deliveryapi.repositories.DeliveryRepository
import org.pocotesteable.deliveryapi.services.interfaces.DeliveryService
import org.springframework.stereotype.Service

@Service
class DeliveryServiceImpl(val deliveryRepository: DeliveryRepository) : DeliveryService {
    override fun create() {
        val delivery = Delivery(true)
        deliveryRepository.save(delivery)
    }
}
