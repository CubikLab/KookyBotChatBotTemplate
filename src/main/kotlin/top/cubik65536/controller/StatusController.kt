package top.cubik65536.controller

import io.github.kookybot.annotation.Filter
import io.github.kookybot.commands.CommandSource
import io.github.kookybot.events.EventHandler
import io.github.kookybot.events.Listener
import top.cubik65536.I18N
import top.cubik65536.util.VersionUtil

@Suppress("unused")
class StatusController: Listener {
    @EventHandler
    @Filter("!!ping")
    fun ping(source: CommandSource) {
        source.sendMessage("pong")
    }

    @EventHandler
    @Filter("!!status")
    fun status(source: CommandSource) {
        var msg = "${I18N.welcome} \n" +
                "-------------------------------- \n" +
                "${I18N.version} ${VersionUtil.getVersion()} \n"

        val stage = VersionUtil.getStageProperty()
        if (stage.contains("dev") || stage.contains("alpha") || stage.contains("beta")) {
            msg += "${I18N.experimental} \n"
        } else if (stage.contains("rc")) {
            msg += "${I18N.releaseCandidate} \n"
        }

        msg += "-------------------------------- \n" +
                "${I18N.poweredBy} Cubik65536 & CubikTech ${I18N.withLove}"

        source.sendMessage(msg)

    }

}
