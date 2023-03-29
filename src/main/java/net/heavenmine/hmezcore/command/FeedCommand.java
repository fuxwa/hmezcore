package net.heavenmine.hmezcore.command;

import net.heavenmine.hmezcore.Main;
import net.heavenmine.hmezcore.file.ConfigFile;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class FeedCommand implements CommandExecutor {
    private Main main;
    private ConfigFile configFile;
    public FeedCommand(Main main, ConfigFile configFile) {
        this.configFile = configFile;
        this.main = main;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        YamlConfiguration messageFile = YamlConfiguration.loadConfiguration(new File(main.getDataFolder(), "message.yml"));
        String prefix = configFile.getPrefix();
        if(sender instanceof Player) {
            if(sender.hasPermission("hmezcore.command.feed")){
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + messageFile.getString("no-permission") ));
                return false;
            } else {
                ((Player) sender).setFoodLevel(20);
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix +  messageFile.getString("food-restore") ));
            }
        } else {
            if(args.length == 1) {
                Player target = main.getServer().getPlayer(args[0]);
                if (target == null) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix + messageFile.getString("player-not-found").replace("{player}", args[0]) ));
                    return false;
                }
                target.setFoodLevel(20);
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix +  messageFile.getString("food-restore") ));
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix + "&6Use /feed <player> !"));
            }
        }
        return false;
    }
}
