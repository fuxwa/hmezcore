package net.heavenmine.hmezcore.command.warps;

import net.heavenmine.hmezcore.Helper;
import net.heavenmine.hmezcore.Main;
import net.heavenmine.hmezcore.file.ConfigFile;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class WarpsCommand implements CommandExecutor {
    private final Main main;
    private final ConfigFile configFile;

    public WarpsCommand(Main main, ConfigFile configFile) {
        this.main = main;
        this.configFile = configFile;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Helper helper = new Helper(configFile);
        YamlConfiguration messageFile = YamlConfiguration.loadConfiguration(new File(main.getDataFolder(), "message.yml"));
        YamlConfiguration warpsFile = YamlConfiguration.loadConfiguration(new File(main.getDataFolder(), "warps.yml"));
        String message = "&a----(HMEasyCore Warp Commands)----\n" +
                "&6/warp <name> - Teleport to warp\n" +
                "&6/warp - Show all warps!\n";
        if(args.length == 0) {
            String listwarp = "&6Warps: &f";
            if(warpsFile.get("spawn") == null) {
                sender.sendMessage(helper.Print( messageFile.getString("spawn-not-found"),true));
                sender.sendMessage(helper.Print(listwarp,true));
                return false;
            }
            ConfigurationSection warpsSection = warpsFile.getConfigurationSection("warps");
            for (String warpName : warpsSection.getKeys(false)){
                listwarp = listwarp + warpName + " ";
            }
            sender.sendMessage(helper.Print(listwarp,true));

        } else if (args.length == 1) {
            ConfigurationSection warpsSection = warpsFile.getConfigurationSection("warps."+ args[0]);
            if(warpsSection == null) {
                sender.sendMessage(helper.Print(messageFile.getString("warp-not-found").replace("{warp}",args[0])));
            } else {
                if(!(sender instanceof Player)) {
                    sender.sendMessage(helper.Print("&cUse /warps <warp-name> <player>", true));
                    return false;
                }
                Player player = (Player) sender;
                String worldName = warpsSection.getString("world");
                World world = main.getServer().getWorld(worldName);
                double x = warpsSection.getDouble("x");
                double y = warpsSection.getDouble("y");
                double z = warpsSection.getDouble("z");
                float pitch = (float) warpsSection.getDouble("pitch");
                float yaw = (float) warpsSection.getDouble("yaw");
                Location location = new Location(world, x, y, z, yaw, pitch);
                player.teleport(location);
                sender.sendMessage(helper.Print(messageFile.getString("teleported-to-warp").replace("{warp}",args[0])));
            }
        } else if(args.length == 3){
            if(sender instanceof Player) {
                sender.sendMessage(helper.Print(message));
            } else {
                ConfigurationSection warpsSection = warpsFile.getConfigurationSection("warps."+ args[0]);
                if(warpsSection == null) {
                    sender.sendMessage(helper.Print(messageFile.getString("warp-not-found").replace("{warp}",args[0])));
                } else {
                    Player target = main.getServer().getPlayer(args[1]);
                    if(target == null) {
                        sender.sendMessage(helper.Print(messageFile.getString("player-not-found").replace("{player}",args[1])));
                        return false;
                    }
                    String worldName = warpsSection.getString("world");
                    World world = main.getServer().getWorld(worldName);
                    double x = warpsSection.getDouble("x");
                    double y = warpsSection.getDouble("y");
                    double z = warpsSection.getDouble("z");
                    float pitch = (float) warpsSection.getDouble("pitch");
                    float yaw = (float) warpsSection.getDouble("yaw");
                    Location location = new Location(world, x, y, z, pitch, yaw);
                    target.teleport(location);
                    sender.sendMessage(helper.Print(messageFile.getString("teleported-to-warp").replace("{warp}",args[0])));
                }
            }
        } else {
            sender.sendMessage(helper.Print(message));
        }
        return false;
    }
}
