package auth.cqrs.user

import auth.infrastructure.repositories.UserRepository
import com.grd.request.HasRequestHandler
import com.grd.request.Request
import com.grd.request.RequestHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import java.util.*

data class UserResponse(
        val id: UUID,
        val name: NameResponse)

data class NameResponse(
        val firstName: String,
        val lastName: String)

@HasRequestHandler(GetUserQueryHandler::class)
data class GetUserQuery(
        val id: UUID) : Request<UserResponse>

@Component
@Scope("prototype")
data class GetUserQueryHandler(
        @Autowired private val userRepository: UserRepository)
    : RequestHandler<GetUserQuery, UserResponse> {

    override fun handle(request: GetUserQuery): UserResponse {

        val user = this.userRepository.findById(request.id).get()
        return UserResponse(user.userId, NameResponse(user.name.firstName, user.name.lastName))

    }
}