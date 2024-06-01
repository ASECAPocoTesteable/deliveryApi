package org.pocotesteable.deliveryapi.controllers

import org.pocotesteable.deliveryapi.services.interfaces.DeliveryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class DeliveryController(private val deliveryService: DeliveryService) {
    @PostMapping("/delivery")
    fun startOrder(): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok().body(deliveryService.create())
        } catch (e: Exception) {
            ResponseEntity.badRequest().body("Error: ${e.message}")
        }
    }
}
