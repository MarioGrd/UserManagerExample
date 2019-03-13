package auth.cqrs.user

import auth.cqrs.core.BasicValidationException
import auth.infrastructure.services.UserService
import request.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import java.util.*
import javax.validation.constraints.NotNull

@HasRequestHandler(ConfirmEmailCommandHandler::class)
data class ConfirmEmailCommand(
        @field: NotNull(message = "Token is required.")
        val token: String) : Request<Unit>

@Component
@Scope("prototype")
data class ConfirmEmailCommandHandler(
        @Autowired private val userService: UserService)
    : RequestHandler<ConfirmEmailCommand, Unit> {

    override fun handle(request: ConfirmEmailCommand) {

        val user = this.userService.findByEmailConfirmationTokenSafe(request.token)
        if (user.email.confirmationTokenExpiration > Date()) throw BasicValidationException("Token expired.")
        user.confirmEmail(request.token)
        userService.save(user)
    }
}

