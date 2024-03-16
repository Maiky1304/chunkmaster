package com.github.maiky1304.chunkmaster.util

import com.github.maiky1304.chunkmaster.ChunkMaster
import com.github.maiky1304.chunkmaster.config.Config
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.entity.Player

object MessageUtil {

    fun messages(): Config {
        return ChunkMaster.instance.messages
    }

    fun sendMessage(player: Player, key: String) {
        val prefix = messages().getString("prefix") ?: "No prefix found"
        val message = messages().getString(key) ?: "No message found for key: $key"

        val miniMessage = MiniMessage.miniMessage()
        val target = ChunkMaster.instance.adventure.player(player)
        target.sendMessage(miniMessage.deserialize(prefix).append(miniMessage.deserialize(message)))
    }

}