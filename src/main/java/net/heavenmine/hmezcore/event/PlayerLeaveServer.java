package net.heavenmine.hmezcore.event;

import net.heavenmine.hmezcore.Main;
import net.heavenmine.hmezcore.data.DataManager;
import net.heavenmine.hmezcore.file.ConfigFile;
import net.heavenmine.hmezcore.modal.PlayerData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Date;

public class PlayerLeaveServer implements Listener {
    private Main main;
    private ConfigFile configFile;
    private DataManager dataManager;

    public PlayerLeaveServer(Main main, ConfigFile configFile, DataManager dataManager) {
        this.main = main;
        this.configFile = configFile;
        this.dataManager = dataManager;
    }
    @EventHandler
    public void updatePlayer(PlayerQuitEvent event) {
        Date date = new Date();
        String uuid = event.getPlayer().getUniqueId().toString();
        String location = event.getPlayer().getLocation().toString();
        String lastjoin = date.toString();
        PlayerData playerData = dataManager.getPlayer(uuid);
        if(playerData != null) {
            if(playerData.getFirstJoin() != null){
                dataManager.updatePlayer(uuid, lastjoin, location);
            }
        }
    }
}
