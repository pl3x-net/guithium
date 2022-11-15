package net.pl3x.servergui.api.player;

import com.google.common.io.ByteArrayDataOutput;
import net.pl3x.servergui.api.Key;
import net.pl3x.servergui.api.gui.Screen;
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

    void send(@NotNull Key channel, @NotNull ByteArrayDataOutput out);
}
