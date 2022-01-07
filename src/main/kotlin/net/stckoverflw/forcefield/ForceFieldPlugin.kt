package net.stckoverflw.forcefield

import net.axay.kspigot.chat.KColors
import net.axay.kspigot.main.KSpigot
import net.stckoverflw.forcefield.command.forceFieldCommand
import net.stckoverflw.forcefield.command.radiusCommand
import net.stckoverflw.forcefield.command.wandCommand
import net.stckoverflw.forcefield.config.ForceFieldConfig
import net.stckoverflw.forcefield.listener.forceFieldListener
import net.stckoverflw.forcefield.listener.interactListener

val config by lazy { ForceFieldConfig() }

class ForceFieldPlugin : KSpigot() {

    override fun startup() {
        forceFieldCommand()
        radiusCommand()
        wandCommand()

        forceFieldListener()
        interactListener()
    }

    override fun shutdown() {
        logger.info("${KColors.RED}The Plugin was disabled!")
    }
}
