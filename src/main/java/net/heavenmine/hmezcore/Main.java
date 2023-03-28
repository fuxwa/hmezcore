package net.heavenmine.hmezcore;

import net.heavenmine.hmezcore.data.DataManager;
import net.heavenmine.hmezcore.event.PlayerJoinServer;
import net.heavenmine.hmezcore.file.ConfigFile;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    private ConfigFile configFile = new ConfigFile(this);
    private DataManager dataManager = new DataManager(this, configFile);

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        reloadConfig();
        getServer().getPluginManager().registerEvents(new PlayerJoinServer(), this);

        dataManager.onLoad();
//        getLogger().info(config.getVersion());


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
