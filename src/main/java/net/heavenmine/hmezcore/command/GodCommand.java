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

public class GodCommand implements CommandExecutor {
    private final Main main;
    private final ConfigFile configFile;
    public GodCommand(Main main, ConfigFile configFile) {
        this.configFile = configFile;
        this.main = main;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        YamlConfiguration messageFile = YamlConfiguration.loadConfiguration(new File(main.getDataFolder(), "message.yml"));
        String prefix = configFile.getPrefix();
        if(sender instanceof Player) {
            if(sender.hasPermission("hmezcore.command.god")){
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + messageFile.getString("no-permission") ));
                return false;
            } else {
                ((Player) sender).setFoodLevel(20);
                ((Player) sender).setSaturation(20f);
                ((Player) sender).setHealth(20);
                ((Player) sender).setInvulnerable(true);
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix +  messageFile.getString("god-mode") ));
            }
        } else {
            if(args.length == 1) {
                if(sender.hasPermission("hmezcore.command.god.other")){
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + messageFile.getString("no-permission") ));
                    return false;
                }
                Player target = main.getServer().getPlayer(args[0]);
                if (target == null) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix + messageFile.getString("player-not-found").replace("{player}", args[0]) ));
                    return false;
                }
                target.setFoodLevel(20);
                target.setSaturation(20f);
                target.setHealth(20);
                target.setInvulnerable(true);
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix +  messageFile.getString("god-mode") ));
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix + "&6Use /god <player> !"));
            }
        }
        return false;
    }
}
