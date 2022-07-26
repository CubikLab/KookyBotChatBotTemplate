package top.cubik65536

import de.comahe.i18n4k.config.I18n4kConfigDefault
import de.comahe.i18n4k.i18n4k
import io.github.kookybot.client.Client
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import top.cubik65536.controller.StatusController
import top.cubik65536.entity.ConfigEntity
import top.cubik65536.plugins.configureRouting
import top.cubik65536.plugins.configureSerialization
import top.cubik65536.util.VersionUtil
import java.util.*


fun main() {
    val config = ConfigEntity().loadConfig()

    val i18n4kConfig = I18n4kConfigDefault()
    i18n4k = i18n4kConfig
    i18n4kConfig.locale = Locale(config.language)

    println("${I18N.starting}\n")
    println("${I18N.selectedLanguage} ${I18N.language}")

    println("${I18N.version} ${VersionUtil.getVersion()}")
    val stage = VersionUtil.getProperty("stage")
    if (stage.contains("dev") || stage.contains("alpha") || stage.contains("beta")) {
        println("${I18N.experimental}")
    } else if (stage.contains("rc")) {
        println("${I18N.releaseCandidate}")
    }
    println()

    val kookToken = config.kook.token
    val kookClient = Client(kookToken) {
        withDefaultCommands()
    }
    CoroutineScope(Dispatchers.Default).launch {
        val self = kookClient.start()
        kookClient.eventManager.addClassListener(StatusController())
    }

    embeddedServer(Netty, port = config.ktor.port, host = config.ktor.host) {
        configureRouting()
        configureSerialization()
    }.start(wait = true)
}
