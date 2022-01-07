package net.stckoverflw.forcefield.extension

import net.axay.kspigot.chat.KColors
import net.axay.kspigot.extensions.geometry.minus
import net.axay.kspigot.items.addLore
import net.axay.kspigot.items.itemStack
import net.axay.kspigot.items.meta
import net.axay.kspigot.items.name
import net.stckoverflw.forcefield.config.getActive
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import org.bukkit.util.Vector
import kotlin.math.pow

fun Player.pushAway(radius: Double) {
    if (getActive()) {
        getNearbyEntities(radius, radius, radius).forEach { entity ->
            if (entity is Player) {
                entity.velocity = entity.eyeLocation.toVector().minus(eyeLocation.toVector()).normalize()
                    .multiply((entity.eyeLocation.toVector().distance(eyeLocation.toVector()) - radius)
                        .pow(2).pow(1/2))
                    .add(Vector(0.0, 0.1, 0.0))
            }
        }
    }
}

val forceWand = itemStack(Material.BLAZE_ROD) {
    meta {
        name = "${KColors.DARKORANGE}Force Wand"
        addLore {
            + " "
            + "§7Right click this wand to create a Force Field"
            + "§7in-front of you and push players away"
        }
        addEnchant(Enchantment.DURABILITY, 1, false)
        addItemFlags(ItemFlag.HIDE_ENCHANTS)
    }
}

fun Player.giveWand() = inventory.addItem(forceWand).isEmpty()
