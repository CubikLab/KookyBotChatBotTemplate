package top.cubik65536.entity

import java.io.File

import com.sksamuel.hoplite.ConfigLoader
import top.cubik65536.I18N
import tech.ixor.utils.FileDownloader

class ConfigEntity {
    data class Ktor(val host: String, val port: Int)

    data class Config(
        val language: String, val ktor: Ktor
    )

    fun loadConfig(): Config {
        val pwd = System.getProperty("user.dir")
        val confFile = "$pwd/conf/config.yaml"
        if (!File(confFile).exists()) {
            println(I18N.configFileNotFound)
            val fileDownloader = FileDownloader()
            fileDownloader.downloadFile(
                "https://cdn.jsdelivr.net/gh/CubikLab/KookyBotChatBotTemplate/src/main/resources/conf/config.yaml",
                confFile,
                I18N.configFileDownloadDescription()
            )
        }
        return ConfigLoader().loadConfigOrThrow(confFile)
    }
}
