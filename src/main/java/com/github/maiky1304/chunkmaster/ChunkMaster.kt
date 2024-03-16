package com.github.maiky1304.chunkmaster

import com.github.maiky1304.chunkmaster.config.Config
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import org.bukkit.plugin.java.JavaPlugin

class ChunkMaster : JavaPlugin() {

    private lateinit var adventure: BukkitAudiences

    private lateinit var configuration: Config
    private lateinit var data: Config

    override fun onEnable() {
        this.adventure = BukkitAudiences.create(this)
    }

}
