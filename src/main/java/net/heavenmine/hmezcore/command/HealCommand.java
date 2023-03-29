package net.heavenmine.hmezcore.command;

import net.heavenmine.hmezcore.Main;
import net.heavenmine.hmezcore.file.ConfigFile;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.io.File;

public class HealCommand implements CommandExecutor {
    private final Main main;
    private final ConfigFile configFile;
    public HealCommand(Main main, ConfigFile configFile) {
        this.configFile = configFile;
        this.main = main;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        YamlConfiguration messageFile = YamlConfiguration.loadConfiguration(new File(main.getDataFolder(), "message.yml"));
        String prefix = configFile.getPrefix();
        if(sender instanceof Player) {
            if(sender.hasPermission("hmezcore.command.heal")){
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + messageFile.getString("no-permission") ));
                return false;
            } else {
                ((Player) sender).setHealth(((Player) sender).getMaxHealth());
                for (PotionEffect effect : ((Player) sender).getActivePotionEffects()) {
                    ((Player) sender).removePotionEffect(effect.getType());
                }
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix +  messageFile.getString("heal-restore") ));
            }
        } else {
            if(args.length == 1) {
                Player target = main.getServer().getPlayer(args[0]);
                if (target == null) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix + messageFile.getString("player-not-found").replace("{player}", args[0]) ));
                    return false;
                }
                target.setHealth(target.getMaxHealth());
                for (PotionEffect effect : target.getActivePotionEffects()) {
                    target.removePotionEffect(effect.getType());
                }
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix +  messageFile.getString("heal-restore") ));
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix + "&6Use /heal <player> !"));
            }
        }
        return false;
    }
}
