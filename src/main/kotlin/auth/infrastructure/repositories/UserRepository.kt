package auth.infrastructure.repositories

import auth.domain.user.User
import org.springframework.data.repository.CrudRepository
import java.util.*

interface UserRepository : CrudRepository<User, UUID> {
    fun findByEmailConfirmationToken(emailConfirmationToken: String) : Optional<User>
    fun findByEmailAddress(email: String): Optional<User>
}