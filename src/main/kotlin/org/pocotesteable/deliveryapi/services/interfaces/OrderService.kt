package org.pocotesteable.deliveryapi.services.interfaces

import org.pocotesteable.deliveryapi.controllers.payload.request.OrderDTO
import org.pocotesteable.deliveryapi.controllers.payload.request.StatusDTO

interface OrderService {
    fun startDelivery(payload: OrderDTO) {}

    fun updateDeliveryStatus(orderId: Long, payload: StatusDTO) {}
}
