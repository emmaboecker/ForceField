package net.stckoverflw.forcefield

import com.mojang.brigadier.arguments.DoubleArgumentType
import com.mojang.brigadier.arguments.FloatArgumentType
import com.mojang.brigadier.arguments.LongArgumentType
import net.axay.kspigot.chat.KColors
import net.axay.kspigot.commands.argument
import net.axay.kspigot.commands.command
import net.axay.kspigot.commands.register
import net.axay.kspigot.commands.requiresPermission
import net.axay.kspigot.event.listen
import net.axay.kspigot.extensions.broadcast
import net.axay.kspigot.extensions.geometry.minus
import net.axay.kspigot.main.KSpigot
import net.kyori.adventure.text.Component
import net.minecraft.commands.arguments.GameProfileArgument
import net.minecraft.network.chat.TextComponent
import net.stckoverflw.forcefield.config.ForceFieldConfig
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.util.Vector
import kotlin.math.pow

class ForceFieldPlugin : KSpigot() {

    lateinit var config: ForceFieldConfig

    override fun startup() {
        config = ForceFieldConfig()

        command("forcefield-radius", true) {
            requiresPermission("forcefield.radius")
            argument("radius", DoubleArgumentType.doubleArg()) {
                executes {
                    radius = DoubleArgumentType.getDouble(it, "radius")
                    it.source.sendSuccess(TextComponent("Radius was set to $radius"), true)
                    return@executes 0
                }
            }
        }.register()

        command("forcefield", true) {
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

        listen<PlayerMoveEvent> {
            it.player.getNearbyEntities(radius, radius, radius).forEach { entity ->
                if (entity is Player) {
                    entity.pushAway()
                }
            }
            if (it.player.getActive()) {
                it.player.pushAway()
            }
        }
    }

    private fun Player.pushAway() {
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

    override fun shutdown() {
        logger.info("${KColors.RED}The Plugin was disabled!")
    }

    private fun Player.setActive(actice: Boolean) {
        config.yaml.set("enabled." + this.uniqueId.toString(), actice)
        config.save()
    }

    private fun Player.getActive() = config.yaml.getBoolean("enabled." + this.uniqueId.toString(), false)

    private var radius: Double
    get() = config.yaml.getDouble("radius", 5.0)
    set(value) = config.yaml.set("radius", value)
}
