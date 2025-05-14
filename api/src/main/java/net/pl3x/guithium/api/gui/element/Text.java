package net.pl3x.guithium.api.gui.element;

import com.google.common.base.Preconditions;
import net.kyori.adventure.text.Component;
import net.pl3x.guithium.api.gui.Point;
import net.pl3x.guithium.api.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents some text to be displayed on the screen.
 */
public class Text extends AbstractElement<Text> {
    private Component text;
    private Boolean shadow;

    /**
     * Create a new text element.
     *
     * @param key    Unique identifier
     * @param text   Text component to draw
     * @param pos    Position of text from anchor (pixel units)
     * @param anchor Anchor for text on screen (percentage 0.0-1.0)
     * @param offset Offset of text from position (percentage 0.0-1.0)
     * @param shadow Whether to draw a drop shadow behind the text
     */
    public Text(@NotNull Key key,
                @NotNull Component text,
                @Nullable Point pos,
                @Nullable Point anchor,
                @Nullable Point offset,
                @Nullable Boolean shadow
    ) {
        super(key, pos, anchor, offset);
        setText(text);
        setShadow(shadow);
    }

    @NotNull
    public Component getText() {
        return this.text;
    }

    public void setText(@NotNull Component text) {
        Preconditions.checkNotNull(text, "Text cannot have null text component.");
        this.text = text;
    }

    @Nullable
    public Boolean hasShadow() {
        return this.shadow;
    }

    public void setShadow(@Nullable Boolean shadow) {
        this.shadow = shadow;
    }

    @NotNull
    public static Builder builder(@NotNull String key) {
        return new Builder(key);
    }

    @NotNull
    public static Builder builder(@NotNull Key key) {
        return new Builder(key);
    }

    public static class Builder extends AbstractBuilder<Builder> {
        private Component text = Component.empty();
        private Boolean shadow;

        public Builder(@NotNull String key) {
            this(Key.of(key));
        }

        public Builder(@NotNull Key key) {
            super(key);
        }

        @NotNull
        public Component getText() {
            return text;
        }

        @NotNull
        public Builder setText(@NotNull Component text) {
            this.text = text;
            return this;
        }

        @Nullable
        public Boolean hasShadow() {
            return shadow;
        }

        @NotNull
        public Builder setShadow(@Nullable Boolean shadow) {
            this.shadow = shadow;
            return this;
        }

        @Override
        @NotNull
        public Text build() {
            return new Text(
                    getKey(),
                    getText(),
                    getPos(),
                    getAnchor(),
                    getOffset(),
                    hasShadow()
            );
        }
    }
}
