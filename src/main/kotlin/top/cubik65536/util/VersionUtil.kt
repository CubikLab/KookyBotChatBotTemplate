package top.cubik65536.util

import java.io.InputStream
import java.util.*

class VersionUtil {
    companion object {
        private fun getVersionProperties(): Properties {
            val inputStream: InputStream? = this::class.java.getResourceAsStream("/version.properties")
            val properties = Properties()
            properties.load(inputStream)
            return properties
        }

        fun getVersionProperty(): String {
            return getVersionProperties().getProperty("version")
        }

        fun getStageProperty(): String {
            return getVersionProperties().getProperty("stage")
        }

        fun getRevisionProperty(): String {
            return getVersionProperties().getProperty("revision")
        }

        fun getVersion(): String {
            val versionProperty = getVersionProperty()
            var stageProperty = getStageProperty()
            val revisionProperty = getRevisionProperty().uppercase()
            stageProperty = stageProperty.replace(regex = Regex("dev"), replacement = "DEV")
            stageProperty = stageProperty.replace(regex = Regex("alpha\\."), replacement = "Alpha ")
            stageProperty = stageProperty.replace(regex = Regex("alpha"), replacement = "Alpha")
            stageProperty = stageProperty.replace(regex = Regex("beta\\."), replacement = "Beta ")
            stageProperty = stageProperty.replace(regex = Regex("beta"), replacement = "Beta")
            stageProperty = stageProperty.replace(regex = Regex("rc\\."), replacement = "Release Candidate ")
            stageProperty = stageProperty.replace(regex = Regex("rc"), replacement = "Release Candidate")
            return "$versionProperty $stageProperty ($revisionProperty)"
        }
    }
}
