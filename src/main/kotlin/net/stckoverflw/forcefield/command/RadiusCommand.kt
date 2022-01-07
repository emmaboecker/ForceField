package net.stckoverflw.forcefield.command

import com.mojang.brigadier.arguments.DoubleArgumentType
import net.axay.kspigot.commands.argument
import net.axay.kspigot.commands.command
import net.axay.kspigot.commands.register
import net.axay.kspigot.commands.requiresPermission
import net.minecraft.network.chat.TextComponent
import net.stckoverflw.forcefield.config.radius

fun radiusCommand() =  command("forcefield-radius", true) {
    requiresPermission("forcefield.radius")
    argument("radius", DoubleArgumentType.doubleArg()) {
        executes {
            radius = DoubleArgumentType.getDouble(it, "radius")
            it.source.sendSuccess(TextComponent("Radius was set to $radius"), true)
            return@executes 0
        }
    }
}.register()
