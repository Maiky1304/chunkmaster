package com.github.maiky1304.chunkmaster.config.models

import org.bukkit.Chunk
import org.bukkit.Location
import org.bukkit.Particle

data class ChunkLoader(
    val location: Location
) {

    fun tick() {
        val world = location.world!!
        world.spawnParticle(Particle.VILLAGER_HAPPY, location.clone().add(0.5, 1.25, 0.5), 5)

        if (!isChunkLoaded()) {
            loadChunk()
        }

        println("[DEBUG] Chunk is loaded: " + isChunkLoaded())
    }

    fun chunk(): Chunk {
        return location.chunk
    }

    fun isChunkLoaded(): Boolean {
        return chunk().isLoaded
    }

    fun loadChunk() {
        chunk().load()
        chunk().isForceLoaded = true
    }

    fun unloadChunk() {
        chunk().unload()
        chunk().isForceLoaded = false
    }

    fun isChunkInUse(): Boolean {
        return chunk().isLoaded && !chunk().isForceLoaded
    }

}