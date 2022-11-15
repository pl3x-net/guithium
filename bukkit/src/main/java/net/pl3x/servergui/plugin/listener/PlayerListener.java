package net.pl3x.servergui.plugin.listener;

import net.pl3x.servergui.plugin.ServerGUIBukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
    private final ServerGUIBukkit plugin;

    public PlayerListener(ServerGUIBukkit plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        this.plugin.getPlayerManager().remove(event.getPlayer().getUniqueId());
    }
}
