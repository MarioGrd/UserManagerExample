package auth.cqrs.core

import com.grd.request.Request
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import request.PostRequestHandler
import request.PreRequestHandler
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger

@Component
@Scope("prototype")
class PreRequestLogging(
        @Autowired private val logger: Logger)
    : PreRequestHandler<Request<Any>, Any> {

    override fun handle(request: Request<Any>) {
        this.logger.log(Level.INFO, "Request with name: '${request::class.java.simpleName}' started at '${Date()}'")
    }

}

@Component
@Scope("prototype")
class PostRequestLogging (
        @Autowired private val logger: Logger)
    : PostRequestHandler<Request<Any>, Any> {

    override fun handle(request: Request<Any>) {
        this.logger.log(Level.INFO, "Request with name: '${request::class.java.simpleName}' ended at '${Date()}'")
    }

}