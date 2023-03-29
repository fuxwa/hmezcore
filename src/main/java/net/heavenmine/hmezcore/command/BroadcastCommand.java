package net.heavenmine.hmezcore.command;

import net.heavenmine.hmezcore.Main;
import net.heavenmine.hmezcore.file.ConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BroadcastCommand implements CommandExecutor {
    private Main main;
    private ConfigFile configFile;
    public BroadcastCommand(Main main, ConfigFile configFile) {
        this.main = main;
        this.configFile = configFile;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String prefix = configFile.getPrefix();
        if(args.length > 0) {
            String message = "";
            for(int i = 0 ; i < args.length; i++) {
                message = message + args[i] + " ";
            }
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&a[&cThông Báo&a] " + message));
            }


            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[&cThông Báo&a] " + message));
            }

        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&' ,prefix + " &6Use &a/bc <message> &6!"));
        }

        return false;
    }
}
