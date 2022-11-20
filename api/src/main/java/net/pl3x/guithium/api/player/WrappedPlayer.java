package net.pl3x.guithium.api.player;

import net.pl3x.guithium.api.gui.Screen;
import net.pl3x.guithium.api.network.Connection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface WrappedPlayer {
    @NotNull
    <T> T unwrap();

    @NotNull
    String getName();

    @NotNull
    UUID getUUID();

    @NotNull
    Connection getConnection();

    @Nullable
    Screen getCurrentScreen();

    void setCurrentScreen(@Nullable Screen screen);
}
