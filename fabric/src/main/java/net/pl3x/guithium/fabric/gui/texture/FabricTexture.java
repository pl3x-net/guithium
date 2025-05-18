package net.pl3x.guithium.fabric.gui.texture;

import net.pl3x.guithium.api.gui.texture.Texture;
import net.pl3x.guithium.api.key.Key;
import org.jetbrains.annotations.NotNull;

public class FabricTexture extends Texture {
    /**
     * Create a new image texture.
     *
     * @param key Unique identifying key
     * @param url URL or resource location
     */
    protected FabricTexture(@NotNull Key key, @NotNull String url) {
        super(key, url);
    }
}
