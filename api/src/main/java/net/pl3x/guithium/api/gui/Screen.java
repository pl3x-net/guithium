package net.pl3x.guithium.api.gui;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import net.pl3x.guithium.api.gui.element.Element;
import net.pl3x.guithium.api.key.Key;
import net.pl3x.guithium.api.key.Keyed;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a screen of gui elements.
 */
public class Screen extends Keyed {
    private final List<Element> elements = new ArrayList<>();

    /**
     * Create a new screen.
     *
     * @param key Unique identifier
     */
    public Screen(@NotNull Key key) {
        super(key);
    }

    /**
     * Get a list of this screen's elements.
     *
     * @return List of elements
     */
    @NotNull
    public List<Element> getElements() {
        return this.elements;
    }

    /**
     * Get element by key.
     *
     * @param key Unique identifier
     * @return Element or null
     */
    @Nullable
    public Element getElement(@NotNull Key key) {
        return this.elements.stream().filter(key::equals).findAny().orElse(null);
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
     * Add multiple elements to screen.
     *
     * @param elements Elements to add
     */
    public void addElements(@NotNull Collection<Element> elements) {
        elements.forEach(this::addElement);
    }

    /**
     * Add element to screen.
     *
     * @param element Element to add
     */
    public void addElement(@NotNull Element element) {
        Preconditions.checkNotNull(element, "Cannot add null element to screen");
        Preconditions.checkArgument(!hasElement(element), "Screen already has element with key '%s'", element.getKey());
        this.elements.add(element);
    }

    /**
     * Remove element from screen.
     *
     * @param element Element to remove
     */
    public void removeElement(@NotNull Element element) {
        removeElement(element.getKey());
    }

    /**
     * Remove element from screen by key.
     *
     * @param key Unique identifier of element to remove
     */
    public void removeElement(@NotNull String key) {
        removeElement(Key.of(key));
    }

    /**
     * Remove element from screen by key.
     *
     * @param key Unique identifier of element to remove
     */
    public void removeElement(@NotNull Key key) {
        this.elements.removeIf(key::equals);
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

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Screen other = (Screen) obj;
        return Objects.equals(getKey(), other.getKey())
                && getElements().equals(other.getElements());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKey(), getElements());
    }

    @Override
    @NotNull
    public String toString() {
        return "Screen{"
                + "key=" + getKey()
                + ",elements=" + getElements()
                + "}";
    }
}
