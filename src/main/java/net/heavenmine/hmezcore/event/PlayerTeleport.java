package net.heavenmine.hmezcore.event;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerTeleport implements Listener {
    private Map<UUID, Location> backLocations = new HashMap<>();

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (!event.getFrom().equals(event.getTo())) {
            UUID playerId = event.getPlayer().getUniqueId();
            backLocations.put(playerId, event.getFrom());
        }
    }

    public Location getLastLocation(Player player) {
        UUID playerId = player.getUniqueId();
        return backLocations.get(playerId);
    }
}
