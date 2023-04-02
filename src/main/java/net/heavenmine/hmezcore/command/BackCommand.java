package net.heavenmine.hmezcore.command;

import net.heavenmine.hmezcore.Helper;
import net.heavenmine.hmezcore.Main;
import net.heavenmine.hmezcore.event.PlayerTeleport;
import net.heavenmine.hmezcore.file.ConfigFile;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BackCommand implements CommandExecutor {
    private final Main main;
    private final ConfigFile configFile;
    private PlayerTeleport playerTeleport;

    public BackCommand(Main main, ConfigFile configFile,PlayerTeleport playerTeleport) {
        this.main = main;
        this.configFile = configFile;
        this.playerTeleport = playerTeleport;
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
        Location lastLocation = playerTeleport.getLastLocation(player);
        if (lastLocation != null) {
            player.teleport(lastLocation);
            player.sendMessage(helper.Print(messageFile.getString("back-success"),true));
        } else {
            player.sendMessage(helper.Print(messageFile.getString("back-not-found"),true));
        }
        return true;
    }
}
