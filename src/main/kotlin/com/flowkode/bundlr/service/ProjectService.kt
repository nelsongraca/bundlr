package com.flowkode.bundlr.service

import com.flowkode.bundlr.model.*
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class ProjectService {
    fun getProject(projectCode: String): Uni<Project> {
        // TODO: fetch file and load it

        val m3x10 = Part(
            id = "m3x10",
            name = "SHCS M3 10mm",
        )
        val lidShort = Part(
            id = "short",
            name = "Short",
            file = "/Panels/Lid/Short/Blank/Lid - Short Blank.stl",
        )
        val lidLong = Part(
            id = "long",
            name = "Long",
            file = "Panels/Lid/Long/Blank/Lid - Long Blank.stl"
        )
        val mcuPico = Part(
            id = "mcu_skr_pico",
            name = "SKR Pico",
            file = "/Trays/MCU/BTT SKR Pico/MCU Tray - BTT SKR Pico - v2 HSI.stl",
            dependencies = listOf(
                Dependency(m3x10, 4)

            )
        )
        val mcuOctopus = Part(
            id = "mcu_btt_octopus",
            name = "BTT Octopus",
            file = "/Trays/MCU/BTT Octopus/MCU Tray - BTT Octopus - v2.stl",
            dependencies = listOf(
                Dependency(m3x10, 6)
            )
        )
        val sidePanelBlank = Part(
            id = "side_panel_blank",
            name = "Blank",
            file = "/Panels/Side Panel/Blank/Side Panel - Blank.stl",
            dependencies = listOf(
                Dependency(m3x10, 6)
            )
        )
        val fanCage5012 = Part(
            id = "fan_cage_5012",
            name = "50x12mm Fan Cage",
            file = "/Fans/50x12/5012 Fan Cage.stl",
        )
        val fanGasket50 = Part(
            id = "fan_gasket_50",
            name = "50mm Fan Gasket",
            file = "/Fans/50x12/5012 TPU Gasket.stl",
            optional = true,

        )
        val sidePanelFan5012 = Part(
            id = "side_panel_fan5012",
            name = "50x12mm Fan",
            file = "/Panels/Side Panel/50mm Fan/Side Panel - External 50mm Fan.stl1",
            dependencies = listOf(
                Dependency(fanCage5012),
                Dependency(fanGasket50)
            )
        )
        return Uni.createFrom().item(
            Project(
                Config(
                    id = "omnibox",
                    baseUrl = "https://raw.githubusercontent.com/jon-harper/OmniBox/main"
                ),
                setOf(
                    m3x10,
                    lidShort,
                    lidLong,
                    mcuPico,
                    mcuOctopus,
                    sidePanelBlank,
                    fanCage5012,
                    fanGasket50,
                    sidePanelFan5012
                ),
                listOf(
                    Component(
                        id = "lid",
                        name = "Lid",
                        parts = setOf(
                            lidShort,
                            lidLong
                        )
                    ),
                    Component(
                        id = "mcu_tray",
                        name = "MCU Tray",
                        parts = setOf(
                            mcuPico,
                            mcuOctopus
                        )
                    ),
                    Component(
                        id = "side_panel_1",
                        name = "Side Panel 1",
                        parts = setOf(
                            sidePanelBlank,
                            sidePanelFan5012
                        )
                    )
                ),
            )
        )
    }
}
