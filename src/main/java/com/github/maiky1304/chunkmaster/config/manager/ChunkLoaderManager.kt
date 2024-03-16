package com.github.maiky1304.chunkmaster.config.manager

import com.github.maiky1304.chunkmaster.ChunkMaster
import com.github.maiky1304.chunkmaster.config.Config
import com.github.maiky1304.chunkmaster.config.models.ChunkLoader
import org.bukkit.Location
import org.bukkit.block.Block

class ChunkLoaderManager(private val plugin: ChunkMaster, private val config: Config) {

    val chunkLoaders = mutableMapOf<Location, ChunkLoader>()

    fun load() {
        // Load chunk loaders
        var worlds = 0

        for (worldName in config.getKeys(false)) {
            worlds++
            for (chunkName in config.getConfigurationSection(worldName)!!.getKeys(false)) {
                val chunkLocation = config.getLocation("$worldName.$chunkName") ?: continue
                chunkLoaders[chunkLocation] = ChunkLoader(chunkLocation)
            }
        }
        plugin.logger.info("Loaded ${chunkLoaders.size} chunk loaders in $worlds world${if (worlds == 1) "" else "s"}.")

        // Setup runnable
        plugin.server.scheduler.runTaskTimer(plugin, Runnable {
            chunkLoaders.forEach { (_, chunkLoader) ->
                chunkLoader.tick()
            }
        }, 0, 1)
    }

    fun addChunkLoader(block: Block): Boolean {
        val location = block.location

        if (chunkLoaders.containsKey(location)) {
            return false
        }

        val world = location.world

        val chunkName = "${location.chunk.x}:${location.chunk.z}"

        config.set("${world!!.name}.$chunkName", location)
        config.save()

        chunkLoaders[location] = ChunkLoader(location)
        return true
    }

    fun getChunkLoader(location: Location): ChunkLoader? {
        if (chunkLoaders.containsKey(location)) {
            return chunkLoaders[location]
        }

        val world = location.world
        val chunkName = "${location.chunk.x}:${location.chunk.z}"

        val chunkLocation = config.getLocation("${world!!.name}.$chunkName") ?: return null
        return ChunkLoader(chunkLocation)
    }

    fun removeChunkLoader(block: Block) {
        val location = block.location
        val world = location.world

        val chunkName = "${location.chunk.x}:${location.chunk.z}"

        config.set("${world!!.name}.$chunkName", null)
        config.save()

        if (chunkLoaders.containsKey(location)) {
            chunkLoaders.remove(location)
        }
    }

}