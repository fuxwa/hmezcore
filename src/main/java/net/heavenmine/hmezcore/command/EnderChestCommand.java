package net.heavenmine.hmezcore.command;

import net.heavenmine.hmezcore.Main;
import net.heavenmine.hmezcore.file.ConfigFile;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.io.File;

public class EnderChestCommand implements CommandExecutor {
    private final Main main;
    private final ConfigFile configFile;

    public EnderChestCommand(Main main, ConfigFile configFile) {
        this.main = main;
        this.configFile = configFile;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        YamlConfiguration messageFile = YamlConfiguration.loadConfiguration(new File(main.getDataFolder(), "message.yml"));
        String prefix = configFile.getPrefix();
        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + messageFile.getString("player-only") ));
            return false;
        }
        Player player = (Player) sender;
        if(!player.hasPermission("hmezcore.command.enderchest")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + messageFile.getString("no-permission") ));
            return false;
        }
        if(args.length == 0) {
            Inventory enderChest = player.getEnderChest();
            player.openInventory(enderChest);
        } else if (args.length == 1) {
            if(!player.hasPermission("hmezcore.command.enderchest.other")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + messageFile.getString("no-permission") ));
                return false;
            }
            Player target = main.getServer().getPlayer(args[0]);
            Inventory enderChest = target.getEnderChest();
            player.openInventory(enderChest);
        }
        return false;
    }
}
