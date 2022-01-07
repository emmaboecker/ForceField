package net.stckoverflw.forcefield.listener

import net.axay.kspigot.event.listen
import net.stckoverflw.forcefield.config.getActive
import net.stckoverflw.forcefield.config.radius
import net.stckoverflw.forcefield.extension.pushAway
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerMoveEvent

fun forceFieldListener() = listen<PlayerMoveEvent> {
    it.player.getNearbyEntities(radius, radius, radius).forEach { entity ->
        if (entity is Player) {
            entity.pushAway(radius)
        }
    }
    if (it.player.getActive()) {
        it.player.pushAway(radius)
    }
}
