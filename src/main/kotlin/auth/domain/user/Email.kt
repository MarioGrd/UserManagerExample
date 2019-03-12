package auth.domain.user

import auth.domain.core.DomainValidationData
import auth.domain.core.DomainValidationHelper
import java.util.Date
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class Email internal constructor(

        @Column(nullable = false)
        val address: String,

        @Column(nullable = false)
        val confirmationToken: String,

        val isConfirmed: Boolean,

        @Column(nullable = false)
        val confirmationTokenExpiration: Date) {

    init {

        val errors = mutableListOf<DomainValidationData>()

        if (address.isEmpty() || address.isBlank()) {
            errors.add(DomainValidationData("email", "Email address is required."))
        }

        if (confirmationToken.isEmpty() || confirmationToken.isBlank()) {
            errors.add(DomainValidationData("confirmation_token", "Confirmation token is required."))
        }

        DomainValidationHelper.validateDomainModel<Email>(errors)
    }
}
