package top.cubik65536

import de.comahe.i18n4k.config.I18n4kConfigDefault
import de.comahe.i18n4k.i18n4k
import io.github.kookybot.client.Client
import io.github.kookybot.events.channel.ChannelMessageEvent
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import top.cubik65536.entity.ConfigEntity
import top.cubik65536.plugins.*
import top.cubik65536.util.VersionUtil
import java.util.*

fun main() {
    val config = ConfigEntity().loadConfig()

    val i18n4kConfig = I18n4kConfigDefault()
    i18n4k = i18n4kConfig
    i18n4kConfig.locale = Locale(config.language)

    println("${I18N.starting}\n")
    println("${I18N.selectedLanguage} ${I18N.language}")

    val version = VersionUtil.getVersion()
    println("${I18N.version} $version")
    if (version.contains("dev") || version.contains("alpha") || version.contains("beta")) {
        println("${I18N.experimental}")
    } else if (version.contains("rc")) {
        println("${I18N.releaseCandidate}")
    }
    println()

    val kookToken = config.kook.token
    val kookClient = Client(kookToken) {
        withDefaultCommands()
    }
    CoroutineScope(Dispatchers.Default).launch {
        val self = kookClient.start()
    }

    embeddedServer(Netty, port = config.ktor.port, host = config.ktor.host) {
        configureRouting()
        configureSerialization()
    }.start(wait = true)
}
