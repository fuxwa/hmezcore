package net.heavenmine.hmezcore;

import net.heavenmine.hmezcore.command.BroadcastCommand;
import net.heavenmine.hmezcore.command.FeedCommand;
import net.heavenmine.hmezcore.command.GamemodeCommand;
import net.heavenmine.hmezcore.data.DataManager;
import net.heavenmine.hmezcore.event.PlayerJoinServer;
import net.heavenmine.hmezcore.event.PlayerLeaveServer;
import net.heavenmine.hmezcore.file.ConfigFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public final class Main extends JavaPlugin {
    private final ConfigFile configFile = new ConfigFile(this);
    private final DataManager dataManager = new DataManager(this, configFile);

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        reloadConfig();
        createMessageFile();
        getLogger().info(getDataFolder().toString());
        dataManager.onLoad();
        getServer().getPluginManager().registerEvents(new PlayerJoinServer(this, configFile, dataManager), this);
        getServer().getPluginManager().registerEvents(new PlayerLeaveServer(this, configFile, dataManager), this);

        getCommand("gm").setExecutor(new GamemodeCommand(this, configFile));
        getCommand("broadcast").setExecutor(new BroadcastCommand(this, configFile));
        getCommand("feed").setExecutor(new FeedCommand(this, configFile));
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public void createMessageFile() {
        File itemsFile = new File(getDataFolder(), "message.yml");
        if (!itemsFile.exists()) {
            itemsFile.getParentFile().mkdirs();
            try (InputStream in = getResource("message.yml")) {
                Files.copy(in, itemsFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
