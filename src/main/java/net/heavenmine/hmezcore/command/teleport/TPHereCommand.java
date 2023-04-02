package net.heavenmine.hmezcore.command.teleport;

import net.heavenmine.hmezcore.Helper;
import net.heavenmine.hmezcore.Main;
import net.heavenmine.hmezcore.file.ConfigFile;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class TPHereCommand implements CommandExecutor {
    private final Main main;
    private final ConfigFile configFile;

    public TPHereCommand(Main main, ConfigFile configFile) {
        this.main = main;
        this.configFile = configFile;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Helper helper = new Helper(configFile);
        YamlConfiguration messageFile = YamlConfiguration.loadConfiguration(new File(main.getDataFolder(), "message.yml"));
        if(!(sender instanceof Player)) {
            sender.sendMessage(messageFile.getString("player-only"));
            return false;
        }
        Player player = (Player) sender;
        if(!player.hasPermission("hmezcore.command.tpcommand")) {
            sender.sendMessage(helper.Print( messageFile.getString("no-permission"),true));
            return false;
        }
        String message = "&a----(HMEasyCore Teleport Commands)----\n" +
                "&6/tp <name> - Teleport to other player\n" +
                "&6/tphere <name> - Teleport other player to your location\n";
        if(args.length == 1) {
            Player target = main.getServer().getPlayer(args[0]);
            if(target == null) {
                player.sendMessage(helper.Print(messageFile.getString("player-not-found").replace("{player}",args[0]),true));
                return false;
            }
            Location location = player.getLocation();
            target.teleport(location);
            player.sendMessage(helper.Print(messageFile.getString("player-teleported-to").replace("{player}",args[0]),true));
            return true;
        } else {
            sender.sendMessage(helper.Print(message));
            return false;
        }
    }
}
