package auth.infrastructure.core

interface PasswordProvider {
    fun validate(password: String, guess: String): Boolean
    fun hashPassword(password: String) : String
}

class PasswordProviderMock : PasswordProvider {
    override fun validate(password: String, guess: String): Boolean = password == guess
    override fun hashPassword(password: String): String  = password
}