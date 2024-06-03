package org.pocotesteable.deliveryapi.services.interfaces

import org.pocotesteable.deliveryapi.controllers.payload.request.OrderDTO
import org.pocotesteable.deliveryapi.controllers.payload.request.StatusDTO
import org.pocotesteable.deliveryapi.controllers.payload.response.OrderedDTO
import reactor.core.publisher.Mono

interface OrderService {
    fun startOrder(payload: OrderDTO) {}

    fun updateOrderStatus(orderId: Long, payload: StatusDTO) {}

    fun takeOrder(orderId: Long): Mono<Any>

    fun haveIncident(orderId: Long): Mono<Any>

    fun completeOrder(orderId: Long) {}

    fun getOrderByDelivery(deliveryId: Long): List<OrderedDTO>
}
