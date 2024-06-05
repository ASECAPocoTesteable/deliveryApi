package org.pocotesteable.deliveryapi.loaders

import org.pocotesteable.deliveryapi.entities.Delivery
import org.pocotesteable.deliveryapi.repositories.DeliveryRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DeliveryLoader(private val deliveryRepository: DeliveryRepository) : CommandLineRunner {
    override fun run(vararg args: String?) {
        if (deliveryRepository.findAll().isEmpty()) {
            deliveryRepository.saveAll(
                listOf(
                    Delivery(isAvailable = true),
                    Delivery(isAvailable = true),
                    Delivery(isAvailable = true),
                    Delivery(isAvailable = true),
                    Delivery(isAvailable = true),
                    Delivery(isAvailable = true),
                    Delivery(isAvailable = true),
                    Delivery(isAvailable = true),
                    Delivery(isAvailable = true),
                    Delivery(isAvailable = true),
                    Delivery(isAvailable = true),
                    Delivery(isAvailable = true),
                    Delivery(isAvailable = true),
                    Delivery(isAvailable = true),
                    Delivery(isAvailable = true),
                    Delivery(isAvailable = true),
                    Delivery(isAvailable = true),
                    Delivery(isAvailable = true),
                    Delivery(isAvailable = true),
                    Delivery(isAvailable = true),
                    Delivery(isAvailable = true),
                    Delivery(isAvailable = true),
                    Delivery(isAvailable = true),
                    Delivery(isAvailable = true),
                    Delivery(isAvailable = true),
                ),
            )
        }
    }
}
