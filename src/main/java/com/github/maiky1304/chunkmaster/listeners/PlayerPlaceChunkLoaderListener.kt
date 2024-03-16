package com.github.maiky1304.chunkmaster.listeners

import com.github.maiky1304.chunkmaster.ChunkMaster
import com.github.maiky1304.chunkmaster.config.manager.ChunkLoaderManager
import com.github.maiky1304.chunkmaster.util.ChunkLoaderUtil
import com.github.maiky1304.chunkmaster.util.MessageUtil
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.metadata.MetadataValue

class PlayerPlaceChunkLoaderListener(private val chunkLoaderManager: ChunkLoaderManager) : Listener {

    @EventHandler
    fun onPlaceChunkLoader(event: PlayerInteractEvent) {
        val player = event.player

        println(event.action)
        println(event.hasItem())

        if (event.action != Action.RIGHT_CLICK_BLOCK) {
            return
        }

        if (!event.hasItem()) {
            return
        }

        val item = event.item!!.clone()
        item.amount = 1
        val compareTo = ChunkLoaderUtil.getChunkLoaderItem().clone()
        compareTo.amount = 1

        if (item != compareTo) {
            return;
        }

        val block = event.clickedBlock!!.getRelative(event.blockFace)
        if (!chunkLoaderManager.addChunkLoader(block)) {
            event.isCancelled = true
            MessageUtil.sendMessage(player, "chunk-loader-already-placed")
            return
        }

        block.setMetadata("chunkmaster:loader", FixedMetadataValue(ChunkMaster.instance, true))
        MessageUtil.sendMessage(player, "activated-chunk-loader")
    }

}