package net.heavenmine.hmezcore.command.spawn;

import net.heavenmine.hmezcore.Helper;
import net.heavenmine.hmezcore.Main;
import net.heavenmine.hmezcore.file.ConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class SetSpawnCommand implements CommandExecutor {
    private final Main main;
    private final ConfigFile configFile;

    public SetSpawnCommand(Main main, ConfigFile configFile) {
        this.main = main;
        this.configFile = configFile;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Helper helper = new Helper(configFile);
        YamlConfiguration messageFile = YamlConfiguration.loadConfiguration(new File(main.getDataFolder(), "message.yml"));
        YamlConfiguration warpsFile = YamlConfiguration.loadConfiguration(new File(main.getDataFolder(), "warps.yml"));
        if(!(sender instanceof Player)) {
            sender.sendMessage(messageFile.getString("player-only"));
            return false;
        }
        Player player = (Player) sender;
        if(!player.hasPermission("hmezcore.command.setspawn")) {
            sender.sendMessage(helper.Print( messageFile.getString("no-permission"),true));
            return false;
        }
        String worldName = player.getLocation().getWorld().getName();
        World world = Bukkit.getWorlds().get(0);
        Double x = player.getLocation().getX();
        Double y = player.getLocation().getY();
        Double z = player.getLocation().getZ();
        Float pitch = player.getLocation().getPitch();
        Float yaw = player.getLocation().getYaw();
        warpsFile.set("spawn" + ".world", worldName);
        warpsFile.set("spawn" + ".x", x);
        warpsFile.set("spawn" + ".y", y);
        warpsFile.set("spawn" + ".z", z);
        warpsFile.set("spawn" + ".pitch", pitch);
        warpsFile.set("spawn" + ".yaw", yaw);
        Location location = new Location(world, x, y, z, yaw, pitch);
        try {
            warpsFile.save(new File(main.getDataFolder(), "warps.yml"));
            world.setSpawnLocation(location);
            sender.sendMessage(helper.Print( messageFile.getString("set-spawn-complete"),true));
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
