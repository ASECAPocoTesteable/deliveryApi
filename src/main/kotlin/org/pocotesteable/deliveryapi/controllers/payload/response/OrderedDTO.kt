package org.pocotesteable.deliveryapi.controllers.payload.response

data class OrderedDTO(
    val userAddress: String,
    val status: String,
    val orderId: Long,
    val warehouseDirection: String,
)
