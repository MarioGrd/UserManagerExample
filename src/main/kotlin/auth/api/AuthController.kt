package auth.api

import auth.cqrs.user.ConfirmEmailCommand
import auth.cqrs.user.LoginCommand
import auth.cqrs.user.RegisterCommand
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import request.RequestBus

@RestController
@RequestMapping("/api/auth")
class AuthController(@Autowired private val requestBus: RequestBus) {

    @PostMapping("/register")
    fun register(@RequestBody command: RegisterCommand) = this.requestBus.sendRequest(command)

    @PostMapping("/login")
    fun login(@RequestBody command: LoginCommand) = this.requestBus.sendRequest(command)

    @PostMapping("/confirmEmail")
    fun confirmEmail(@RequestBody command: ConfirmEmailCommand) = this.requestBus.sendRequest(command)
}