package org.pocotesteable.deliveryapi.controllers.payload.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Positive

data class OrderDTO(
    @field:Positive(message = "Order ID must be greater than 0")
    val orderId: Long,
    @field:NotBlank(message = "User address must not be blank")
    val userAddress: String,
    @field:NotEmpty(message = "Products must not be empty")
    val products: List<ProductQuantityDTO>,
    @field:NotBlank(message = "Warehouse direction must not be blank")
    val warehouseDirection: String,
)

data class ProductQuantityDTO(
    @field:NotBlank(message = "Product must not be blank")
    val product: String,
    @field:Positive(message = "Quantity must be greater than 0")
    val quantity: Int,
)
