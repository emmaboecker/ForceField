package net.stckoverflw.forcefield.config

import net.axay.kspigot.main.KSpigotMainInstance
import net.stckoverflw.forcefield.config
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import java.io.File
import java.io.IOException

abstract class AbstractConfig(name: String) {

    private val file: File
    private val dir: File = File(KSpigotMainInstance.dataFolder.path)
    val yaml: YamlConfiguration

    init {
        if (!dir.exists()) {
            dir.mkdirs()
        }
        file = File(dir, name)
        if (!file.exists()) {
            KSpigotMainInstance.saveResource(name, false)
        }
        yaml = YamlConfiguration()
        try {
            yaml.load(file)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun save() {
        try {
            yaml.save(file)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}

class ForceFieldConfig : AbstractConfig("forcefield.yml")

fun Player.setActive(active: Boolean) {
    config.yaml.set("enabled." + this.uniqueId.toString(), active)
    config.save()
}

fun Player.getActive() = config.yaml.getBoolean("enabled." + this.uniqueId.toString(), false)

var radius: Double
    get() = config.yaml.getDouble("radius", 5.0)
    set(value) = config.yaml.set("radius", value)
