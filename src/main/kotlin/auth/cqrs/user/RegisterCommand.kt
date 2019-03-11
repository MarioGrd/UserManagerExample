package auth.cqrs.user

import auth.domain.role.RoleEnum
import auth.domain.user.User
import auth.infrastructure.core.PasswordProvider
import auth.infrastructure.repositories.UserRepository
import auth.infrastructure.services.RoleService
import auth.infrastructure.services.UserService
import com.grd.request.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.context.annotation.Scope
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

@HasRequestHandler( handler = RegisterCommandHandler::class)
data class RegisterCommand(
        @field: NotEmpty (message = "First name is required.")
        @field: NotBlank (message = "First name is blank.")
        val firstName: String,

        @field: NotBlank (message = "Last name is required.")
        val lastName: String,

        @field: NotBlank (message = "Password is required.")
        val password: String,

        @field: NotBlank ( message = "Email is required.")
        @field: Email ( message = "Email is not in valid format.")
        val email: String) : Request<Unit>

@Component
@Scope("prototype")
data class RegisterCommandHandler(
        @Autowired private val userService: UserService,
        @Autowired private val roleService: RoleService,
        @Autowired private val passwordProvider: PasswordProvider)
    : RequestHandler<RegisterCommand, Unit> {

    override fun handle(request: RegisterCommand) {

        val user = this.userService.findByEmailAddress(request.email)
        val role = this.roleService.findByIdSafe(RoleEnum.User.ordinal)

        if (!user.isPresent) {
            this.userService.save(User(request.firstName, request.lastName, passwordProvider.hashPassword(request.password), request.email, role))
        }
    }
}




