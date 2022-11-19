package net.pl3x.guithium.api.gui.texture;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.pl3x.guithium.api.Key;
import net.pl3x.guithium.api.Keyed;
import net.pl3x.guithium.api.json.JsonObjectWrapper;
import net.pl3x.guithium.api.json.JsonSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class Texture extends Keyed implements JsonSerializable {
    public static final Texture DIRT = new Texture(Key.of("minecraft:dirt"), "textures/gui/options_background.png");

    private String url;

    public Texture(@NotNull Key key, @NotNull String url) {
        super(key);
        setUrl(url);
    }

    @NotNull
    public String getUrl() {
        return this.url;
    }

    public void setUrl(@NotNull String url) {
        Preconditions.checkNotNull(url, "Url cannot be null");
        this.url = url;
    }

    @Override
    @NotNull
    public JsonElement toJson() {
        JsonObjectWrapper json = new JsonObjectWrapper();
        json.addProperty("key", getKey());
        json.addProperty("url", getUrl());
        return json.getJsonObject();
    }

    @NotNull
    public static Texture fromJson(@NotNull JsonObject json) {
        Preconditions.checkArgument(json.has("key"), "Key cannot be null");
        Preconditions.checkArgument(json.has("url"), "Url cannot be null");
        return new Texture(
            Key.of(json.get("key").getAsString()),
            json.get("url").getAsString()
        );
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        Texture other = (Texture) o;
        return getKey().equals(other.getKey())
            && getUrl().equals(other.getUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKey(), getUrl());
    }

    @Override
    @NotNull
    public String toString() {
        return "Texture{"
            + "key=" + getKey()
            + ",url=" + getUrl()
            + "}";
    }
}
