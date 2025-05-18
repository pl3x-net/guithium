package net.pl3x.guithium.plugin.listener;

import net.pl3x.guithium.api.Guithium;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

public class PaperListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void onLogin(@NotNull PlayerLoginEvent event) {
        if (event.getResult() == PlayerLoginEvent.Result.ALLOWED) {
            Guithium.api().getPlayerManager().add(event.getPlayer());
        }
    }

    @EventHandler
    public void onQuit(@NotNull PlayerQuitEvent event) {
        Guithium.api().getPlayerManager().remove(event.getPlayer());
    }
}
