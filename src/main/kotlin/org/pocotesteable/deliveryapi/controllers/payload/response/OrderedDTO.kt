package org.pocotesteable.deliveryapi.controllers.payload.response

data class OrderedDTO(
    val id: Long,
    val userAddress: String,
    val status: String,
)
