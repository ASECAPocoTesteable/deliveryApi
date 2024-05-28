package org.pocotesteable.deliveryapi.controllers.payload.request

import jakarta.validation.constraints.NotBlank

data class StatusDTO(
    @field:NotBlank(message = "Status must not be blank")
    val status: String,
    @field:NotBlank(message = "Description must not be blank")
    val description: String,
)
