package auth.infrastructure.core

import org.springframework.stereotype.Service

interface EmailProvider {
    fun sendEmail(mail: String, content: String)
}

@Service
class EmailProviderMock : EmailProvider {
    override fun sendEmail(mail: String, content: String) {
        System.out.println("Sending mail to '$mail'")
    }
}