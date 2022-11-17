package net.pl3x.servergui.plugin.listener;

import net.pl3x.servergui.plugin.ServerGUI;
import net.pl3x.servergui.plugin.player.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
    private final ServerGUI plugin;

    public PlayerListener(ServerGUI plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        this.plugin.getPlayerManager().add(new Player(event.getPlayer()));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        this.plugin.getPlayerManager().remove(event.getPlayer().getUniqueId());
    }
}
