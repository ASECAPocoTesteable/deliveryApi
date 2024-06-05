package org.pocotesteable.deliveryapi.services.implementations

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class ControlTowerServiceImpl(
    @Autowired private val webClient: WebClient,
) {
    fun notifyDelivery(orderId: Long): Mono<String> {
//        val url = "http://controltowerpt:8080/delivery/picked?orderId=$orderId"
//
//        return webClient.put()
//            .uri(url)
//            .retrieve()
//            .bodyToMono(String::class.java)
//            .onErrorResume { throwable ->
//                Mono.error(Exception("Failed to notify picked: ${throwable.message}", throwable))
//            }
        return Mono.just("")
    }

    fun notifyIncident(orderId: Long): Mono<String> {
//        val url = "http://controltowerpt:8080/delivery/failed?orderId=$orderId"
//
//        return webClient.put()
//            .uri(url)
//            .retrieve()
//            .bodyToMono(String::class.java)
//            .onErrorResume { throwable ->
//                Mono.error(Exception("Failed to notify incident: ${throwable.message}", throwable))
//            }
        return Mono.just("success")
    }

    fun notifyComplete(orderId: Long): Mono<String> {
//        val url = "http://controltowerpt:8080/delivery/completed?orderId=$orderId"
//
//        return webClient.put()
//            .uri(url)
//            .retrieve()
//            .bodyToMono(String::class.java)
//            .onErrorResume { throwable ->
//                Mono.error(Exception("Failed to notify complete: ${throwable.message}", throwable))
//            }
        return Mono.just("success")
    }
}
