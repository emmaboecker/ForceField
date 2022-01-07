package net.stckoverflw.forcefield.listener

import net.axay.kspigot.event.listen
import net.axay.kspigot.extensions.geometry.minus
import net.stckoverflw.forcefield.extension.forceWand
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.util.Vector

fun interactListener() = listen<PlayerInteractEvent> { event ->
    if (!event.action.isRightClick ||
        event.item == null ||
        !event.item!!.isSimilar(forceWand)
    ) {
        return@listen
    }
    val baseVector = event.player.eyeLocation
    event.player.getNearbyEntities(50.0, 50.0, 50.0)
        .filterIsInstance<Player>()
        .filter {
            it.eyeLocation.toVector()
                .add(Vector(0.0, -0.5, 0.0))
                .subtract(baseVector.toVector())
                .normalize()
                .dot(baseVector.direction) > 0.95
        }
        .forEach { player ->
            player.velocity = player.eyeLocation.toVector().minus(event.player.eyeLocation.toVector()).normalize()
                .add(Vector(0.0, 0.5, 0.0))
        }
}
