package com.github.maiky1304.chunkmaster.config

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class Config(private val plugin: JavaPlugin, private val fileName: String) : YamlConfiguration() {

    private val file: File = File(plugin.dataFolder, fileName)

    fun load() {
        if (!plugin.dataFolder.exists()) {
            plugin.dataFolder.mkdirs()
        }

        if (!file.exists()) {
            plugin.saveResource(fileName, false)
        }
        load(file)
    }

    fun save() {
        save(file)
    }

}