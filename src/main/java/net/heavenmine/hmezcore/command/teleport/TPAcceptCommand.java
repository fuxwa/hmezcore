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
import java.util.UUID;

public class TPAcceptCommand implements CommandExecutor {
    private final Main main;
    private final ConfigFile configFile;
    private TPACommand tpaCommand;

    public TPAcceptCommand(Main main, ConfigFile configFile, TPACommand tpaCommand) {
        this.main = main;
        this.configFile = configFile;
        this.tpaCommand = tpaCommand;
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
        UUID playerId = player.getUniqueId();
        UUID senderId = tpaCommand.getTpaRequest(player);
        if (senderId != null) {
            Player senderPlayer = Bukkit.getPlayer(senderId);
            if (senderPlayer != null) {
                player.teleport(senderPlayer.getLocation());
                player.sendMessage(helper.Print(messageFile.getString("teleported-to-player").replace("{player}",senderPlayer.getName()),true));
                senderPlayer.sendMessage(helper.Print(messageFile.getString("accept-request").replace("{player}",player.getName()),true));
            } else {
                player.sendMessage(helper.Print(messageFile.getString("player-not-found").replace("{player}",senderPlayer.getName()),true));
            }
            tpaCommand.removeTpaRequest(playerId);
        } else {
            player.sendMessage(helper.Print(messageFile.getString("request-not-found"),true));
        }
        return false;
    }
}
