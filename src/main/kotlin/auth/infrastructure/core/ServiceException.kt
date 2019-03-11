package auth.infrastructure.core

data class EntityNotFoundException(val msg: String) : Exception()