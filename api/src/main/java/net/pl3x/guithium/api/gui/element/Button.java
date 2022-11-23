package net.pl3x.guithium.api.gui.element;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.pl3x.guithium.api.Key;
import net.pl3x.guithium.api.gui.Point;
import net.pl3x.guithium.api.gui.Screen;
import net.pl3x.guithium.api.json.JsonObjectWrapper;
import net.pl3x.guithium.api.player.WrappedPlayer;
import net.pl3x.guithium.api.util.TriConsumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Represents a clickable button.
 */
public class Button extends Rect {
    private String text;
    private Component tooltip;
    private OnClick onClick = (screen, button, player) -> {
    };

    /**
     * Creates a new clickable button.
     *
     * @param key     Unique identifier for button
     * @param pos     Position of button
     * @param anchor  Anchor for button
     * @param offset  Offset of button
     * @param size    Size of button
     * @param text    Text label
     * @param tooltip Text on hover tooltip
     */
    protected Button(@NotNull Key key, @Nullable Point pos, @Nullable Point anchor, @Nullable Point offset, @Nullable Point size, @Nullable String text, @Nullable Component tooltip) {
        super(key, Type.BUTTON, pos, anchor, offset, size);
        setText(text);
        setTooltip(tooltip);
    }

    /**
     * Get the text label.
     *
     * @return Text label
     */
    @Nullable
    public String getText() {
        return this.text;
    }

    /**
     * Set the text label.
     *
     * @param text Text label
     */
    public void setText(@Nullable String text) {
        this.text = text;
    }

    /**
     * Get the text for hover tooltip.
     *
     * @return Tooltip text
     */
    @Nullable
    public Component getTooltip() {
        return this.tooltip;
    }

    /**
     * Set the text for hover tooltip.
     *
     * @param tooltip Tooltip text to set
     */
    public void setTooltip(@Nullable Component tooltip) {
        this.tooltip = tooltip;
    }

    /**
     * Get the action to execute when the button is clicked.
     *
     * @return OnClick action
     */
    @Nullable
    public OnClick onClick() {
        return this.onClick;
    }

    /**
     * Set the action to execute when the button is clicked.
     *
     * @param onClick OnClick action
     */
    public void onClick(@Nullable OnClick onClick) {
        this.onClick = onClick;
    }

    @Override
    @NotNull
    public JsonElement toJson() {
        JsonObjectWrapper json = new JsonObjectWrapper(super.toJson());
        json.addProperty("text", getText());
        json.addProperty("tooltip", getTooltip());
        return json.getJsonObject();
    }

    /**
     * Create a new button from Json.
     *
     * @param json Json representation of a button
     * @return A new button
     */
    @NotNull
    public static Button fromJson(@NotNull JsonObject json) {
        Preconditions.checkArgument(json.has("key"), "Key cannot be null");
        return new Button(
            Key.of(json.get("key").getAsString()),
            !json.has("pos") ? null : Point.fromJson(json.get("pos").getAsJsonObject()),
            !json.has("anchor") ? null : Point.fromJson(json.get("anchor").getAsJsonObject()),
            !json.has("offset") ? null : Point.fromJson(json.get("offset").getAsJsonObject()),
            !json.has("size") ? null : Point.fromJson(json.get("size").getAsJsonObject()),
            !json.has("text") ? null : json.get("text").getAsString(),
            !json.has("tooltip") ? null : GsonComponentSerializer.gson().deserialize(json.get("tooltip").getAsString())
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
        Button other = (Button) o;
        return Objects.equals(getText(), other.getText())
            && Objects.equals(getTooltip(), other.getTooltip())
            && super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getText(), getTooltip(), super.hashCode());
    }

    @Override
    @NotNull
    public String toString() {
        return String.format("Button{%s}", getPropertiesAsString());
    }

    @Override
    @NotNull
    protected String getPropertiesAsString() {
        return super.getPropertiesAsString()
            + ",text=" + getText()
            + ",tooltip=" + getTooltip();
    }

    /**
     * Create a new button builder.
     *
     * @param key Unique identifying key for the button
     * @return New button builder
     */
    @NotNull
    public static Builder builder(@NotNull String key) {
        return new Builder(key);
    }

    /**
     * Create a new button builder.
     *
     * @param key Unique identifying key for the button
     * @return New button builder
     */
    @NotNull
    public static Builder builder(@NotNull Key key) {
        return new Builder(key);
    }

    /**
     * Builder for buttons.
     */
    public static class Builder extends Rect.Builder<Builder> {
        private String text;
        private Component tooltip;
        private OnClick onClick = (screen, button, player) -> {
        };

        /**
         * Create a new button builder.
         *
         * @param key Unique identifying key for the button
         */
        public Builder(@NotNull String key) {
            this(Key.of(key));
        }

        /**
         * Create a new button builder.
         *
         * @param key Unique identifying key for the button
         */
        public Builder(@NotNull Key key) {
            super(key);
        }

        /**
         * Get the text on the button's face.
         *
         * @return Text on face
         */
        @Nullable
        public String getText() {
            return text;
        }

        /**
         * Set the text on the button's face.
         *
         * @param text Text to set
         * @return This builder
         */
        @NotNull
        public Builder setText(@Nullable String text) {
            this.text = text;
            return this;
        }

        /**
         * Get the text for hover tooltip.
         *
         * @return Tooltip text
         */
        @Nullable
        public Component getTooltip() {
            return tooltip;
        }

        /**
         * Set the text for hover tooltip.
         *
         * @param tooltip Tooltip text to set
         * @return This builder
         */
        @NotNull
        public Builder setTooltip(@Nullable Component tooltip) {
            this.tooltip = tooltip;
            return this;
        }

        /**
         * Get the action to execute when the button is clicked.
         *
         * @return OnClick action
         */
        @Nullable
        public OnClick onClick() {
            return this.onClick;
        }

        /**
         * Set the action to execute when the button is clicked.
         *
         * @param onClick OnClick action
         * @return This builder
         */
        @NotNull
        public Builder onClick(@Nullable OnClick onClick) {
            this.onClick = onClick;
            return this;
        }

        /**
         * Build a new button from the current properties in this builder.
         *
         * @return New button
         */
        @Override
        @NotNull
        public Button build() {
            Button button = new Button(getKey(), getPos(), getAnchor(), getOffset(), getSize(), getText(), getTooltip());
            button.onClick(this.onClick);
            return button;
        }
    }

    /**
     * Executable functional interface to fire when a button is clicked.
     */
    @FunctionalInterface
    public interface OnClick extends TriConsumer<Screen, Button, WrappedPlayer> {
        /**
         * Called when a button is clicked.
         *
         * @param screen Active screen where button was clicked
         * @param button Button that was clicked
         * @param player Player that button the checkbox
         */
        void accept(@NotNull Screen screen, @NotNull Button button, @NotNull WrappedPlayer player);
    }
}
