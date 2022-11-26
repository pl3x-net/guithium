package net.pl3x.guithium.api.gui.element;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.pl3x.guithium.api.Key;
import net.pl3x.guithium.api.gui.Screen;
import net.pl3x.guithium.api.gui.Vec2;
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
    private String label;
    private Component tooltip;
    private Boolean selected;
    private Boolean showLabel;
    private OnToggled onToggled = (screen, checkbox, player, selected) -> {
    };

    /**
     * Creates a new toggleable checkbox.
     *
     * @param key       Unique identifier for checkbox
     * @param pos       Position of checkbox
     * @param anchor    Anchor for checkbox
     * @param offset    Offset of checkbox
     * @param rotation  Rotation in degrees
     * @param scale     Scale of element
     * @param size      Size of checkbox
     * @param label     Text label
     * @param tooltip   Text on hover tooltip
     * @param selected  Selected state
     * @param showLabel Show text label
     */
    protected Checkbox(@NotNull Key key, @Nullable Vec2 pos, @Nullable Vec2 anchor, @Nullable Vec2 offset, @Nullable Float rotation, @Nullable Float scale, @Nullable Vec2 size, @Nullable String label, @Nullable Component tooltip, @Nullable Boolean selected, @Nullable Boolean showLabel) {
        super(key, Type.CHECKBOX, pos, anchor, offset, rotation, scale, size);
        setLabel(label);
        setTooltip(tooltip);
        setSelected(selected);
        setShowLabel(showLabel);
    }

    /**
     * Get the text label.
     *
     * @return Text label
     */
    @Nullable
    public String getLabel() {
        return this.label;
    }

    /**
     * Set the text label.
     *
     * @param label Text label
     */
    public void setLabel(@Nullable String label) {
        this.label = label;
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
     * Get the selected state.
     *
     * @return True if selected
     */
    @Nullable
    public Boolean isSelected() {
        return this.selected;
    }

    /**
     * Set the selected state.
     *
     * @param selected Selected state
     */
    public void setSelected(@Nullable Boolean selected) {
        this.selected = selected;
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
     * Get the action to execute when the checkbox is toggled.
     *
     * @return Toggled action
     */
    @Nullable
    public OnToggled onToggled() {
        return this.onToggled;
    }

    /**
     * Set the action to execute when the checkbox is toggled.
     *
     * @param onToggled Toggled action
     */
    public void onToggled(@Nullable OnToggled onToggled) {
        this.onToggled = onToggled;
    }

    @Override
    @NotNull
    public JsonElement toJson() {
        JsonObjectWrapper json = new JsonObjectWrapper(super.toJson());
        json.addProperty("label", getLabel());
        json.addProperty("tooltip", getTooltip());
        json.addProperty("selected", isSelected());
        json.addProperty("showLabel", isShowLabel());
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
            !json.has("pos") ? null : Vec2.fromJson(json.get("pos").getAsJsonObject()),
            !json.has("anchor") ? null : Vec2.fromJson(json.get("anchor").getAsJsonObject()),
            !json.has("offset") ? null : Vec2.fromJson(json.get("offset").getAsJsonObject()),
            !json.has("rotation") ? null : json.get("rotation").getAsFloat(),
            !json.has("scale") ? null : json.get("scale").getAsFloat(),
            !json.has("size") ? null : Vec2.fromJson(json.get("size").getAsJsonObject()),
            !json.has("label") ? null : json.get("label").getAsString(),
            !json.has("tooltip") ? null : GsonComponentSerializer.gson().deserialize(json.get("tooltip").getAsString()),
            !json.has("selected") ? null : json.get("selected").getAsBoolean(),
            !json.has("showLabel") ? null : json.get("showLabel").getAsBoolean()
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
        return Objects.equals(getLabel(), other.getLabel())
            && Objects.equals(getTooltip(), other.getTooltip())
            && Objects.equals(isSelected(), other.isSelected())
            && Objects.equals(isShowLabel(), other.isShowLabel())
            && super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLabel(), getTooltip(), isSelected(), isShowLabel(), super.hashCode());
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
            + ",label=" + getLabel()
            + ",tooltip=" + getTooltip()
            + ",selected=" + isSelected()
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
        private String label;
        private Component tooltip;
        private Boolean selected;
        private Boolean showLabel;
        private OnToggled onToggled = (screen, checkbox, player, selected) -> {
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
        public String getLabel() {
            return this.label;
        }

        /**
         * Set the text label.
         *
         * @param label Text label
         * @return This builder
         */
        @NotNull
        public Builder setLabel(@Nullable String label) {
            this.label = label;
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
         * Get the selected state.
         *
         * @return True if selected
         */
        @Nullable
        public Boolean isSelected() {
            return this.selected;
        }

        /**
         * Set the selected state.
         *
         * @param selected Selected state
         * @return This builder
         */
        @NotNull
        public Builder setSelected(@Nullable Boolean selected) {
            this.selected = selected;
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
         * Get the action to execute when the checkbox is toggled.
         *
         * @return Toggled action
         */
        @Nullable
        public OnToggled onToggled() {
            return this.onToggled;
        }

        /**
         * Set the action to execute when the checkbox is toggled.
         *
         * @param onToggled Toggled action
         * @return This builder
         */
        @NotNull
        public Builder onToggled(@Nullable OnToggled onToggled) {
            this.onToggled = onToggled;
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
            Checkbox checkbox = new Checkbox(getKey(), getPos(), getAnchor(), getOffset(), getRotation(), getScale(), getSize(), getLabel(), getTooltip(), isSelected(), isShowLabel());
            checkbox.onToggled(this.onToggled);
            return checkbox;
        }
    }

    /**
     * Executable functional interface to fire when a checkbox is toggled.
     */
    @FunctionalInterface
    public interface OnToggled extends QuadConsumer<Screen, Checkbox, WrappedPlayer, Boolean> {
        /**
         * Called when a checkbox is toggled.
         *
         * @param screen   Active screen where checkbox was toggled
         * @param checkbox Checkbox that was toggled
         * @param player   Player that toggled the checkbox
         * @param selected New selected state of the checkbox
         */
        void accept(@NotNull Screen screen, @NotNull Checkbox checkbox, @NotNull WrappedPlayer player, @NotNull Boolean selected);
    }
}
