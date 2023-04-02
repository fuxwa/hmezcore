package net.heavenmine.hmezcore.command.teleport;

import net.heavenmine.hmezcore.Helper;
import net.heavenmine.hmezcore.Main;
import net.heavenmine.hmezcore.file.ConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TPACommand implements CommandExecutor {
    private final Main main;
    private final ConfigFile configFile;
    private Map<UUID, UUID> tpaRequests = new HashMap<>();
    public TPACommand(Main main, ConfigFile configFile) {
        this.main = main;
        this.configFile = configFile;
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
        String message = "&a----(HMEasyCore Teleport Commands)----\n" +
                "&6/tpa <name> - Teleport to other player\n" +
                "&6/tpahere <name> - Teleport other player to your location\n";
        if (args.length == 1) {
            Player targetPlayer = Bukkit.getPlayer(args[0]);
            if (targetPlayer != null) {
                UUID targetPlayerId = targetPlayer.getUniqueId();
                tpaRequests.put(targetPlayerId, player.getUniqueId());
                targetPlayer.sendMessage(helper.Print(messageFile.getString("receive-request-teleport").replace("{player}",player.getName()),true));
                player.sendMessage(helper.Print(messageFile.getString("send-request-success").replace("{player}",targetPlayer.getName()),true));
            } else {
                player.sendMessage(helper.Print(messageFile.getString("player-not-found").replace("{player}",args[0]),true));
            }
        } else {
            player.sendMessage(helper.Print(message));
        }
        return false;
    }
    public UUID getTpaRequest(Player player) {
        return tpaRequests.get(player.getUniqueId());
    }
    public void removeTpaRequest(UUID playerId) {
        tpaRequests.remove(playerId);
    }
}
