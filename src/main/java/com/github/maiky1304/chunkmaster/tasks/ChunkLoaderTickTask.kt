package com.github.maiky1304.chunkmaster.tasks

import com.github.maiky1304.chunkmaster.config.manager.ChunkLoaderManager

class ChunkLoaderTickTask(private val chunkLoaderManager: ChunkLoaderManager) : Runnable {

    override fun run() {
        chunkLoaderManager.chunkLoaders.forEach { (_, chunkLoader) ->
            chunkLoader.tick()
        }
    }

}