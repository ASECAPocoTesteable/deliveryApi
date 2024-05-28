package org.pocotesteable.deliveryapi.services.interfaces

import org.pocotesteable.deliveryapi.controllers.payload.request.OrderDTO

interface DeliveryService {
    fun startDelivery(payload: OrderDTO) {
    }
}
