package com.github.maiky1304.chunkmaster.util

import com.github.justadeni.HexColorLib.color
import com.github.maiky1304.chunkmaster.ChunkMaster
import com.github.maiky1304.chunkmaster.config.Config
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object ChunkLoaderUtil {

    private fun config(): Config {
        return ChunkMaster.instance.configuration
    }

    fun getChunkLoaderItem(): ItemStack {
        val itemConfig = config().getConfigurationSection("item") ?: throw IllegalStateException("Item section not found in config.yml")

        val material = Material.valueOf(itemConfig.getString("type") ?: throw IllegalStateException("Material not found in item section"))
        val name = itemConfig.getString("name") ?: throw IllegalStateException("Name not found in item section")
        val lore = itemConfig.getStringList("lore")

        val item = ItemStack(material)
        val meta = item.itemMeta ?: throw IllegalStateException("ItemMeta is null")

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name.color()))
        meta.lore = lore.map { ChatColor.translateAlternateColorCodes('&', it.color()) }
        item.itemMeta = meta

        return item
    }

}