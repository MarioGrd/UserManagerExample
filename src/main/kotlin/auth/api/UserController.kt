package auth.api

import auth.cqrs.user.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*
import request.RequestBus

@RestController
@RequestMapping("/api/users")
class UserController(@Autowired private val requestBus: RequestBus) {

    @GetMapping("/{userId}")
    fun getUser(@RequestParam userId: UUID) = this.requestBus.sendRequest(GetUserQuery(userId))

    @PostMapping("/{userId}/role/{roleId}/add")
    fun addToRole(@PathVariable userId: UUID, @PathVariable roleId: Int) = this.requestBus.sendRequest(AddUserToRoleCommand(userId, roleId))

    @PostMapping("/{userId}/role/{roleId}/remove")
    fun removeFromRole(@PathVariable userId: UUID, @PathVariable roleId: Int) = this.requestBus.sendRequest(RemoveUserFromRoleCommand(userId, roleId))
}
