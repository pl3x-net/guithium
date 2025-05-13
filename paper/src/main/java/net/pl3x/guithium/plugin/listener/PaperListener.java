package net.pl3x.guithium.plugin.listener;

import net.pl3x.guithium.plugin.GuithiumPlugin;
import net.pl3x.guithium.plugin.player.PaperPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PaperListener implements Listener {
    private final GuithiumPlugin plugin;

    public PaperListener(final GuithiumPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        this.plugin.getPlayerManager().add(new PaperPlayer(event.getPlayer()));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        this.plugin.getPlayerManager().remove(event.getPlayer().getUniqueId());
    }
}
