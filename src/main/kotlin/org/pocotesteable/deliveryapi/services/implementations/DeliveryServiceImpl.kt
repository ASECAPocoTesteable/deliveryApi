package org.pocotesteable.deliveryapi.services.implementations

import org.pocotesteable.deliveryapi.controllers.payload.request.OrderDTO
import org.pocotesteable.deliveryapi.entities.Product
import org.pocotesteable.deliveryapi.entities.PurchaseOrder
import org.pocotesteable.deliveryapi.entities.Status
import org.pocotesteable.deliveryapi.repositories.DeliveryRepository
import org.pocotesteable.deliveryapi.repositories.OrderRepository
import org.pocotesteable.deliveryapi.repositories.ProductRepository
import org.pocotesteable.deliveryapi.repositories.StatusRepository
import org.pocotesteable.deliveryapi.services.interfaces.DeliveryService
import org.springframework.stereotype.Service

@Service
class DeliveryServiceImpl(val orderRepository: OrderRepository, val productRepository: ProductRepository, val statusRepository: StatusRepository, val deliveryRepository: DeliveryRepository) : DeliveryService {
    override fun startDelivery(payload: OrderDTO) {
        val deliveries = deliveryRepository.getFirstAvailable().orElseThrow { Exception("No available delivery") }
        val delivery = deliveries.first()
        delivery.isAvailable = false
        deliveryRepository.save(delivery)

        val status = Status("PREPARE", "Your order is being prepared.")
        statusRepository.save(status)

        val order = PurchaseOrder(
            userAddress = payload.userAddress,
            status = status,
            delivery = delivery,
        )
        orderRepository.save(order)

        payload.products.map {
            val product = Product(it.product, it.quantity, it.warehouseAddress, order)
            productRepository.save(product)
            product
        }
    }
}
