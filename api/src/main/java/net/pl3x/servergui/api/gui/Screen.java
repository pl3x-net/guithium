package net.pl3x.servergui.api.gui;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.pl3x.servergui.api.Key;
import net.pl3x.servergui.api.Keyed;
import net.pl3x.servergui.api.ServerGUI;
import net.pl3x.servergui.api.gui.element.Element;
import net.pl3x.servergui.api.json.JsonObjectWrapper;
import net.pl3x.servergui.api.json.JsonSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Screen extends Keyed implements JsonSerializable {
    private Type type;
    private final Map<Key, Element> elements = new ConcurrentHashMap<>();

    public Screen(@NotNull Key key) {
        this(key, null);
    }

    public Screen(@NotNull Key key, @Nullable Type type) {
        super(key);
        setType(type);
    }

    @Nullable
    public Type getType() {
        return this.type;
    }

    public void setType(@Nullable Type type) {
        this.type = type;
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

    public void send(@NotNull UUID uuid) {
        ServerGUI.api().getNetworkManager().send(uuid, this);
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
            !json.has("type") ? null : Type.get(json.get("type").getAsString())
        );
        if (json.has("elements")) {
            json.get("elements").getAsJsonArray().forEach(jsonElement -> {
                if (jsonElement.isJsonObject()) {
                    Element element = Element.createElement(jsonElement.getAsJsonObject());
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

        public Builder(@NotNull String key) {
            this(Key.of(key));
        }

        public Builder(@NotNull Key key) {
            super(key);
        }

        @Nullable
        public Type getType() {
            return type;
        }

        @NotNull
        public Builder setType(@Nullable Type type) {
            this.type = type;
            return this;
        }

        @NotNull
        public Screen build() {
            return new Screen(getKey(), getType());
        }
    }

    public enum Type {
        HUD,
        SCREEN;

        private final String name;

        Type() {
            this.name = name().toLowerCase(Locale.ROOT);
        }

        @Override
        @NotNull
        public String toString() {
            return this.name;
        }

        private static final Map<String, Type> BY_NAME = new HashMap<>();

        static {
            Arrays.stream(values()).forEach(type -> BY_NAME.put(type.toString(), type));
        }

        @Nullable
        public static Type get(@Nullable String type) {
            return type == null ? null : BY_NAME.get(type.toLowerCase(Locale.ROOT));
        }
    }
}
