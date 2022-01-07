package net.stckoverflw.forcefield.command

import net.axay.kspigot.commands.command
import net.axay.kspigot.commands.register
import net.axay.kspigot.commands.requiresPermission
import net.minecraft.network.chat.TextComponent
import net.stckoverflw.forcefield.extension.giveWand
import org.bukkit.entity.Player

fun wandCommand() = command("forcefield-wand", true) {
    requiresPermission("forcefield.wand")
    executes {
        if (it.source.bukkitSender is Player) {
            if ((it.source.bukkitSender as Player).giveWand()) {
                it.source.sendSuccess(TextComponent("Gave you Force-Field Wand"), false)
            } else {
                it.source.sendFailure(TextComponent("Your inventory is full."))
            }
        }
        return@executes 0
    }
}.register()
