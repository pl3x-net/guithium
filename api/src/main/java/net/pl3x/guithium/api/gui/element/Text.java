package net.pl3x.guithium.api.gui.element;

import com.google.common.base.Preconditions;
import java.util.Objects;
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
        super(key, Type.TEXT, pos, anchor, offset);
        setText(text);
        setShadow(shadow);
    }

    /**
     * Get the text component.
     *
     * @return Text component
     */
    @NotNull
    public Component getText() {
        return this.text;
    }

    /**
     * Set the text component.
     *
     * @param text Component to set
     */
    public void setText(@NotNull Component text) {
        Preconditions.checkNotNull(text, "Text cannot have null text component.");
        this.text = text;
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
     */
    public void setShadow(@Nullable Boolean shadow) {
        this.shadow = shadow;
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
        Text other = (Text) obj;
        return super.equals(obj)
                && Objects.equals(getText(), other.getText())
                && Objects.equals(hasShadow(), other.hasShadow());
    }

    /**
     * Create a new text builder.
     *
     * @param key Unique identifying key for the text
     * @return New text builder
     */
    @NotNull
    public static Builder builder(@NotNull String key) {
        return new Builder(key);
    }

    /**
     * Create a new text builder.
     *
     * @param key Unique identifying key for the text
     * @return New text builder
     */
    @NotNull
    public static Builder builder(@NotNull Key key) {
        return new Builder(key);
    }

    /**
     * Builder for texts.
     */
    public static class Builder extends AbstractBuilder<Builder> {
        private Component text = Component.empty();
        private Boolean shadow;

        /**
         * Create a new text builder.
         *
         * @param key Unique identifying key for the text
         */
        public Builder(@NotNull String key) {
            this(Key.of(key));
        }

        /**
         * Create a new text builder.
         *
         * @param key Unique identifying key for the text
         */
        public Builder(@NotNull Key key) {
            super(key);
        }

        /**
         * Get the text component.
         *
         * @return Text component
         */
        @NotNull
        public Component getText() {
            return text;
        }

        /**
         * Set the text component.
         *
         * @param text Component to set
         * @return This builder
         */
        @NotNull
        public Builder setText(@NotNull Component text) {
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
            return shadow;
        }

        /**
         * Set whether to draw a drop shadow behind the text.
         *
         * @param shadow {@code true} if text should have shadow, otherwise {@code false}
         * @return This builder
         */
        @NotNull
        public Builder setShadow(@Nullable Boolean shadow) {
            this.shadow = shadow;
            return this;
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
            Builder other = (Builder) obj;
            return super.equals(obj)
                    && Objects.equals(getText(), other.getText())
                    && Objects.equals(hasShadow(), other.hasShadow());
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
