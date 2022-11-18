package net.pl3x.guithium.plugin.listener;

import net.pl3x.guithium.plugin.Guithium;
import net.pl3x.guithium.plugin.player.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerListener implements Listener {
    private final Guithium plugin;

    public PlayerListener(@NotNull Guithium plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(@NotNull PlayerJoinEvent event) {
        this.plugin.getPlayerManager().add(new Player(event.getPlayer()));
    }

    @EventHandler
    public void onQuit(@NotNull PlayerQuitEvent event) {
        this.plugin.getPlayerManager().remove(event.getPlayer().getUniqueId());
    }
}
