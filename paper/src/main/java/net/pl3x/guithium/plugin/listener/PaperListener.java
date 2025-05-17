package net.pl3x.guithium.plugin.listener;

import net.pl3x.guithium.plugin.GuithiumPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

public class PaperListener implements Listener {
    private final GuithiumPlugin plugin;

    public PaperListener(@NotNull GuithiumPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(@NotNull PlayerJoinEvent event) {
        this.plugin.getPlayerManager().add(event.getPlayer());
    }

    @EventHandler
    public void onQuit(@NotNull PlayerQuitEvent event) {
        this.plugin.getPlayerManager().remove(event.getPlayer());
    }
}
