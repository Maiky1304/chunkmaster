package com.github.maiky1304.chunkmaster

import com.github.maiky1304.chunkmaster.config.Config
import com.github.maiky1304.chunkmaster.config.manager.ChunkLoaderManager
import com.github.maiky1304.chunkmaster.listeners.PlayerPlaceChunkLoaderListener
import com.github.maiky1304.chunkmaster.util.ChunkLoaderUtil
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.plugin.java.JavaPlugin

class ChunkMaster : JavaPlugin() {

    companion object {

        lateinit var instance: ChunkMaster
            private set

    }

    lateinit var adventure: BukkitAudiences

    lateinit var configuration: Config
    lateinit var chunkLoaders: Config
    lateinit var messages: Config

    lateinit var chunkLoaderManager: ChunkLoaderManager

    override fun onEnable() {
        instance = this

        this.adventure = BukkitAudiences.create(this)

        this.configuration = Config(this, "config.yml")
        this.configuration.load()

        this.chunkLoaders = Config(this, "chunk-loaders.yml")
        this.chunkLoaders.load()

        this.messages = Config(this, "messages.yml")
        this.messages.load()

        this.addRecipes()

        this.chunkLoaderManager = ChunkLoaderManager(this, this.chunkLoaders)
        this.chunkLoaderManager.load()

        this.server.pluginManager.registerEvents(PlayerPlaceChunkLoaderListener(this.chunkLoaderManager), this)
    }

    private fun addRecipes() {
        val chunkLoaderItem = ChunkLoaderUtil.getChunkLoaderItem()

        val namespacedKey = NamespacedKey(this, "chunk_loader")
        val shapedRecipe = ShapedRecipe(namespacedKey, chunkLoaderItem)
        shapedRecipe.shape(
            *configuration.getStringList("recipe.pattern").toTypedArray()
        )
        for ((key, value) in configuration.getConfigurationSection("recipe.materials")!!.getValues(false)) {
            shapedRecipe.setIngredient(key[0], Material.valueOf(value as String))
        }

        try {
            Bukkit.addRecipe(shapedRecipe)
            logger.info("Added chunk loader recipe!")
        } catch (e: IllegalStateException) {
            logger.info("Recipe already exists! Skipping...")
        }
    }

}
