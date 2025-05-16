package net.pl3x.guithium.api.gui.texture;

import com.google.common.base.Preconditions;
import net.pl3x.guithium.api.json.JsonSerializable;
import net.pl3x.guithium.api.key.Key;
import net.pl3x.guithium.api.key.Keyed;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an image texture.
 */
public class Texture extends Keyed implements JsonSerializable {
    /**
     * Mojang's default dirt texture used in options screens.
     */
    public static final Texture DIRT = Texture.of("minecraft:dirt", "textures/gui/options_background.png");

    private final String url;

    /**
     * Create a new image texture.
     *
     * @param key Unique identifying key
     * @param url URL or resource location
     */
    protected Texture(@NotNull Key key, @NotNull String url) {
        super(key);
        Preconditions.checkNotNull(url, "Url cannot be null");
        this.url = url;
    }

    /**
     * Create a new image texture.
     *
     * @param id  Unique identifying key
     * @param url URL or resource location
     * @return new Texture
     */
    @NotNull
    public static Texture of(@NotNull String id, @NotNull String url) {
        return of(Key.of(id), url);
    }

    /**
     * Create a new image texture.
     *
     * @param key Unique identifying key
     * @param url URL or resource location
     * @return new Texture
     */
    @NotNull
    public static Texture of(@NotNull Key key, @NotNull String url) {
        return new Texture(key, url);
    }

    /**
     * Get the URL or resource location to the texture.
     * <p>
     * Values starting with <code>http://</code> or <code>https://</code> will be retrieved from the internet.
     * <p>
     * For vanilla textures, just specify the resource location with or without the <code>minecraft:</code> namespace.
     * <p>
     * For textures provided by other client mods, just specify the resource location with their mod id as the namespace.
     *
     * @return URL or resource location of the texture
     */
    @NotNull
    public String getUrl() {
        return this.url;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        Texture other = (Texture) obj;
        return getUrl().equals(other.getUrl());
    }

    @Override
    public int hashCode() {
        // pacifies codefactor.io
        return super.hashCode();
    }
}
