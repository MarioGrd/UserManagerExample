package auth.infrastructure.repositories

import auth.domain.role.Role
import org.springframework.data.repository.CrudRepository

interface RoleRepository: CrudRepository<Role, Int>
