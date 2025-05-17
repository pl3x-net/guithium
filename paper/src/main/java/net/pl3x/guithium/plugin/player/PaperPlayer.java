package net.pl3x.guithium.plugin.player;

import java.util.UUID;
import net.pl3x.guithium.api.Guithium;
import net.pl3x.guithium.api.Unsafe;
import net.pl3x.guithium.api.gui.Screen;
import net.pl3x.guithium.api.player.WrappedPlayer;
import net.pl3x.guithium.plugin.network.PaperConnection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PaperPlayer implements WrappedPlayer {
    private final Player player;
    private final PaperConnection connection;

    private Screen currentScreen;
    private int protocol = -1;

    protected PaperPlayer(@NotNull Player player) {
        this.player = player;
        this.connection = new PaperConnection(this);
    }

    @NotNull
    public <T> T unwrap() {
        return Unsafe.cast(this.player);
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
    public PaperConnection getConnection() {
        return this.connection;
    }

    @Override
    @Nullable
    public Screen getCurrentScreen() {
        return this.currentScreen;
    }

    @Override
    public void setCurrentScreen(@Nullable Screen screen) {
        this.currentScreen = screen;
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
