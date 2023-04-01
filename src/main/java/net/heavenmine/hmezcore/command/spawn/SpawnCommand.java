package net.heavenmine.hmezcore.command.spawn;

import net.heavenmine.hmezcore.Helper;
import net.heavenmine.hmezcore.Main;
import net.heavenmine.hmezcore.file.ConfigFile;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class SpawnCommand implements CommandExecutor {
    private final Main main;
    private final ConfigFile configFile;

    public SpawnCommand(Main main, ConfigFile configFile) {
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
        if(warpsFile.get("spawn") == null) {
            sender.sendMessage(helper.Print( messageFile.getString("spawn-not-found"),true));
            return false;
        }
        String worldName = warpsFile.getString("spawn.world");
        World world = main.getServer().getWorld(worldName);
        Double x = warpsFile.getDouble("spawn.x");
        Double y = warpsFile.getDouble("spawn.y");
        Double z = warpsFile.getDouble("spawn.z");
        Float pitch = (float) warpsFile.getDouble("spawn.pitch");
        Float yaw = (float) warpsFile.getDouble("spawn.yaw");
        Location location = new Location(world, x, y, z, yaw, pitch);
        player.teleport(location);
        sender.sendMessage(helper.Print( messageFile.getString("teleported-to-spawn"),true));
        return true;
    }
}
