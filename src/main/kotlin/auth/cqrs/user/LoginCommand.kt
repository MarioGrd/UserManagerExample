package auth.cqrs.user

import auth.infrastructure.core.PasswordProvider
import auth.infrastructure.repositories.UserRepository
import auth.infrastructure.services.UserService
import auth.infrastructure.services.UserServiceImpl
import com.grd.request.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

@HasRequestHandler(LoginCommandHandler::class)
data class LoginCommand(
        @field: NotBlank(message = "Email is required.")
        @field: Email(message = "Not a valid email.")
        val email: String,

        @field: NotBlank(message = "Password is required.")
        val password: String) : Request<Boolean>

@Component
@Scope("prototype")
data class LoginCommandHandler(
        @Autowired private val userService: UserService,
        @Autowired private val passwordProvider: PasswordProvider)
    : RequestHandler<LoginCommand, Boolean> {

    override fun handle(request: LoginCommand) : Boolean {

        val user = this.userService.findByEmailAddressSafe(request.email)
        return passwordProvider.validate(user.password, request.password)

    }
}
