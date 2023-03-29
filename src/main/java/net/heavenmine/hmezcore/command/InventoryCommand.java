package net.heavenmine.hmezcore.command;

import net.heavenmine.hmezcore.Main;
import net.heavenmine.hmezcore.file.ConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.io.File;

public class InventoryCommand implements CommandExecutor {
    private final Main main;
    private final ConfigFile configFile;

    public InventoryCommand(Main main, ConfigFile configFile) {
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
        if(!player.hasPermission("hmezcore.command.inventory")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + messageFile.getString("player-not-found") ));
            return false;
        }
        Player target = main.getServer().getPlayer(args[0]);
        if (args.length == 1) {
//            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
//            Inventory enderChest = offlinePlayer.getPlayer().getEnderChest();
//            player.openInventory(enderChest);
//            Player target = main.getServer().getPlayer(args[0]);
            if(target != null) {
                Inventory inventory = target.getInventory();
                player.openInventory(inventory);
            } else {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
                if(offlinePlayer.getPlayer() != null) {
                    Inventory inventory = offlinePlayer.getPlayer().getInventory();
                    player.openInventory(inventory);
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + messageFile.getString("player-not-found").replace("{player}", args[0]) ));
                    return false;
                }
            }
        }
        return true;
    }
}
