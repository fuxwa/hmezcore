package net.heavenmine.hmezcore.command;

import net.heavenmine.hmezcore.Main;
import net.heavenmine.hmezcore.file.ConfigFile;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class GamemodeCommand implements CommandExecutor {

    private Main main;
    private ConfigFile configFile;

    public GamemodeCommand(Main main, ConfigFile configFile) {
        this.main = main;
        this.configFile = configFile;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        YamlConfiguration messageFile = YamlConfiguration.loadConfiguration(new File(main.getDataFolder(), "message.yml"));
        String prefix = configFile.getPrefix();
        if (!(commandSender instanceof Player)) {
            if (args.length == 2) {
                Player target = main.getServer().getPlayer(args[0]);
                if (target == null) {
                    commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix + messageFile.getString("player-not-found").replace("{player}", args[1]) ));
                } else {
                    if (args[0].equalsIgnoreCase("1")) {
                        target.setGameMode(GameMode.CREATIVE);
                        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + messageFile.getString("other-change-creative").replace("{player}", target.getDisplayName())));
                        return true;
                    } else if (args[0].equalsIgnoreCase("2")) {
                        target.setGameMode(GameMode.ADVENTURE);
                        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + messageFile.getString("other-change-adventure").replace("{player}", target.getDisplayName())));
                        return true;
                    } else if (args[0].equalsIgnoreCase("0")) {
                        target.setGameMode(GameMode.SURVIVAL);
                        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + messageFile.getString("other-change-survival").replace("{player}", target.getDisplayName())));
                        return true;
                    } else if (args[0].equalsIgnoreCase("3")) {
                        target.setGameMode(GameMode.SPECTATOR);
                        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + messageFile.getString("other-change-spectator").replace("{player}", target.getDisplayName())));
                        return true;
                    }
                    commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&cArgs " + args[0] + " not found"));
                }
                return false;
            } else {
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&' ,messageFile.getString("player-only")));
            }
            return true;
        }
        Player player = (Player) commandSender;

        if (!player.hasPermission("hmezcore.command.gamemode")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + messageFile.getString("no-permission")));
            return false;
        }
        if(args.length == 1) {
            if (args[0].equalsIgnoreCase("1")) {
                player.setGameMode(GameMode.CREATIVE);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + messageFile.getString("player-change-creative")));
                return true;
            } else if (args[0].equalsIgnoreCase("2")) {
                player.setGameMode(GameMode.ADVENTURE);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + messageFile.getString("player-change-adventure")));
                return true;
            } else if (args[0].equalsIgnoreCase("0")) {
                player.setGameMode(GameMode.SURVIVAL);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + messageFile.getString("player-change-survival")));
                return true;
            } else if (args[0].equalsIgnoreCase("3")) {
                player.setGameMode(GameMode.SPECTATOR);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + messageFile.getString("player-change-spectator")));
                return true;
            }
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &cArgs " + args[0] + " not found"));
            return false;
        } else if (args.length == 2) {
            Player target = main.getServer().getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix + messageFile.getString("player-not-found").replace("{player}", args[1]) ));
            } else {
                if (args[0].equalsIgnoreCase("1")) {
                    target.setGameMode(GameMode.CREATIVE);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + messageFile.getString("other-change-creative").replace("{player}", target.getDisplayName())));
                    return true;
                } else if (args[0].equalsIgnoreCase("2")) {
                    target.setGameMode(GameMode.ADVENTURE);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + messageFile.getString("other-change-adventure").replace("{player}", target.getDisplayName())));
                    return true;
                } else if (args[0].equalsIgnoreCase("0")) {
                    target.setGameMode(GameMode.SURVIVAL);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + messageFile.getString("other-change-survival").replace("{player}", target.getDisplayName())));
                    return true;
                } else if (args[0].equalsIgnoreCase("3")) {
                    target.setGameMode(GameMode.SPECTATOR);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + messageFile.getString("other-change-spectator").replace("{player}", target.getDisplayName())));
                    return true;
                }
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&cArgs " + args[0] + " not found"));
            }
            return false;
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &cError: Args not found"));
        }

        return true;
    }
}
