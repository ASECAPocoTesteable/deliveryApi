package org.pocotesteable.deliveryapi.services.implementations

import org.pocotesteable.deliveryapi.controllers.payload.request.OrderDTO
import org.pocotesteable.deliveryapi.controllers.payload.request.StatusDTO
import org.pocotesteable.deliveryapi.entities.Product
import org.pocotesteable.deliveryapi.entities.PurchaseOrder
import org.pocotesteable.deliveryapi.entities.Status
import org.pocotesteable.deliveryapi.repositories.DeliveryRepository
import org.pocotesteable.deliveryapi.repositories.OrderRepository
import org.pocotesteable.deliveryapi.repositories.ProductRepository
import org.pocotesteable.deliveryapi.repositories.StatusRepository
import org.pocotesteable.deliveryapi.services.interfaces.OrderService
import org.springframework.stereotype.Service

@Service
class OrderServiceImpl(val orderRepository: OrderRepository, val productRepository: ProductRepository, val statusRepository: StatusRepository, val deliveryRepository: DeliveryRepository) : OrderService {
    override fun startOrder(payload: OrderDTO) {
        val deliveries = deliveryRepository.getFirstAvailable().orElseThrow { Exception("No available delivery") }
        if (deliveries.isEmpty()) {
            throw Exception("No available delivery")
        }
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

    override fun updateOrderStatus(orderId: Long, payload: StatusDTO) {
        val order = orderRepository.findById(orderId).orElseThrow { Exception("Order not found") }
        val status = statusRepository.findById(order.status.id).orElseThrow { Exception("Status not found") }
        status.state = payload.status
        status.description = payload.description
        statusRepository.save(status)
    }

    override fun completeOrder(orderId: Long) {
        updateOrderStatus(orderId, StatusDTO("COMPLETE", "Your order is complete."))

        val order = orderRepository.findById(orderId).orElseThrow { Exception("Order not found") }
        val delivery = order.delivery
        if (delivery != null) {
            delivery.isAvailable = true
            deliveryRepository.save(delivery)
        }
    }
}
