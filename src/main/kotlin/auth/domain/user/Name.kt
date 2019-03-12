package auth.domain.user

import auth.domain.core.DomainValidationData
import auth.domain.core.DomainValidationHelper
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class Name constructor(
        @Column(nullable = false)
        val firstName: String,

        @Column(nullable = false)
        val lastName: String,
        val middleName: String? = "") {

    init {

        val errors = mutableListOf<DomainValidationData>()

        if (firstName.isEmpty() || firstName.isBlank()) {
            errors.add(DomainValidationData("first_name", "First name is required."))
        }

        if (lastName.isEmpty() || lastName.isBlank()) {
            errors.add(DomainValidationData("last_name", "Last name is required."))
        }

        DomainValidationHelper.validateDomainModel<Name>(errors)
    }
}