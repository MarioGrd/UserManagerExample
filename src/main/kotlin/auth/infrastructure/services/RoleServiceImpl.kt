package auth.infrastructure.services

import auth.domain.user.Role
import auth.infrastructure.repositories.RoleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

interface RoleService {
    fun findByIdSafe(id: Int) : Role
}

@Service
class RoleServiceImpl(@Autowired private val roleRepository: RoleRepository) : RoleService {

    override fun findByIdSafe(id: Int): Role =
        this.roleRepository.findById(id).orElseThrow { EntityNotFoundException("Role not found.")}

}