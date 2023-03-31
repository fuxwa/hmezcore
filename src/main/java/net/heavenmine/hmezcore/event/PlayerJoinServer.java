package net.heavenmine.hmezcore.event;

import net.heavenmine.hmezcore.Main;
import net.heavenmine.hmezcore.data.DataManager;
import net.heavenmine.hmezcore.file.ConfigFile;
import net.heavenmine.hmezcore.modal.PlayerData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Date;

public class PlayerJoinServer implements Listener {
    private Main main;
    private ConfigFile configFile;
    private DataManager dataManager;
    public PlayerJoinServer (Main main, ConfigFile configFile, DataManager dataManager) {
        this.main = main;
        this.configFile = configFile;
        this.dataManager = dataManager;
    }
    @EventHandler
    public void createPlayerData(PlayerJoinEvent event) {
        Date date = new Date();
        String username = event.getPlayer().getDisplayName();
        String uuid = event.getPlayer().getUniqueId().toString();
        String ip_address = event.getPlayer().getAddress().getAddress().getHostAddress();
        String firtjoin = date.toString();
        PlayerData playerData = dataManager.getPlayerData(uuid);
        if(playerData != null) {
            if(playerData.getFirstJoin() != null){
//                event.getPlayer().sendMessage("Welcome back " + username + "!");
            } else {
                dataManager.insertPlayerData(username, uuid, ip_address, firtjoin,"","", "","","");
            }
        } else {
            dataManager.insertPlayerData(username, uuid, ip_address, firtjoin,"","","","","");
        }
    }
}
