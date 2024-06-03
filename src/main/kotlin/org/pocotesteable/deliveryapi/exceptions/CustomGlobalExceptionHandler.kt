package org.pocotesteable.deliveryapi.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class CustomGlobalExceptionHandler {

    // Método para manejar las excepciones de validación
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun customValidationError(ex: MethodArgumentNotValidException, request: WebRequest): ResponseEntity<Any> {
        // Obtenemos todos los errores de validación
        val errors = ex.bindingResult.fieldErrors.map { error -> "${error.field}: ${error.defaultMessage}" }

        // Devolvemos una respuesta con los errores
        return ResponseEntity(errors, HttpStatus.BAD_REQUEST)
    }
}
