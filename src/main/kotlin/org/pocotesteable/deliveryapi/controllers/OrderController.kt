package org.pocotesteable.deliveryapi.controllers

import jakarta.validation.Valid
import org.pocotesteable.deliveryapi.controllers.payload.request.OrderDTO
import org.pocotesteable.deliveryapi.controllers.payload.request.StatusDTO
import org.pocotesteable.deliveryapi.services.interfaces.OrderService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderController(private val orderService: OrderService) {

    @PostMapping("/order")
    fun startOrder(@Valid @RequestBody payload: OrderDTO): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok().body(orderService.startOrder(payload))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body("Error: ${e.message}")
        }
    }

    @PostMapping("/order/{orderId}/status")
    fun updateOrderStatus(@PathVariable("orderId") orderId: Long, @Valid @RequestBody payload: StatusDTO): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok().body(orderService.updateOrderStatus(orderId, payload))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body("Error: ${e.message}")
        }
    }
}
