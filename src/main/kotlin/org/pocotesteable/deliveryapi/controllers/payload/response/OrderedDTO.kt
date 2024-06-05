package org.pocotesteable.deliveryapi.controllers.payload.response

data class OrderedDTO(
    val id: Long,
    val userAddress: String,
    val status: String,
    val warehouseDirection: String,
    val products: List<ProductDTO>,
)

data class ProductDTO(
    val id: Long,
    val name: String,
    val quantity: Int,
)
