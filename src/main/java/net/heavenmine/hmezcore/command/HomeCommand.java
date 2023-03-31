package net.heavenmine.hmezcore.command;

import net.heavenmine.hmezcore.Helper;
import net.heavenmine.hmezcore.Main;
import net.heavenmine.hmezcore.data.DataManager;
import net.heavenmine.hmezcore.file.ConfigFile;
import net.heavenmine.hmezcore.modal.HomePlayerData;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.List;

public class HomeCommand implements CommandExecutor {
    private final Main main;
    private final ConfigFile configFile;

    public HomeCommand(Main main, ConfigFile configFile) {
        this.main = main;
        this.configFile = configFile;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        DataManager data = new DataManager(main, configFile);
        Helper helper = new Helper(configFile);
        YamlConfiguration messageFile = YamlConfiguration.loadConfiguration(new File(main.getDataFolder(), "message.yml"));
        String prefix = configFile.getPrefix();
        String message = "&a----(HMEasyCore Home Commands)----\n" +
                "&6/home set <name> - Set home at your location\n" +
                "&6/home tp <name> - Teleport to your home\n" +
                "&6/home del <name> - Delete your home\n" +
                "&6/home list - See all your home\n";
        if(args.length > 0) {
            switch (args[0]) {
                case "set":
                    if(!(sender instanceof Player)) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + messageFile.getString("player-only") ));
                        return false;
                    }
                    Player player = (Player) sender;
                    if(args.length == 2) {
                        List<HomePlayerData> homedata = data.getHomeData(player.getUniqueId().toString());
                        if(homedata.isEmpty()) {
                            String username = player.getName();
                            String uuid = player.getUniqueId().toString();
                            String homename = args[1];
                            String world = player.getLocation().getWorld().getName();
                            String x = String.valueOf(player.getLocation().getX());
                            String y = String.valueOf(player.getLocation().getY());
                            String z = String.valueOf(player.getLocation().getZ());
                            data.insertHomeData(username, uuid, homename, world,x , y, z);
                            player.sendMessage(helper.Print(messageFile.getString("set-home-complete").replace("{home}", args[1]),true));
                        } else {
                            player.sendMessage(helper.Print(messageFile.getString("max-home"),true));
                        }
                    } else {
                        sender.sendMessage(helper.Print(message));
                    }
                    return true;

                case "list":
                    if(!(sender instanceof Player)) {
                        if(args.length == 1) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + messageFile.getString("player-only") ));
                            return false;
                        } else if (args.length == 2){
                            Player target = main.getServer().getPlayer(args[0]);
                            if(target == null) {
                                sender.sendMessage(helper.Print(messageFile.getString("player-not-found").replace("{player}",args[1])));
                                return false;
                            } else {
                                List<HomePlayerData> homedata = data.getHomeData(target.getUniqueId().toString());
                                String listHome = "";
                                Integer homeAmount = 0;
                                for (HomePlayerData item : homedata) {
                                    listHome = listHome + item.getHomeName() + " ";
                                    homeAmount = homeAmount + 1;
                                }
                                sender.sendMessage(helper.Print("&a----(Your Home)----\n" +
                                        (homeAmount > 0 ? listHome : "Empty"),true));
                            }
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + messageFile.getString("player-only") ));
                            return false;
                        }

                    } else {
                        Player player2 = (Player) sender;
                        List<HomePlayerData> homedata = data.getHomeData(player2.getUniqueId().toString());
                        String listHome = "";
                        Integer homeAmount = 0;
                        for (HomePlayerData item : homedata) {
                            listHome = listHome + item.getHomeName() + " ";
                            homeAmount = homeAmount + 1;
                        }
                        player2.sendMessage(helper.Print("&a----(Your Home)----\n" +
                                (homeAmount > 0 ? listHome : "Empty"),true));
                    }

                case "del":
                    if(!(sender instanceof Player)) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + messageFile.getString("player-only")));
                        return false;
                    }

                    Player player2 = (Player) sender;
                    data.deleteHome(player2.getUniqueId().toString(), args[1].toString());
                case "tp":
                    if(!(sender instanceof Player)) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + messageFile.getString("player-only") ));
                        return false;
                    }
                    if(args.length == 2) {
                        Player player3 = (Player) sender;
                        String uuid = player3.getUniqueId().toString();
                        String homeName = args[1].toString();
                        HomePlayerData found = data.findHome(uuid, homeName);
                        if(found != null) {

                            World world = main.getServer().getWorld(found.getWorld());
                            Double x = Double.valueOf(found.getX());
                            Double y = Double.valueOf(found.getY());
                            Double z = Double.valueOf(found.getZ());
                            Location location = new Location(world,x,y,z);
                            player3.teleport(location);
                            player3.sendMessage(helper.Print(messageFile.getString("teleported-to-home").replace("{home}", homeName), true));
                        } else {
                            player3.sendMessage(helper.Print(messageFile.getString("home-not-found").replace("{home}", homeName), true));
                        }
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                        return false;
                    }

                default:
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                    return false;
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
        }
        return false;
    }
}
