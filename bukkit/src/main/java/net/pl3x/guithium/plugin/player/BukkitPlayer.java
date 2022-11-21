package net.pl3x.guithium.plugin.player;

import net.pl3x.guithium.api.gui.Screen;
import net.pl3x.guithium.api.network.Connection;
import net.pl3x.guithium.api.player.WrappedPlayer;
import net.pl3x.guithium.plugin.Guithium;
import net.pl3x.guithium.plugin.network.BukkitConnection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class BukkitPlayer implements WrappedPlayer {
    private final Player player;
    private final Connection connection;

    private Screen currentScreen;
    private int protocol;

    public BukkitPlayer(@NotNull Player player) {
        this.player = player;
        this.connection = new BukkitConnection(this);
    }

    @NotNull
    @SuppressWarnings("unchecked")
    public <T> T unwrap() {
        return (T) this.player;
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
    @NotNull
    public Connection getConnection() {
        return this.connection;
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
    public boolean hasGuithium() {
        return getProtocol() == Guithium.PROTOCOL;
    }

    public int getProtocol() {
        return this.protocol;
    }

    public void setProtocol(int protocol) {
        this.protocol = protocol;
    }
}
