package net.heavenmine.hmezcore;

import net.heavenmine.hmezcore.command.*;
import net.heavenmine.hmezcore.command.spawn.SetSpawnCommand;
import net.heavenmine.hmezcore.command.spawn.SpawnCommand;
import net.heavenmine.hmezcore.command.warps.DelWarpCommand;
import net.heavenmine.hmezcore.command.warps.SetWarpCommand;
import net.heavenmine.hmezcore.command.warps.WarpsCommand;
import net.heavenmine.hmezcore.data.DataManager;
import net.heavenmine.hmezcore.event.PlayerJoinServer;
import net.heavenmine.hmezcore.event.PlayerLeaveServer;
import net.heavenmine.hmezcore.event.PlayerTeleport;
import net.heavenmine.hmezcore.file.ConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
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
        createWarpsFile();
        dataManager.onLoad();
        getServer().getWorld("world").setGameRuleValue("spawnRadius", "0");
//        try {
//            saveDefaultSpawn();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        PlayerTeleport playerTeleport = new PlayerTeleport();
        getServer().getPluginManager().registerEvents(new PlayerJoinServer(this, configFile, dataManager), this);
        getServer().getPluginManager().registerEvents(new PlayerLeaveServer(this, configFile, dataManager), this);
        getServer().getPluginManager().registerEvents(playerTeleport,this);

        getCommand("gm").setExecutor(new GamemodeCommand(this, configFile));
        getCommand("broadcast").setExecutor(new BroadcastCommand(this, configFile));
        getCommand("feed").setExecutor(new FeedCommand(this, configFile));
        getCommand("heal").setExecutor(new HealCommand(this, configFile));
        getCommand("ec").setExecutor(new EnderChestCommand(this, configFile));
        getCommand("inventory").setExecutor(new InventoryCommand(this, configFile));
        getCommand("god").setExecutor(new GodCommand(this, configFile));
        getCommand("home").setExecutor(new HomeCommand(this, configFile));
        getCommand("warps").setExecutor(new WarpsCommand(this, configFile));
        getCommand("setwarp").setExecutor(new SetWarpCommand(this, configFile));
        getCommand("delwarp").setExecutor(new DelWarpCommand(this, configFile));
        getCommand("spawn").setExecutor(new SpawnCommand(this, configFile));
        getCommand("setspawn").setExecutor(new SetSpawnCommand(this, configFile));
        getCommand("back").setExecutor(new BackCommand(this, configFile, playerTeleport));
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
    public void createWarpsFile() {
        File itemsFile = new File(getDataFolder(), "warps.yml");
        if (!itemsFile.exists()) {
            itemsFile.getParentFile().mkdirs();
            try (InputStream in = getResource("warps.yml")) {
                Files.copy(in, itemsFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void saveDefaultSpawn() throws IOException {
        World world = Bukkit.getWorld("world");
        String worldName = world.getName();
        Double x = world.getSpawnLocation().getX();
        Double y = world.getSpawnLocation().getY() + 2;
        Double z = world.getSpawnLocation().getZ();
        Float pitch = world.getSpawnLocation().getPitch();
        Float yaw = world.getSpawnLocation().getYaw();
        FileConfiguration warps = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "warps.yml"));
        if(warps.get("spawn") == null) {
            warps.set("spawn.world",worldName);
            warps.set("spawn.x",x);
            warps.set("spawn.y",y);
            warps.set("spawn.z",z);
            warps.set("spawn.pitch",pitch);
            warps.set("spawn.yaw",yaw);
            warps.save(new File(getDataFolder(), "warps.yml"));
        }
    }

}
