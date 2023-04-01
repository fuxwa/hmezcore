package net.heavenmine.hmezcore.command.warps;

import net.heavenmine.hmezcore.Helper;
import net.heavenmine.hmezcore.Main;
import net.heavenmine.hmezcore.file.ConfigFile;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class SetWarpCommand implements CommandExecutor {
    private final Main main;
    private final ConfigFile configFile;

    public SetWarpCommand(Main main, ConfigFile configFile) {
        this.main = main;
        this.configFile = configFile;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Helper helper = new Helper(configFile);
        YamlConfiguration messageFile = YamlConfiguration.loadConfiguration(new File(main.getDataFolder(), "message.yml"));
        YamlConfiguration warpsFile = YamlConfiguration.loadConfiguration(new File(main.getDataFolder(), "warps.yml"));
        String message = "&a----(HMEasyCore SetWarp Commands)----\n" +
                "&6/setwarp <name> - Set new warp at your location\n" +
                "&6/delwarp - Delete a warp!\n";
        if(!(sender instanceof Player)) {
            sender.sendMessage(messageFile.getString("player-only"));
            return false;
        }
        Player player = (Player) sender;
        if((!player.hasPermission("hmezcore.commands.warps"))) {
            sender.sendMessage(helper.Print( messageFile.getString("no-permission"),true));
            return false;
        }
        if(args.length == 1) {
            String world = player.getLocation().getWorld().getName();
            Double x = player.getLocation().getX();
            Double y = player.getLocation().getY();
            Double z = player.getLocation().getZ();
            Float pitch = player.getLocation().getPitch();
            Float yaw = player.getLocation().getYaw();
            warpsFile.set("warps."+ args[0]+".world", world);
            warpsFile.set("warps."+ args[0]+".x", x);
            warpsFile.set("warps."+ args[0]+".y", y);
            warpsFile.set("warps."+ args[0]+".z", z);
            warpsFile.set("warps."+ args[0]+".pitch", pitch);
            warpsFile.set("warps."+ args[0]+".yaw", yaw);
            try {
                warpsFile.save(new File(main.getDataFolder(), "warps.yml"));
                sender.sendMessage(helper.Print( messageFile.getString("set-warp-complete").replace("{warp}", args[0]),true));
                return true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            sender.sendMessage(helper.Print(message));
            return false;
        }
    }
}
