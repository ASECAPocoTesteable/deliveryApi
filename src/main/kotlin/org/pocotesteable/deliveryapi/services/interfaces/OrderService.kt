package org.pocotesteable.deliveryapi.services.interfaces

import org.pocotesteable.deliveryapi.controllers.payload.request.OrderDTO
import org.pocotesteable.deliveryapi.controllers.payload.request.StatusDTO

interface OrderService {
    fun startOrder(payload: OrderDTO) {}

    fun updateOrderStatus(orderId: Long, payload: StatusDTO) {}

    fun takeOrder(orderId: Long) {}

    fun completeOrder(orderId: Long) {}
}
