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

@ControllerAdvice
class ExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(RequestValidationException::class)
    protected fun handleValidationException(ex: RequestValidationException, request: WebRequest): ResponseEntity<Any> =
        handleErrorInternal(ex, ErrorResponse(ex.errors, ex.at), ex.code, request)

    @ExceptionHandler(DomainValidationException::class)
    protected fun handleDomainException(ex: DomainValidationException, request: WebRequest): ResponseEntity<Any>
        = handleErrorInternal(ex, ErrorResponse(ex.errors, ex.at), ex.code, request)

    @ExceptionHandler(EntityNotFoundException::class)
    protected fun handleEntityNotFoundException(ex: EntityNotFoundException, request: WebRequest): ResponseEntity<Any>
        = handleErrorInternal(ex, ex.msg, HttpStatus.NOT_FOUND, request)

    @ExceptionHandler(BasicValidationException::class)
    protected fun handleBasicValidationException(ex: BasicValidationException, request: WebRequest): ResponseEntity<Any>
        = handleErrorInternal(ex, ex.msg, HttpStatus.NOT_FOUND, request)

    private fun handleErrorInternal(ex: Exception, msg: Any, status: HttpStatus, request: WebRequest): ResponseEntity<Any>
        = handleExceptionInternal(ex, msg, HttpHeaders(), status, request)
}

data class ErrorResponse(val errors: Map<String, List<String>>,val at:Date)


