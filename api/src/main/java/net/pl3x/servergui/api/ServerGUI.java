package net.pl3x.servergui.api;

import net.pl3x.servergui.api.gui.GuiManager;
import net.pl3x.servergui.api.json.Gson;
import net.pl3x.servergui.api.texture.TextureManager;
import org.jetbrains.annotations.NotNull;

public interface ServerGUI {
    int PROTOCOL = 1;

    final class Provider {
        static ServerGUI api;

        @NotNull
        public static ServerGUI api() {
            return Provider.api;
        }
    }

    @NotNull
    static ServerGUI api() {
        return Provider.api();
    }

    @NotNull
    Gson gson();

    @NotNull
    GuiManager getGuiManager();

    @NotNull
    TextureManager getTextureManager();
}
