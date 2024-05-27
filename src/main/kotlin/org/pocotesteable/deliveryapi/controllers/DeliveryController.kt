package org.pocotesteable.deliveryapi.controllers

import jakarta.validation.Valid
import org.pocotesteable.deliveryapi.controllers.payload.request.OrderDTO
import org.pocotesteable.deliveryapi.services.interfaces.DeliveryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class DeliveryController(private val deliveryService: DeliveryService) {

    @PostMapping("/delivery")
    fun startDelivery(@Valid @RequestBody payload: OrderDTO): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok().body(deliveryService.startDelivery(payload))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body("Error: ${e.message}")
        }
    }
}
