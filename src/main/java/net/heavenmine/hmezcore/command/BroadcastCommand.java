package net.heavenmine.hmezcore.command;

import net.heavenmine.hmezcore.Main;
import net.heavenmine.hmezcore.file.ConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class BroadcastCommand implements CommandExecutor {
    private Main main;
    private ConfigFile configFile;
    public BroadcastCommand(Main main, ConfigFile configFile) {
        this.main = main;
        this.configFile = configFile;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        YamlConfiguration messageFile = YamlConfiguration.loadConfiguration(new File(main.getDataFolder(), "message.yml"));
        String prefix = configFile.getPrefix();
        if(args.length > 0) {
            String message = "";
            for(int i = 0 ; i < args.length; i++) {
                message = message + args[i] + " ";
            }
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&' , messageFile.getString("prefix-broadcast") + message));
            } else {
                if (!sender.hasPermission("hmezcore.command.gamemode")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + messageFile.getString("no-permission") ));
                    return false;
                }
            }
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        messageFile.getString("prefix-broadcast") + message));
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&' ,prefix + " &6Use &d/bc <message> &6!"));
        }

        return false;
    }
}
