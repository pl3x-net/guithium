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
import net.pl3x.guithium.api.util.QuadConsumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Represents a toggleable checkbox.
 */
public class Checkbox extends Rect {
    private String text;
    private Component tooltip;
    private Boolean defaultSelected;
    private Boolean showLabel;
    private OnClick onClick = (screen, checkbox, player, selected) -> {
    };

    /**
     * Creates a new toggleable checkbox.
     *
     * @param key             Unique identifier for element
     * @param pos             Position of element
     * @param anchor          Anchor for element
     * @param offset          Offset of element
     * @param size            Size of element
     * @param text            Text label
     * @param tooltip         Text on hover tooltip
     * @param defaultSelected Default selected state
     * @param showLabel       Show text label
     */
    protected Checkbox(@NotNull Key key, @Nullable Point pos, @Nullable Point anchor, @Nullable Point offset, @Nullable Point size, @Nullable String text, @Nullable Component tooltip, @Nullable Boolean defaultSelected, @Nullable Boolean showLabel) {
        super(key, Type.CHECKBOX, pos, anchor, offset, size);
        setText(text);
        setTooltip(tooltip);
        setDefaultSelected(defaultSelected);
        setShowLabel(showLabel);
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
     * Get default selected state.
     *
     * @return True if defaults to selected
     */
    @Nullable
    public Boolean isDefaultSelected() {
        return this.defaultSelected;
    }

    /**
     * Set default selected state.
     *
     * @param defaultSelected Default selected state
     */
    public void setDefaultSelected(@Nullable Boolean defaultSelected) {
        this.defaultSelected = defaultSelected;
    }

    /**
     * Get if we should show text label.
     *
     * @return True to show text label
     */
    @Nullable
    public Boolean isShowLabel() {
        return this.showLabel;
    }

    /**
     * Set if we should show text label.
     *
     * @param showLabel True to show text label
     */
    public void setShowLabel(@Nullable Boolean showLabel) {
        this.showLabel = showLabel;
    }

    /**
     * Get the action to execute when the checkbox is clicked.
     *
     * @return OnClick action
     */
    @Nullable
    public OnClick onClick() {
        return this.onClick;
    }

    /**
     * Set the action to execute when the checkbox is clicked.
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
        json.addProperty("default_selected", isDefaultSelected());
        json.addProperty("show_label", isShowLabel());
        return json.getJsonObject();
    }

    /**
     * Create a new checkbox from Json.
     *
     * @param json Json representation of a checkbox
     * @return A new checkbox
     */
    @NotNull
    public static Checkbox fromJson(@NotNull JsonObject json) {
        Preconditions.checkArgument(json.has("key"), "Key cannot be null");
        return new Checkbox(
            Key.of(json.get("key").getAsString()),
            !json.has("pos") ? null : Point.fromJson(json.get("pos").getAsJsonObject()),
            !json.has("anchor") ? null : Point.fromJson(json.get("anchor").getAsJsonObject()),
            !json.has("offset") ? null : Point.fromJson(json.get("offset").getAsJsonObject()),
            !json.has("size") ? null : Point.fromJson(json.get("size").getAsJsonObject()),
            !json.has("text") ? null : json.get("text").getAsString(),
            !json.has("tooltip") ? null : GsonComponentSerializer.gson().deserialize(json.get("tooltip").getAsString()),
            !json.has("default_selected") ? null : json.get("default_selected").getAsBoolean(),
            !json.has("show_label") ? null : json.get("show_label").getAsBoolean()
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
        Checkbox other = (Checkbox) o;
        return Objects.equals(getText(), other.getText())
            && Objects.equals(getTooltip(), other.getTooltip())
            && Objects.equals(isDefaultSelected(), other.isDefaultSelected())
            && Objects.equals(isShowLabel(), other.isShowLabel())
            && super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getText(), getTooltip(), isDefaultSelected(), isShowLabel(), super.hashCode());
    }

    @Override
    @NotNull
    public String toString() {
        return String.format("Checkbox{%s}", getPropertiesAsString());
    }

    @Override
    @NotNull
    protected String getPropertiesAsString() {
        return super.getPropertiesAsString()
            + ",text=" + getText()
            + ",tooltip=" + getTooltip()
            + ",defaultSelected=" + isDefaultSelected()
            + ",showLabel=" + isShowLabel();
    }

    /**
     * Create a new checkbox builder.
     *
     * @param key Unique identifying key for the checkbox
     * @return New checkbox builder
     */
    @NotNull
    public static Builder builder(@NotNull String key) {
        return new Builder(key);
    }

    /**
     * Create a new checkbox builder.
     *
     * @param key Unique identifying key for the checkbox
     * @return New checkbox builder
     */
    @NotNull
    public static Builder builder(@NotNull Key key) {
        return new Builder(key);
    }

    /**
     * Builder for checkboxes.
     */
    public static class Builder extends Rect.Builder<Builder> {
        private String text;
        private Component tooltip;
        private Boolean defaultSelected;
        private Boolean showLabel;
        private OnClick onClick = (screen, checkbox, player, selected) -> {
        };

        /**
         * Create a new checkbox builder.
         *
         * @param key Unique identifying key for the checkbox
         */
        public Builder(@NotNull String key) {
            this(Key.of(key));
        }

        /**
         * Create a new checkbox builder.
         *
         * @param key Unique identifying key for the checkbox
         */
        public Builder(@NotNull Key key) {
            super(key);
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
            return this.tooltip;
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
         * Get default selected state.
         *
         * @return True if defaults to selected
         */
        @Nullable
        public Boolean isDefaultSelected() {
            return this.defaultSelected;
        }

        /**
         * Set default selected state.
         *
         * @param defaultSelected Default selected state
         * @return This builder
         */
        @NotNull
        public Builder setDefaultSelected(@Nullable Boolean defaultSelected) {
            this.defaultSelected = defaultSelected;
            return this;
        }

        /**
         * Get if we should show text label.
         *
         * @return True to show text label
         */
        @Nullable
        public Boolean isShowLabel() {
            return this.showLabel;
        }

        /**
         * Set if we should show text label.
         *
         * @param showLabel True to show text label
         * @return This builder
         */
        @NotNull
        public Builder setShowLabel(@Nullable Boolean showLabel) {
            this.showLabel = showLabel;
            return this;
        }

        /**
         * Get the action to execute when the checkbox is clicked.
         *
         * @return OnClick action
         */
        @Nullable
        public OnClick onClick() {
            return this.onClick;
        }

        /**
         * Set the action to execute when the checkbox is clicked.
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
         * Build a new checkbox from the current properties in this builder.
         *
         * @return New checkbox
         */
        @Override
        @NotNull
        public Checkbox build() {
            Checkbox checkbox = new Checkbox(getKey(), getPos(), getAnchor(), getOffset(), getSize(), getText(), getTooltip(), isDefaultSelected(), isShowLabel());
            checkbox.onClick(this.onClick);
            return checkbox;
        }
    }

    /**
     * Executable functional interface to fire when a checkbox is clicked.
     */
    @FunctionalInterface
    public interface OnClick extends QuadConsumer<Screen, Checkbox, WrappedPlayer, Boolean> {
        /**
         * Called when a checkbox is clicked.
         *
         * @param screen   Active screen where checkbox was clicked
         * @param checkbox Checkbox that was clicked
         * @param player   Player that clicked the checkbox
         * @param selected New selected state of the checkbox
         */
        void accept(@NotNull Screen screen, @NotNull Checkbox checkbox, @NotNull WrappedPlayer player, @NotNull Boolean selected);
    }
}
