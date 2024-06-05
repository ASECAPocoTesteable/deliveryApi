package org.pocotesteable.deliveryapi.services.implementations

import org.pocotesteable.deliveryapi.controllers.payload.request.OrderDTO
import org.pocotesteable.deliveryapi.controllers.payload.request.StatusDTO
import org.pocotesteable.deliveryapi.controllers.payload.response.OrderedDTO
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
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@Service
class OrderServiceImpl(
    val orderRepository: OrderRepository,
    val productRepository: ProductRepository,
    val statusRepository: StatusRepository,
    val deliveryRepository: DeliveryRepository,
    val controlTowerServiceImpl: ControlTowerServiceImpl,
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
            val product = Product(it.product, it.quantity, order)
            productRepository.save(product)
            product
        }
    }

    override fun updateOrderStatus(orderId: Long, payload: StatusDTO) {
        val order = orderRepository.findById(orderId).orElseThrow { Exception("Order not found") }
        val status = statusRepository.findById(order.status.id).orElseThrow { Exception("Status not found") }
        val state = State.fromString(payload.status)
        verifyStatusChange(status, state)
        status.state = state
        status.description = payload.description
        statusRepository.save(status)
    }

    override fun takeOrder(orderId: Long): Mono<Any> {
        return controlTowerServiceImpl.notifyDelivery(orderId)
            .flatMap { success ->
                if (success.isEmpty()) {
                    updateOrderStatus(orderId, StatusDTO("INPROGRESS", "Tu orden esta en progreso de entrega."))
                    Mono.just(true)
                } else {
                    Mono.error(Exception("Error al notificar al Control Tower."))
                }
            }
    }

    override fun haveIncident(orderId: Long): Mono<Any> {
        return controlTowerServiceImpl.notifyIncident(orderId)
            .doOnError { e -> println("Error: ${e.message}") } // print the error message if an error occurs
            .flatMap { success ->
                if (success == "success") {
                    updateOrderStatus(orderId, StatusDTO("INCIDENT", "El repartidor tuvo un incidente. Se solucionara el problema a la brevedad."))
                    Mono.just(true)
                } else {
                    Mono.error(Exception("Error al notificar al Control Tower."))
                }
            }
    }

    override fun completeOrder(orderId: Long): Mono<Any> {
        return controlTowerServiceImpl.notifyComplete(orderId)
            .doOnError { e -> println("Error: ${e.message}") }
            .flatMap { success ->
                if (success == "success") {
                    updateOrderStatus(orderId, StatusDTO("DELIVERED", "Tu orden ya fue entregada."))

                    Mono.defer {
                        Mono.fromCallable {
                            val order = orderRepository.findById(orderId).orElseThrow { Exception("Order not found") }
                            val delivery = order.delivery
                            if (delivery != null) {
                                delivery.isAvailable = true
                                deliveryRepository.save(delivery)
                            }
                            order
                        }
                            .subscribeOn(Schedulers.boundedElastic())
                            .flatMap { Mono.just(true) }
                    }
                } else {
                    Mono.error(Exception("Error al notificar al Control Tower."))
                }
            }
    }

    override fun getOrderByDelivery(deliveryId: Long): List<OrderedDTO> {
        val delivery = deliveryRepository.findById(deliveryId).orElseThrow { Exception("Delivery not found") }
        val order = orderRepository.findByDeliveryId(delivery.id)
        // for each order, map it to OrderedDTO
        return order.map {
            OrderedDTO(
                it.userAddress,
                it.status.state.stateName,
            )
        }
    }

    private fun verifyStatusChange(orderStatus: Status, status: State) {
        when (status) {
            State.ASSIGNED -> {
                throw Exception("No se puede asignar la orden luego de su creación")
            }

            State.INPROGRESS -> {
                if (orderStatus.state == State.INPROGRESS || orderStatus.state == State.DELIVERED) {
                    throw Exception("No se puede cambiar del estado ${orderStatus.state} a $status")
                }
            }

            State.DELIVERED -> {
                if (orderStatus.state == State.ASSIGNED || orderStatus.state == State.INCIDENT || orderStatus.state == State.DELIVERED) {
                    throw Exception("No se puede cambiar del estado ${orderStatus.state} a $status")
                }
            }

            State.INCIDENT -> {
                if (orderStatus.state == State.DELIVERED || orderStatus.state == State.ASSIGNED || orderStatus.state == State.INCIDENT) {
                    throw Exception("No se puede cambiar del estado ${orderStatus.state} a $status")
                }
            }
        }
    }
}
