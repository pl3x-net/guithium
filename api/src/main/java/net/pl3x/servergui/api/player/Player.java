package net.pl3x.servergui.api.player;

import net.pl3x.servergui.api.gui.Screen;
import net.pl3x.servergui.api.net.Connection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface Player {
    @NotNull
    String getName();

    @NotNull
    UUID getUUID();

    @Nullable
    Screen getCurrentScreen();

    void setCurrentScreen(@Nullable Screen screen);

    @NotNull
    Connection getConnection();
}
