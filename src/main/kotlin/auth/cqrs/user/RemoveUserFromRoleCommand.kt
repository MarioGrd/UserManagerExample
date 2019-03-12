package auth.cqrs.user

import auth.infrastructure.services.RoleService
import auth.infrastructure.services.UserService
import com.grd.request.HasRequestHandler
import com.grd.request.Request
import com.grd.request.RequestHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import java.util.*
import javax.validation.constraints.NotBlank

@HasRequestHandler(handler = RemoveUserFromRoleCommandHandler::class)
data class RemoveUserFromRoleCommand(
        val userId: UUID,
        val roleId: Int)
    : Request<Unit>

@Component
@Scope("prototype")
class RemoveUserFromRoleCommandHandler(
        @Autowired private val userService: UserService,
        @Autowired private val roleService: RoleService)
    : RequestHandler<RemoveUserFromRoleCommand, Unit> {

    override fun handle(request: RemoveUserFromRoleCommand) {

        val user = this.userService.findByIdSafe(request.userId)
        val role = this.roleService.findByIdSafe(request.roleId)
        user.removeFromRole(role)
        userService.save(user)

    }
}