package auth.domain.core

import org.springframework.http.HttpStatus
import java.lang.Exception
import java.util.*

class DomainValidationHelper {
    companion object {
        inline fun<reified T> validateDomainModel(error: DomainValidationData) {
                val e = mutableMapOf<String, List<String>>()
                e[error.key] = listOf(error.value)
                throw DomainValidationException(
                        source = T::class.java.simpleName,
                        code = HttpStatus.INTERNAL_SERVER_ERROR,
                        errors = e)
        }
        inline fun<reified T> validateDomainModel(errors: MutableList<DomainValidationData>) {
            if (errors.any())
                throw DomainValidationException(
                        source = T::class.java.simpleName,
                        code = HttpStatus.INTERNAL_SERVER_ERROR,
                        errors = errors.groupBy({ it.key }, { it.value }))
        }
    }
}

data class DomainValidationData(val key: String, val value: String)
data class DomainValidationException(
        val source: String,
        val code: HttpStatus,
        val errors: Map<String, List<String>>,
        val at: Date = Date()
        ) : Exception()
