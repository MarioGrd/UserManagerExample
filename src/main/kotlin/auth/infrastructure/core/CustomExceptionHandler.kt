package auth.infrastructure.core

import auth.cqrs.core.BasicValidationException
import auth.cqrs.core.RequestValidationException
import auth.domain.core.DomainValidationException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import java.util.*
import javax.swing.text.html.parser.Entity


@ControllerAdvice
class CustomExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(RequestValidationException::class)
    protected fun handleValidationException(ex: RequestValidationException, request: WebRequest): ResponseEntity<Any> {
        val bodyOfResponse = ErrorResponse(ex.source, ex.errors, ex.at)
        return handleExceptionInternal(ex, bodyOfResponse, HttpHeaders(), ex.code, request)
    }

    @ExceptionHandler(DomainValidationException::class)
    protected fun handleDomainException(ex: DomainValidationException, request: WebRequest): ResponseEntity<Any> {
        val bodyOfResponse = ErrorResponse(ex.source, ex.errors, ex.at)
        return handleExceptionInternal(ex, bodyOfResponse, HttpHeaders(), ex.code, request)
    }

    @ExceptionHandler(EntityNotFoundException::class)
    protected fun handleEntityNotFoundException(ex: EntityNotFoundException, request: WebRequest): ResponseEntity<Any> {
        return handleExceptionInternal(ex, ex.msg, HttpHeaders(), HttpStatus.NOT_FOUND, request)
    }

    @ExceptionHandler(BasicValidationException::class)
    protected fun handleBasicValidationException(ex: BasicValidationException, request: WebRequest): ResponseEntity<Any> {
        return handleExceptionInternal(ex, ex.msg, HttpHeaders(), HttpStatus.NOT_FOUND, request)
    }
}

data class ErrorResponse(val source: String, val errors: Map<String, List<String>>,val at:Date)


