package auth.domain.user

import auth.domain.core.DomainValidationData
import auth.domain.core.DomainValidationHelper
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "users")
class User
constructor(
        firstName: String,
        lastName: String,
        password: String,
        mail: String,
        role: Role) {

    @Id
    @Column(nullable = false, name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    val userId: UUID = UUID.randomUUID()

    @Column(nullable = false, updatable = false)
    val createdAt: Date = Date()

    @Column(nullable = false)
    var password: String
        private set

    @Embedded
    var name: Name
        private set

    @Embedded
    var email: Email
        private set

    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinTable(
            name = "user_roles",
            joinColumns = [JoinColumn(name = "user_id")],
            inverseJoinColumns = [JoinColumn(name = "role_id")])
    var roles: MutableCollection<Role> = mutableListOf()

    fun confirmEmail(token: String) {
        if (this.email.confirmationToken == token && this.email.confirmationTokenExpiration < Date()) {
            this.email = Email(this.email.address, UUID.randomUUID().toString(), true, Date())
        }
    }

    fun removeFromRole(role: Role) {
        if (!this.roles.any { it.roleId == role.roleId }) {
            DomainValidationHelper.validateDomainModel<User>(DomainValidationData("role", "User doesn't have role '${role.name}'"))
        }
        this.roles.remove(role)
    }

    fun addToRole(role: Role) {
        if (this.roles.any { it.roleId == role.roleId }) {
            DomainValidationHelper.validateDomainModel<User>(DomainValidationData("role", "User already has '${role.name}'"))
        }
        this.roles.add(role)
    }

    init {

        val errors = mutableListOf<DomainValidationData>()

        if (firstName.isEmpty() || firstName.isBlank()) {
            errors.add(DomainValidationData("first_name", "First name is required."))
        }

        if (lastName.isEmpty() || lastName.isBlank()) {
            errors.add(DomainValidationData("last_name", "Last name is required."))
        }

        if (password.isEmpty() || password.isBlank()) {
            errors.add(DomainValidationData("password", "Password is required."))
        }

        DomainValidationHelper.validateDomainModel<User>(errors)

        this.name = Name(firstName, lastName)
        this.email = Email(mail, UUID.randomUUID().toString(), false, Date())
        this.password = password
        this.roles.add(role)
    }
}


