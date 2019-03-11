package auth.domain.user

import auth.domain.role.Role
import java.io.Serializable
import java.util.*
import javax.persistence.*

//@Entity
class UserRole constructor(
        @EmbeddedId val userRoleId: UserRoleId) {

        @ManyToOne(fetch = FetchType.EAGER, optional = false)
        @JoinColumn(name = "user_id")
        @MapsId("userId")
        val user: User? = null

        @OneToOne(fetch = FetchType.EAGER, optional = false)
        @JoinColumn(name = "role_id")
        @MapsId("roleId")
        val role: Role? = null
}


data class UserRoleId(val userId: UUID, val roleId: Int) : Serializable

