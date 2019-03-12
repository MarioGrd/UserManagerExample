package auth.cqrs.user

import auth.infrastructure.services.UserService
import com.grd.request.HasRequestHandler
import com.grd.request.Request
import com.grd.request.RequestHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import java.util.*
import javax.validation.constraints.NotBlank

data class UserResponse(
        val id: UUID,
        val name: NameResponse)

data class NameResponse(
        val firstName: String,
        val lastName: String)

@HasRequestHandler(GetUserQueryHandler::class)
data class GetUserQuery(
        val id: UUID)
    : Request<UserResponse>

@Component
@Scope("prototype")
data class GetUserQueryHandler(
        @Autowired private val userService: UserService)
    : RequestHandler<GetUserQuery, UserResponse> {

    override fun handle(request: GetUserQuery): UserResponse {

        val user = this.userService.findByIdSafe(request.id)
        return UserResponse(user.userId, NameResponse(user.name.firstName, user.name.lastName))

    }
}