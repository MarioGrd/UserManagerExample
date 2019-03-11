package auth.infrastructure.services

import auth.domain.user.User
import auth.infrastructure.core.EntityNotFoundException
import auth.infrastructure.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

interface UserService {
    fun findByIdSafe(id: UUID): User
    fun findByEmailAddress(email: String): Optional<User>
    fun findByEmailAddressSafe(email: String): User
    fun findByEmailConfirmationTokenSafe(emailConfirmationToken: String) : User
    fun save(user: User)
}

@Service
class UserServiceImpl(
        @Autowired private val userRepository: UserRepository)
    : UserService {

    override fun findByIdSafe(id: UUID): User =
        this.userRepository.findById(id).orElseThrow{EntityNotFoundException("User not found.")}

    override fun findByEmailAddress(email: String): Optional<User> =
            this.userRepository.findByEmailAddress(email)

    override fun findByEmailAddressSafe(email: String): User =
        this.userRepository.findByEmailAddress(email).orElseThrow{EntityNotFoundException("User not found.")}

    override fun findByEmailConfirmationTokenSafe(emailConfirmationToken: String): User =
        this.userRepository.findByEmailConfirmationToken(emailConfirmationToken).orElseThrow{EntityNotFoundException("User not found.")}

    override fun save(user: User) {
        this.userRepository.save(user)
    }
}