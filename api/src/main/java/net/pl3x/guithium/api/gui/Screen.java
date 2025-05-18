package net.pl3x.guithium.api.gui;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import net.pl3x.guithium.api.Guithium;
import net.pl3x.guithium.api.gui.element.Element;
import net.pl3x.guithium.api.key.Key;
import net.pl3x.guithium.api.key.Keyed;
import net.pl3x.guithium.api.network.packet.CloseScreenPacket;
import net.pl3x.guithium.api.network.packet.OpenScreenPacket;
import net.pl3x.guithium.api.player.WrappedPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a screen of gui elements.
 */
public class Screen extends Keyed {
    private final boolean hud;
    private final List<Element> elements = new ArrayList<>();

    /**
     * Create a new screen.
     *
     * @param key Unique identifier
     */
    public Screen(@NotNull String key) {
        this(Key.of(key));
    }

    /**
     * Create a new screen.
     *
     * @param key Unique identifier
     */
    public Screen(@NotNull Key key) {
        this(key, false);
    }

    /**
     * Create a new screen.
     *
     * @param key   Unique identifier
     * @param isHud {#code true} if screen is a HUD, otherwise {#code false}
     */
    public Screen(@NotNull String key, boolean isHud) {
        this(Key.of(key), isHud);
    }

    /**
     * Create a new screen.
     *
     * @param key   Unique identifier
     * @param isHud {#code true} if screen is a HUD, otherwise {#code false}
     */
    public Screen(@NotNull Key key, boolean isHud) {
        super(key);
        this.hud = isHud;
    }

    /**
     * Whether this screen is a HUD type.
     *
     * @return {#code true} if screen is a HUD, otherwise {#code false}
     */
    public boolean isHud() {
        return this.hud;
    }

    /**
     * Get an unmodifiable list of this screen's elements.
     *
     * @return List of elements
     */
    @NotNull
    public List<Element> getElements() {
        return ImmutableList.copyOf(this.elements);
    }

    /**
     * Get element by key.
     *
     * @param key Unique identifier
     * @return Element or null
     */
    @Nullable
    public Element getElement(@NotNull String key) {
        return getElement(Key.of(key));
    }

    /**
     * Get element by key.
     *
     * @param key Unique identifier
     * @return Element or null
     */
    @Nullable
    public Element getElement(@NotNull Key key) {
        return this.elements.stream().filter(element -> key.equals(element.getKey())).findAny().orElse(null);
    }

    /**
     * Add multiple elements to screen.
     *
     * @param elements Elements to add
     * @return {@code true} if at least one element was added, otherwise {@code false}
     * @throws IllegalArgumentException if screen already contains any element with a matching key
     */
    public boolean addElements(@NotNull Collection<Element> elements) {
        boolean changed = false;
        for (Element element : elements) {
            changed |= addElement(element);
        }
        return changed;
    }

    /**
     * Add element to screen.
     *
     * @param element Element to add
     * @return {@code true} if element was added, otherwise {@code false}
     * @throws IllegalArgumentException if screen already contains element with matching key
     */
    public boolean addElement(@NotNull Element element) {
        Preconditions.checkNotNull(element, "Cannot add null element to screen");
        Preconditions.checkArgument(!hasElement(element), "Screen already has element with key '%s'", element.getKey());
        return this.elements.add(element);
    }

    /**
     * Remove element from screen.
     *
     * @param element Element to remove
     * @return {@code true} if element was removed, otherwise {@code false}
     */
    public boolean removeElement(@NotNull Element element) {
        return removeElement(element.getKey());
    }

    /**
     * Remove element from screen by key.
     *
     * @param key Unique identifier of element to remove
     * @return {@code true} if element with specified key was removed, otherwise {@code false}
     */
    public boolean removeElement(@NotNull String key) {
        return removeElement(Key.of(key));
    }

    /**
     * Remove element from screen by key.
     *
     * @param key Unique identifier of element to remove
     * @return {@code true} if element with specified key was removed, otherwise {@code false}
     */
    public boolean removeElement(@NotNull Key key) {
        return this.elements.removeIf(element -> key.equals(element.getKey()));
    }

    /**
     * Check if screen has element.
     *
     * @param element Element to check
     * @return {@code true} if screen has element, otherwise {@code false}
     */
    public boolean hasElement(@NotNull Element element) {
        return hasElement(element.getKey());
    }

    /**
     * Check if screen has element by key.
     *
     * @param key Unique identifier to check
     * @return {@code true} if screen has element with specified key, otherwise {@code false}
     */
    public boolean hasElement(@NotNull String key) {
        return hasElement(Key.of(key));
    }

    /**
     * Check if screen has element by key.
     *
     * @param key Unique identifier to check
     * @return {@code true} if screen has element with specified key, otherwise {@code false}
     */
    public boolean hasElement(@NotNull Key key) {
        return this.elements.stream().anyMatch(element -> key.equals(element.getKey()));
    }

    /**
     * Open this screen on player's client.
     * <p>
     * If this screen is a HUD, then the screen will be added to the
     * client's HUD manager.
     * <p>
     * If this screen is not a HUD, then the player's controls will be
     * locked and the screen will be displayed to them.
     * <p>
     * If the player already has a non-HUD screen being displayed (even
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
     * If this screen is a HUD, then the screen will be added to the
     * client's HUD manager.
     * <p>
     * If this screen is not a HUD, then the player's controls will be
     * locked and the screen will be displayed to them.
     * <p>
     * If the player already has a non-HUD screen being displayed (even
     * a screen provided from another mod or vanilla), then that screen will
     * be replaced with this one.
     *
     * @param player Player to open screen for
     * @param <T>    Native player type
     */
    public <T> void open(@NotNull T player) {
        open(Guithium.api().getPlayerManager().get(player));
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
        close(Guithium.api().getPlayerManager().get(player));
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        Screen other = (Screen) obj;
        return Objects.equals(isHud(), other.isHud())
                && getElements().equals(other.getElements());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                super.hashCode(),
                isHud(),
                getElements()
        );
    }
}
