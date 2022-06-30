package top.cubik65536

import de.comahe.i18n4k.config.I18n4kConfigDefault
import de.comahe.i18n4k.i18n4k
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import top.cubik65536.entity.ConfigEntity
import top.cubik65536.plugins.*
import java.util.*

fun main() {
    val config = ConfigEntity().loadConfig()

    val i18n4kConfig = I18n4kConfigDefault()
    i18n4k = i18n4kConfig
    i18n4kConfig.locale = Locale(config.language)

    println("${I18N.starting}\n")
    println("${I18N.selectedLanguage} ${I18N.language}")

    embeddedServer(Netty, port = config.ktor.port, host = config.ktor.host) {
        configureRouting()
        configureSerialization()
    }.start(wait = true)
}
