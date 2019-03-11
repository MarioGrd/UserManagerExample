package auth.cqrs.user

import auth.cqrs.core.BasicValidationException
import auth.cqrs.core.RequestValidationException
import auth.infrastructure.repositories.UserRepository
import auth.infrastructure.services.UserServiceImpl
import com.grd.request.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import java.util.*
import javax.validation.ValidationException

@HasRequestHandler(ConfirmEmailCommandHandler::class)
data class ConfirmEmailCommand(val token: String) : Request<Unit>

@Component
@Scope("prototype")
data class ConfirmEmailCommandHandler(
        @Autowired private val userService: UserServiceImpl)
    : RequestHandler<ConfirmEmailCommand, Unit> {

    override fun handle(request: ConfirmEmailCommand) {

        val user = this.userService.findByEmailConfirmationTokenSafe(request.token)
        if (user.email.confirmationTokenExpiration > Date()) throw BasicValidationException("Token expired.")
        user.confirmEmail(request.token)
        userService.save(user)
    }
}

