package top.cubik65536.routes.web

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.http.*
import io.ktor.server.routing.*
import kotlinx.html.*
import top.cubik65536.I18N
import top.cubik65536.util.VersionUtil

fun Route.index() {
    get("/") {
        call.respondHtml(HttpStatusCode.OK) {
            head {
                title { +"${I18N.welcome}" }
                meta { charset = "utf-8" }
                link( rel = "stylesheet", href = "/assets/style.css", type = "text/css")
                meta { name = "viewport"; content = "width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" }
                script { src = "https://unpkg.com/feather-icons" }
            }
            body {
                div { id = "page-wrapper"
                    div ("index-container") {
                        div ("content") {
                            h1 { +"${I18N.welcome}" }

                            hr {}

                            p {
                                b { +"${I18N.version} " }
                                +VersionUtil.getVersion()
                            }
                            val stage = VersionUtil.getStageProperty()
                            if (stage.contains("dev") || stage.contains("alpha") || stage.contains("beta")) {
                                p { b { +"${I18N.experimental}" } }
                            } else if (stage.contains("rc")) {
                                p { b { +"${I18N.releaseCandidate}" } }
                            }

                            hr {}
                            br {}

                            p {
                                +"${I18N.successfullyInstalled}"
                            }

                            br {}

                            footer {
                                hr {}
                                a (href = "https://github.com/CubikTech/KookyBotChatBotTemplate/issues") {
                                    +"${I18N.reportBug}"
                                }
                                hr {}
                                a (href = "https://github.com/CubikTech") {
                                    i { attributes["data-feather"] = "github" }
                                }
                                +" | ${I18N.poweredBy} "
                                a (href = "https://cubik65536.top") { +"Cubik65536 & CubikTech" }
                                +" ${I18N.withLove}"
                                br {}
                                + "${I18N.htmlThemeDesigned0}"
                                a (href = "https://github.com/athul/archie") { +"Archie Theme" }
                                + "${I18N.htmlThemeDesigned1}"
                                a (href = "https://github.com/KevinZonda") { +"@KevinZonda" }
                                + "${I18N.htmlThemeDesigned2}"
                            }
                        }
                    }
                }

                script { +"feather.replace()" }

            }
        }
    }
}
