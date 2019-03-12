package auth.domain.user

import auth.domain.core.DomainValidationData
import auth.domain.core.DomainValidationHelper
import javax.persistence.*

@Entity
class Role
constructor(
        @Id
        @Column(nullable = false, name = "role_id")
        @GeneratedValue(strategy = GenerationType.AUTO)
        val roleId: Int,

        @Column(nullable = false)
        val name: String) {

    init {

        val errors = mutableListOf<DomainValidationData>()

        if (this.name.isBlank() || this.name.isEmpty()) {
            errors.add(DomainValidationData("role_name", "Role name is required."))
        }

        DomainValidationHelper.validateDomainModel<Role>(errors)
    }
}
