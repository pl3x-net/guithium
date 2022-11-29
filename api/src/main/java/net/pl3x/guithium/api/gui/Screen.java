package net.pl3x.guithium.api.gui;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.pl3x.guithium.api.Guithium;
import net.pl3x.guithium.api.Key;
import net.pl3x.guithium.api.Keyed;
import net.pl3x.guithium.api.gui.element.Element;
import net.pl3x.guithium.api.gui.element.Gradient;
import net.pl3x.guithium.api.gui.element.Image;
import net.pl3x.guithium.api.gui.element.Radio;
import net.pl3x.guithium.api.gui.texture.Texture;
import net.pl3x.guithium.api.json.JsonObjectWrapper;
import net.pl3x.guithium.api.json.JsonSerializable;
import net.pl3x.guithium.api.network.packet.CloseScreenPacket;
import net.pl3x.guithium.api.network.packet.OpenScreenPacket;
import net.pl3x.guithium.api.player.WrappedPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a screen to draw on the client.
 * <p>
 * Screens are the containers of elements to draw.
 * <p>
 * Some screens can be used as HUDs which are always displayed to the player while they are playing.
 */
public class Screen extends Keyed implements JsonSerializable {
    /**
     * The default gradient background used on a lot of in-game screens, such as inventory menus.
     */
    public static final Gradient GRADIENT_BACKGROUND = Gradient.builder(Guithium.MOD_ID + ":gradient")
        .setColorTop(0xC0101010)
        .setColorBottom(0xD0101010)
        .build();

    /**
     * The default tiled dirt background used on a lot of options type screens.
     */
    public static final Image TILED_DIRT_BACKGROUND = Image.builder(Guithium.MOD_ID + ":tiled_dirt")
        .setTexture(Texture.DIRT)
        .setVertexColor(0xFF404040)
        .setTileModifier(32.0F)
        .build();

    private final Type type;
    private final Map<Key, Element> elements = new LinkedHashMap<>();

    /**
     * Creates a new default type (non-hud) screen.
     *
     * @param key Unique identifying key
     */
    protected Screen(@NotNull Key key) {
        this(key, null);
    }

    /**
     * Creates a new screen.
     *
     * @param key  Unique identifying key
     * @param type Type of screen
     */
    protected Screen(@NotNull Key key, @Nullable Type type) {
        super(key);
        this.type = type;
    }

    /**
     * Get this screen's type.
     *
     * @return Type of screen
     */
    @Nullable
    public Type getType() {
        return this.type;
    }

    /**
     * Add a collection of elements to the screen.
     *
     * @param elements Collection of elements to add
     */
    public void addElements(@NotNull Collection<Element> elements) {
        elements.forEach(this::addElement);
    }

    /**
     * Get an unmodifiable view of this screen's elements.
     *
     * @return Unmodifiable view of elements
     */
    @NotNull
    public Map<Key, Element> getElements() {
        return Collections.unmodifiableMap(this.elements);
    }

    /**
     * Add an element to this screen.
     *
     * @param element Element to add
     */
    public void addElement(@NotNull Element element) {
        if (element instanceof Radio radio && Boolean.TRUE.equals(radio.isSelected())) {
            Key group = radio.getGroup();
            if (group != null) {
                this.elements.forEach((key, element1) -> {
                    if (element1 instanceof Radio radio1 && Boolean.TRUE.equals(radio1.isSelected())) {
                        if (group.equals(radio1.getGroup())) {
                            throw new RuntimeException(String.format("Cannot add more than one selected radio button (%s and %s are both selected)", radio.getKey(), radio1.getKey()));
                        }
                    }
                });
            }
        }
        this.elements.put(element.getKey(), element);
    }

    /**
     * Get an element by unique id.
     *
     * @param key Unique id of element
     * @return Element if it exists, otherwise null
     */
    @Nullable
    public Element getElement(@NotNull Key key) {
        return this.elements.get(key);
    }

    /**
     * Get an element by unique id.
     *
     * @param key Unique id of element
     * @return Element if it exists, otherwise null
     */
    @Nullable
    public Element getElement(@NotNull String key) {
        return getElement(Key.of(key));
    }

    /**
     * Remove element from the screen.
     *
     * @param element Element to remove
     */
    public void removeElement(@NotNull Element element) {
        this.elements.remove(element.getKey());
    }

    /**
     * Remove element from the screen.
     *
     * @param key Unique id of element to remove
     */
    public void removeElement(@NotNull Key key) {
        this.elements.entrySet().removeIf(entry -> entry.getKey().equals(key));
    }

    /**
     * Remove element from the screen.
     *
     * @param key Unique id of element to remove
     */
    public void removeElement(@NotNull String key) {
        removeElement(Key.of(key));
    }

    /**
     * Check if element exists on screen.
     *
     * @param element Element to check
     * @return True if element is in this screen
     */
    public boolean hasElement(@NotNull Element element) {
        return this.elements.containsKey(element.getKey());
    }

    /**
     * Check if element exists on screen.
     *
     * @param key Unique id of element to check
     * @return True if element is in this screen
     */
    public boolean hasElement(@NotNull Key key) {
        for (Element element : this.elements.values()) {
            if (element.getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if element exists on screen.
     *
     * @param key Unique id of element to check
     * @return True if element is in this screen
     */
    public boolean hasElement(@NotNull String key) {
        return hasElement(Key.of(key));
    }

    /**
     * Open this screen on player's client.
     * <p>
     * If this screen is of type HUD, then the screen will be added to the
     * client's HUD manager.
     * <p>
     * If this screen is not a HUD type, then the player's controls will be
     * locked and the screen will be displayed to them.
     * <p>
     * If the player already has a non-HUD type screen being displayed (even
     * a screen provided from another mod or vanilla), then that screen will
     * be replaced with this one.
     *
     * @param player Player to open screen for
     */
    public void open(@NotNull WrappedPlayer player) {
        player.setCurrentScreen(this);
        player.getConnection().send(new OpenScreenPacket(this));
    }

    /**
     * Open this screen on player's client.
     * <p>
     * If this screen is of type HUD, then the screen will be added to the
     * client's HUD manager.
     * <p>
     * If this screen is not a HUD type, then the player's controls will be
     * locked and the screen will be displayed to them.
     * <p>
     * If the player already has a non-HUD type screen being displayed (even
     * a screen provided from another mod or vanilla), then that screen will
     * be replaced with this one.
     *
     * @param player Player to open screen for
     * @param <T>    Native player type
     */
    public <T> void open(@NotNull T player) {
        WrappedPlayer wrapped = Guithium.api().getPlayerManager().get(player);
        if (wrapped != null) {
            open(wrapped);
        }
    }

    /**
     * Close this screen on player's client.
     * <p>
     * If the player is <em>not</em> being displayed this screen, then no
     * action will be taken.
     *
     * @param player Player to close screen for
     */
    public void close(@NotNull WrappedPlayer player) {
        player.setCurrentScreen(null);
        player.getConnection().send(new CloseScreenPacket(getKey()));
    }

    /**
     * Close this screen on player's client.
     * <p>
     * If the player is <em>not</em> being displayed this screen, then no
     * action will be taken.
     *
     * @param player Player to close screen for
     * @param <T>    Native player type
     */
    public <T> void close(@NotNull T player) {
        WrappedPlayer wrapped = Guithium.api().getPlayerManager().get(player);
        if (wrapped != null) {
            close(wrapped);
        }
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

    /**
     * Create a new screen from Json.
     *
     * @param json Json representation of a screen
     * @return A new screen
     */
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
                        screen.addElement(element);
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

    /**
     * Create a new screen builder.
     *
     * @param key Unique identifying key for the screen
     * @return New screen builder
     */
    @NotNull
    public static Builder builder(@NotNull String key) {
        return new Builder(key);
    }

    /**
     * Create a new screen builder.
     *
     * @param key Unique identifying key for the screen
     * @return New screen builder
     */
    @NotNull
    public static Builder builder(@NotNull Key key) {
        return new Builder(key);
    }

    /**
     * Builder for screens.
     */
    public static class Builder extends Keyed {
        private Type type;

        /**
         * Create a new screen builder.
         *
         * @param key Unique identifying key for the screen
         */
        public Builder(@NotNull String key) {
            this(Key.of(key));
        }

        /**
         * Create a new screen builder.
         *
         * @param key Unique identifying key for the screen
         */
        public Builder(@NotNull Key key) {
            super(key);
        }

        /**
         * Get this screen's type.
         *
         * @return Type of screen
         */
        @Nullable
        public Type getType() {
            return this.type;
        }

        /**
         * Set this screen's type.
         *
         * @param type Type of screen
         * @return This builder
         */
        @NotNull
        public Builder setType(@Nullable Type type) {
            this.type = type;
            return this;
        }

        /**
         * Build a new screen from the current properties in this builder.
         *
         * @return New screen
         */
        @NotNull
        public Screen build() {
            return new Screen(getKey(), getType());
        }
    }

    /**
     * Represents a screen type.
     */
    public enum Type {
        /**
         * Huds are drawn on the player's screen while they are playing
         * the game. The player's controls are <em>not</em> locked.
         */
        HUD,

        /**
         * Screen is the normal/default type of screen. This will lock
         * the player's controls while it is being displayed.
         */
        SCREEN
    }
}
