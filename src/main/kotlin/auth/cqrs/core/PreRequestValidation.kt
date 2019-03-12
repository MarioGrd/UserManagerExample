package auth.cqrs.core

import com.grd.request.Request
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import request.PreRequestHandler
import java.lang.Exception
import java.util.*
import javax.validation.Validator

@Component
@Scope("prototype")
class PreRequestValidation(@Autowired private val validator: Validator) : PreRequestHandler<Request<Any>, Any> {

    override fun handle(request: Request<Any>) {
        val errors =
            this.validator.validate(request)
                .map { RequestValidationData(it.propertyPath.toString(), it.message) }

        this.validateRequest(request.javaClass.name, errors)
    }

    private fun validateRequest(
            source: String,
            errors: List<RequestValidationData>) {
        if (errors.any())
            throw RequestValidationException(
                    source = source,
                    code = HttpStatus.BAD_REQUEST,
                    errors = errors.groupBy({ it.key }, { it.value }))
    }
}

data class RequestValidationData(val key: String, val value: String)
data class RequestValidationException(
        val source: String,
        val code: HttpStatus,
        val errors: Map<String, List<String>>,
        val at: Date = Date()) : Exception()

data class BasicValidationException(val msg: String, val code: HttpStatus = HttpStatus.BAD_REQUEST, val at: Date = Date()) : Exception()