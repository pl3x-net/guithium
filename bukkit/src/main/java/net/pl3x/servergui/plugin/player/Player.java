package net.pl3x.servergui.plugin.player;

import net.pl3x.servergui.api.gui.Screen;
import net.pl3x.servergui.plugin.net.Connection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class Player implements net.pl3x.servergui.api.player.Player {
    private final org.bukkit.entity.Player player;
    private final Connection connection;

    private Screen currentScreen;

    public Player(@NotNull org.bukkit.entity.Player player) {
        this.player = player;
        this.connection = new Connection(this);
    }

    @NotNull
    public org.bukkit.entity.Player getBukkitPlayer() {
        return this.player;
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
        if (screen == null || screen.getType() != Screen.Type.HUD) {
            this.currentScreen = screen;
        }
    }

    @Override
    @NotNull
    public Connection getConnection() {
        return this.connection;
    }
}
