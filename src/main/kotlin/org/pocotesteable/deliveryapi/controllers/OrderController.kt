package org.pocotesteable.deliveryapi.controllers

import jakarta.validation.Valid
import org.pocotesteable.deliveryapi.controllers.payload.request.OrderDTO
import org.pocotesteable.deliveryapi.services.interfaces.OrderService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class OrderController(private val orderService: OrderService) {

    @PostMapping("/order")
    fun startOrder(@Valid @RequestBody payload: OrderDTO): ResponseEntity<Any> {
        return try {
            orderService.startOrder(payload)
            ResponseEntity.ok().body(true)
        } catch (e: Exception) {
            ResponseEntity.badRequest().body("Error: ${e.message}")
        }
    }

    @PostMapping("/order/{orderId}/take")
    fun takeOrder(@PathVariable("orderId") orderId: Long): Mono<ResponseEntity<Any>> {
        return orderService.takeOrder(orderId)
            .map { ResponseEntity.ok().body(it) }
            .onErrorResume { e ->
                Mono.just(ResponseEntity.badRequest().body("Error: ${e.message}"))
            }
    }

    @PostMapping("/order/{orderId}/incident")
    fun haveIncident(@PathVariable("orderId") orderId: Long): Mono<ResponseEntity<Any>> {
        return orderService.haveIncident(orderId)
            .map { ResponseEntity.ok().body(it) }
            .onErrorResume { e ->
                Mono.just(ResponseEntity.badRequest().body("Error: ${e.message}"))
            }
    }

    @PostMapping("/order/{orderId}/complete")
    fun completeOrder(@PathVariable("orderId") orderId: Long): Mono<ResponseEntity<Any>> {
        return orderService.completeOrder(orderId)
            .map { ResponseEntity.ok().body(it) }
            .onErrorResume { e ->
                Mono.just(ResponseEntity.badRequest().body("Error: ${e.message}"))
            }
    }

    @GetMapping("/order/{deliveryId}")
    fun getOrderByDelivery(@PathVariable("deliveryId") deliveryId: Long): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok().body(orderService.getOrderByDelivery(deliveryId))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body("Error: ${e.message}")
        }
    }
}
