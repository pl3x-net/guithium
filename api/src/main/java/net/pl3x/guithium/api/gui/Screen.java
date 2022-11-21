package net.pl3x.guithium.api.gui;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.pl3x.guithium.api.Key;
import net.pl3x.guithium.api.Keyed;
import net.pl3x.guithium.api.gui.element.Element;
import net.pl3x.guithium.api.gui.element.Gradient;
import net.pl3x.guithium.api.gui.element.Rect;
import net.pl3x.guithium.api.json.JsonObjectWrapper;
import net.pl3x.guithium.api.json.JsonSerializable;
import net.pl3x.guithium.api.network.packet.CloseScreenPacket;
import net.pl3x.guithium.api.network.packet.OpenScreenPacket;
import net.pl3x.guithium.api.player.WrappedPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class Screen extends Keyed implements JsonSerializable {
    private final Type type;
    private final Map<Key, Element> elements = new LinkedHashMap<>();

    protected Screen(@NotNull Key key) {
        this(key, null);
    }

    protected Screen(@NotNull Key key, @Nullable Type type) {
        super(key);
        this.type = type;
    }

    @Nullable
    public Type getType() {
        return this.type;
    }

    public void addElements(@NotNull Collection<Element> elements) {
        elements.forEach(this::addElement);
    }

    @NotNull
    public Map<Key, Element> getElements() {
        return this.elements;
    }

    public void addElement(@NotNull Element element) {
        this.elements.put(element.getKey(), element);
    }

    @Nullable
    public Element getElement(@NotNull Element element) {
        return this.elements.get(element.getKey());
    }

    @Nullable
    public Element getElement(@NotNull Key key) {
        return this.elements.get(key);
    }

    @Nullable
    public Element getElement(@NotNull String key) {
        return getElement(Key.of(key));
    }

    public void removeElement(@NotNull Element element) {
        this.elements.remove(element.getKey());
    }

    public void removeElement(@NotNull Key key) {
        this.elements.entrySet().removeIf(entry -> entry.getKey().equals(key));
    }

    public void removeElement(@NotNull String key) {
        removeElement(Key.of(key));
    }

    public boolean hasElement(@NotNull Element element) {
        return this.elements.containsKey(element.getKey());
    }

    public boolean hasElement(@NotNull Key key) {
        for (Element element : this.elements.values()) {
            if (element.getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasElement(@NotNull String key) {
        return hasElement(Key.of(key));
    }

    public void open(@NotNull WrappedPlayer player) {
        player.setCurrentScreen(this);
        player.getConnection().send(new OpenScreenPacket(this));
    }

    public void close(@NotNull WrappedPlayer player) {
        player.setCurrentScreen(null);
        player.getConnection().send(new CloseScreenPacket(getKey()));
    }

    @Override
    @NotNull
    public JsonElement toJson() {
        JsonObjectWrapper json = new JsonObjectWrapper();
        json.addProperty("key", getKey());
        json.addProperty("type", getType());
        json.addProperty("elements", getElements().values());
        return json.getJsonObject();
    }

    @NotNull
    public static Screen fromJson(@NotNull JsonObject json) {
        Preconditions.checkArgument(json.has("key"), "Key cannot be null");
        Screen screen = new Screen(
            Key.of(json.get("key").getAsString()),
            !json.has("type") ? null : Type.valueOf(json.get("type").getAsString().toUpperCase(Locale.ROOT))
        );
        if (json.has("elements")) {
            json.get("elements").getAsJsonArray().forEach(jsonElement -> {
                if (jsonElement.isJsonObject()) {
                    Element element = Element.fromJson(jsonElement.getAsJsonObject());
                    if (element != null) {
                        screen.getElements().put(element.getKey(), element);
                    }
                }
            });
        }
        return screen;
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
        Screen other = (Screen) o;
        return Objects.equals(getKey(), other.getKey())
            && Objects.equals(getType(), other.getType())
            && getElements().equals(other.getElements());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKey(), getType(), getElements());
    }

    @Override
    @NotNull
    public String toString() {
        return "Screen{"
            + "key=" + getKey()
            + ",type=" + getType()
            + ",elements=" + getElements()
            + "}";
    }

    public static Builder builder(@NotNull String key) {
        return new Builder(key);
    }

    public static Builder builder(@NotNull Key key) {
        return new Builder(key);
    }

    public static class Builder extends Keyed {
        private Type type;
        private Rect background;

        public Builder(@NotNull String key) {
            this(Key.of(key));
        }

        public Builder(@NotNull Key key) {
            super(key);
            setBackground(Gradient.DEFAULT);
        }

        @Nullable
        public Type getType() {
            return this.type;
        }

        @NotNull
        public Builder setType(@Nullable Type type) {
            this.type = type;
            return this;
        }

        @Nullable
        public Rect getBackground() {
            return this.background;
        }

        @NotNull
        public Builder setBackground(@Nullable Rect background) {
            this.background = background;
            return this;
        }

        @NotNull
        public Screen build() {
            Screen screen = new Screen(getKey(), getType());
            if (getBackground() != null && getType() != Type.HUD) {
                screen.addElement(getBackground());
            }
            return screen;
        }
    }

    /**
     * Represents a screen type.
     */
    public enum Type {
        HUD, SCREEN
    }
}
