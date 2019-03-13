package auth.cqrs.user

import auth.infrastructure.services.RoleService
import auth.infrastructure.services.UserService
import request.HasRequestHandler
import request.Request
import request.RequestHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import java.util.*

@HasRequestHandler(handler = AddUserToRoleCommandHandler::class)
data class AddUserToRoleCommand(
        val userId: UUID,
        val roleId: Int): Request<Unit>

@Component
@Scope("prototype")
data class AddUserToRoleCommandHandler(
        @Autowired private val userService: UserService,
        @Autowired private val roleService: RoleService)
    : RequestHandler<AddUserToRoleCommand, Unit> {

    override fun handle(request: AddUserToRoleCommand) {

        val user = this.userService.findByIdSafe(request.userId)
        val role = this.roleService.findByIdSafe(request.roleId)
        user.addToRole(role)
        userService.save(user)
    }
}