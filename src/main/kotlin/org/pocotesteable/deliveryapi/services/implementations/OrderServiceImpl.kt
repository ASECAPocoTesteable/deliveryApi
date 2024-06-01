package org.pocotesteable.deliveryapi.services.implementations

import org.pocotesteable.deliveryapi.controllers.payload.request.OrderDTO
import org.pocotesteable.deliveryapi.controllers.payload.request.StatusDTO
import org.pocotesteable.deliveryapi.entities.Product
import org.pocotesteable.deliveryapi.entities.PurchaseOrder
import org.pocotesteable.deliveryapi.entities.Status
import org.pocotesteable.deliveryapi.enums.State
import org.pocotesteable.deliveryapi.repositories.DeliveryRepository
import org.pocotesteable.deliveryapi.repositories.OrderRepository
import org.pocotesteable.deliveryapi.repositories.ProductRepository
import org.pocotesteable.deliveryapi.repositories.StatusRepository
import org.pocotesteable.deliveryapi.services.interfaces.OrderService
import org.springframework.stereotype.Service

@Service
class OrderServiceImpl(
    val orderRepository: OrderRepository,
    val productRepository: ProductRepository,
    val statusRepository: StatusRepository,
    val deliveryRepository: DeliveryRepository
) : OrderService {
    override fun startOrder(payload: OrderDTO) {
        val deliveries = deliveryRepository.getFirstAvailable().orElseThrow { Exception("No available delivery") }
        if (deliveries.isEmpty()) {
            throw Exception("No available delivery")
        }
        val delivery = deliveries.first()
        delivery.isAvailable = false
        deliveryRepository.save(delivery)

        val status = Status(State.ASSIGNED, "Tu orden ya ha sido asignada a un delivery.")
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
        val state = State.fromString(payload.status)
        verifyStatusChange(order, status)
        status.state = state
        status.description = payload.description
        statusRepository.save(status)
    }

    override fun takeOrder(orderId: Long) {
        updateOrderStatus(orderId, StatusDTO("INPROGRESS", "Tu orden esta en progreso de entrega."))
    }

    override fun completeOrder(orderId: Long) {
        updateOrderStatus(orderId, StatusDTO("DELIVERED", "Tu orden ya fue entregada."))

        val order = orderRepository.findById(orderId).orElseThrow { Exception("Order not found") }
        val delivery = order.delivery
        if (delivery != null) {
            delivery.isAvailable = true
            deliveryRepository.save(delivery)
        }
    }

    private fun verifyStatusChange(order: PurchaseOrder, status: Status) {
        when (status.state) {
            State.ASSIGNED -> {
                throw Exception("No se puede asignar la orden luego de su creación")
            }

            State.INPROGRESS -> {
                if (order.status.state == State.INPROGRESS || order.status.state == State.CANCELED || order.status.state == State.DELIVERED) {
                    throw Exception("No se puede cambiar del estado ${order.status.state} a ${status.state}")
                }
            }

            State.DELIVERED -> {
                if (order.status.state == State.ASSIGNED || order.status.state == State.CANCELED || order.status.state == State.DELIVERED) {
                    throw Exception("No se puede cambiar del estado ${order.status.state} a ${status.state}")
                }
            }

            State.CANCELED -> {
                if (order.status.state == State.DELIVERED) {
                    throw Exception("No se puede cambiar del estado ${order.status.state} a ${status.state}")
                }
            }
        }
    }
}
