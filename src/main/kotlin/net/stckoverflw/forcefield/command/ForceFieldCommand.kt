package net.stckoverflw.forcefield.command

import net.axay.kspigot.commands.argument
import net.axay.kspigot.commands.command
import net.axay.kspigot.commands.register
import net.axay.kspigot.commands.requiresPermission
import net.minecraft.commands.arguments.GameProfileArgument
import net.stckoverflw.forcefield.config.getActive
import net.stckoverflw.forcefield.config.setActive
import org.bukkit.Bukkit

fun forceFieldCommand() = command("forcefield", true) {
    requiresPermission("forcefield.activate")
    argument("player", GameProfileArgument.gameProfile()) {
        executes {
            val gameProfile = GameProfileArgument.getGameProfiles(it, "player").first()
            val target = Bukkit.getPlayer(gameProfile.id) ?: return@executes 1

            target.setActive(!target.getActive())

            target.sendMessage("Your ForceField is now ".plus(if (target.getActive()) "active" else "inactive"))

            return@executes 0
        }
    }
}.register()
