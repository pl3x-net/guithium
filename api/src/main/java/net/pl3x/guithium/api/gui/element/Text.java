package net.pl3x.guithium.api.gui.element;

import java.util.Objects;
import net.kyori.adventure.text.Component;
import net.pl3x.guithium.api.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a text element.
 */
public class Text extends AbstractElement<Text> {
    private Component text;
    private Boolean shadow;

    /**
     * Create a new text element.
     *
     * @param key Unique identifier
     */
    public Text(@NotNull String key) {
        this(Key.of(key));
    }

    /**
     * Create a new text element.
     *
     * @param key Unique identifier
     */
    public Text(@NotNull Key key) {
        super(key, Type.TEXT);
    }

    /**
     * Create a new text element.
     *
     * @param key Unique identifier
     * @return New text element
     */
    @NotNull
    public static Text of(@NotNull String key) {
        return of(Key.of(key));
    }

    /**
     * Create a new text element.
     *
     * @param key Unique identifier
     * @return New text element
     */
    @NotNull
    public static Text of(@NotNull Key key) {
        return new Text(key);
    }

    /**
     * Get the text component.
     *
     * @return Text component
     */
    @Nullable
    public Component getText() {
        return this.text;
    }

    /**
     * Set the text component.
     *
     * @param text Component to set
     * @return This text
     */
    @NotNull
    public Text setText(@Nullable Component text) {
        this.text = text;
        return this;
    }

    /**
     * Whether to draw a drop shadow behind the text.
     *
     * @return {@code true} to draw a drop shadow behind the text, otherwise {@code false}
     */
    @Nullable
    public Boolean hasShadow() {
        return this.shadow;
    }

    /**
     * Set whether to draw a drop shadow behind the text.
     *
     * @param shadow {@code true} if text should have shadow, otherwise {@code false}
     * @return This text
     */
    @NotNull
    public Text setShadow(@Nullable Boolean shadow) {
        this.shadow = shadow;
        return this;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        Text other = (Text) obj;
        return Objects.equals(getText(), other.getText())
                && Objects.equals(hasShadow(), other.hasShadow());
    }
}
