package net.pl3x.servergui.api.network;

import net.pl3x.servergui.api.gui.Screen;
import net.pl3x.servergui.api.gui.element.Element;
import net.pl3x.servergui.api.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface NetworkManager {
    void send(@NotNull Player player, @NotNull Element element);

    void send(@NotNull Player player, @Nullable Screen screen);
}
