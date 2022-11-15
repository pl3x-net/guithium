package net.pl3x.servergui.plugin.player;

import com.google.common.io.ByteArrayDataOutput;
import net.pl3x.servergui.api.Key;
import net.pl3x.servergui.api.gui.Screen;
import net.pl3x.servergui.api.player.Player;
import net.pl3x.servergui.plugin.ServerGUIBukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class BukkitPlayer implements Player {
    private final org.bukkit.entity.Player player;

    private Screen currentScreen;

    public BukkitPlayer(@NotNull org.bukkit.entity.Player player) {
        this.player = player;
    }

    @Override
    @NotNull
    public String getName() {
        return this.player.getName();
    }

    @Override
    @NotNull
    public UUID getUUID() {
        return this.player.getUniqueId();
    }

    @Override
    @Nullable
    public Screen getCurrentScreen() {
        return this.currentScreen;
    }

    @Override
    public void setCurrentScreen(@Nullable Screen screen) {
        System.out.println("Current screen: " + (screen == null ? null : screen.getKey()));
        Thread.dumpStack();
        this.currentScreen = screen;
    }

    @Override
    public void send(@NotNull Key channel, @NotNull ByteArrayDataOutput out) {
        this.player.sendPluginMessage(ServerGUIBukkit.instance(), channel.toString(), out.toByteArray());
    }
}
