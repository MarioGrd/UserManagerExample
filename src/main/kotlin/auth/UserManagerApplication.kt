package auth

import auth.cqrs.core.PostRequestLogging
import auth.cqrs.core.PreRequestLogging
import auth.cqrs.core.PreRequestValidation
import auth.infrastructure.core.EmailProvider
import auth.infrastructure.core.EmailProviderMock
import auth.infrastructure.core.PasswordProvider
import auth.infrastructure.core.PasswordProviderMock
import com.grd.request.*
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import request.PostRequestHandler
import request.PreRequestHandler
import request.RequestBus
import java.util.logging.Logger


@SpringBootApplication
@Configuration
@ComponentScan("auth")
class UserManagerApplication : ApplicationContextAware {

    private lateinit var ctx: ApplicationContext

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        this.ctx = applicationContext
    }

    @Bean
    fun logger()= Logger.getGlobal()

    @Bean
    fun passwordProvider() = PasswordProviderMock() as PasswordProvider

    @Bean
    fun emailProvider() = EmailProviderMock() as EmailProvider

    @Bean
    @SuppressWarnings("unchecked")
    fun requestBus(context: ApplicationContext): RequestBus {

        return SimpleRequestBus { name -> context.getBean(name) as RequestHandler<Request<Any>, Any> }
                .setPreRequestHandler(context.getBean(PreRequestLogging::class.java) as PreRequestHandler<Request<*>, *>)
                .setPreRequestHandler(context.getBean(PreRequestValidation::class.java) as PreRequestHandler<Request<*>, *>)
                .setPostRequestHandler(context.getBean(PostRequestLogging::class.java) as PostRequestHandler<Request<*>, *>)
    }
}

fun main(args: Array<String>) {
    runApplication<UserManagerApplication>(*args)
}










