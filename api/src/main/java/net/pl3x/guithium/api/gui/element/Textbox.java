package net.pl3x.guithium.api.gui.element;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Objects;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.pl3x.guithium.api.Key;
import net.pl3x.guithium.api.gui.Vec2;
import net.pl3x.guithium.api.json.JsonObjectWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an interactable textbox for user input.
 */
public class Textbox extends Rect {
    private String value;
    private Component suggestion;
    private Boolean bordered;
    private Boolean canLoseFocus;
    private Integer maxLength;
    private Boolean editable;
    private Integer textColor;
    private Integer textColorUneditable;

    /**
     * Creates a new textbox.
     *
     * @param key                 Unique identifier for button
     * @param pos                 Position of button
     * @param anchor              Anchor for button
     * @param offset              Offset of button
     * @param rotation            Rotation in degrees
     * @param scale               Scale of element
     * @param size                Size of button
     * @param value               Current value for the input
     * @param suggestion          Current suggestion on the cursor
     * @param bordered            Show background and border
     * @param canLoseFocus        Can textbox be focusable
     * @param maxLength           Max input value length
     * @param editable            Is textbox user editable
     * @param textColor           Color of text
     * @param textColorUneditable Color of text if uneditable
     */
    protected Textbox(@NotNull Key key, @Nullable Vec2 pos, @Nullable Vec2 anchor, @Nullable Vec2 offset, @Nullable Float rotation, @Nullable Float scale, @Nullable Vec2 size, @Nullable String value, @Nullable Component suggestion, @Nullable Boolean bordered, @Nullable Boolean canLoseFocus, @Nullable Integer maxLength, @Nullable Boolean editable, @Nullable Integer textColor, @Nullable Integer textColorUneditable) {
        super(key, Type.TEXTBOX, pos, anchor, offset, rotation, scale, size);
        setValue(value);
        setSuggestion(suggestion);
        setBordered(bordered);
        setCanLoseFocus(canLoseFocus);
        setMaxLength(maxLength);
        setEditable(editable);
        setTextColor(textColor);
        setTextColorUneditable(textColorUneditable);
    }

    /**
     * Get the current value for the input.
     * <p>
     * If null, default empty {@link String} will be used.
     *
     * @return Current input value
     */
    @Nullable
    public String getValue() {
        return this.value;
    }

    /**
     * Set the current value for the input.
     * <p>
     * If null, default empty {@link String} will be used.
     *
     * @param value Current input value
     */
    public void setValue(@Nullable String value) {
        this.value = value;
    }

    /**
     * Get current suggestion on the cursor.
     * <p>
     * If null, no suggestion will be used.
     *
     * @return Current suggestion
     */
    @Nullable
    public Component getSuggestion() {
        return this.suggestion;
    }

    /**
     * Set current suggestion on the cursor.
     * <p>
     * If null, no suggestion will be used.
     *
     * @param suggestion Current suggestion
     */
    public void setSuggestion(@Nullable Component suggestion) {
        this.suggestion = suggestion;
    }

    /**
     * Get whether the input box has a background and border.
     * <p>
     * If null, default <code>true</code> will be used.
     *
     * @return True if bordered
     */
    @Nullable
    public Boolean isBordered() {
        return this.bordered;
    }

    /**
     * Set whether the input box has a background and border.
     * <p>
     * If null, default <code>true</code> will be used.
     *
     * @param bordered True if bordered
     */
    public void setBordered(@Nullable Boolean bordered) {
        this.bordered = bordered;
    }

    /**
     * Get whether the textbox can gain and lose focus.
     * <p>
     * If null, default <code>true</code> will be used.
     *
     * @return True to gain and lose focus
     */
    @Nullable
    public Boolean canLoseFocus() {
        return this.canLoseFocus;
    }

    /**
     * Set whether the textbox can gain and lose focus.
     * <p>
     * If null, default <code>true</code> will be used.
     *
     * @param canLoseFocus True to gain and lose focus
     */
    public void setCanLoseFocus(@Nullable Boolean canLoseFocus) {
        this.canLoseFocus = canLoseFocus;
    }

    /**
     * Get the maximum length of the input value.
     * <p>
     * If null, default maximum length of <code>32</code> will be used.
     *
     * @return Max length of input value
     */
    @Nullable
    public Integer getMaxLength() {
        return this.maxLength;
    }

    /**
     * Set the maximum length of the input value.
     * <p>
     * If null, default maximum length of <code>32</code> will be used.
     *
     * @param maxLength Max length of input value
     */
    public void setMaxLength(@Nullable Integer maxLength) {
        this.maxLength = maxLength;
    }

    /**
     * Get whether the textbox can be edited by the player.
     * <p>
     * If null, default <code>true</code> will be used.
     *
     * @return True to let player edit input value
     */
    @Nullable
    public Boolean isEditable() {
        return this.editable;
    }

    /**
     * Set whether the textbox can be edited by the player.
     * <p>
     * If null, default <code>true</code> will be used.
     *
     * @param editable True to let player edit input value
     */
    public void setEditable(@Nullable Boolean editable) {
        this.editable = editable;
    }

    /**
     * Get the text color if textbox is editable.
     * <p>
     * If null, default text color of <code>0xFFFFFFFF</code> will be used.
     *
     * @return Text color
     */
    @Nullable
    public Integer getTextColor() {
        return this.textColor;
    }

    /**
     * Get the text color if textbox is editable.
     * <p>
     * If null, default text color of <code>0xFFFFFFFF</code> will be used.
     *
     * @param color Text color
     */
    public void setTextColor(@Nullable Integer color) {
        this.textColor = color;
    }

    /**
     * Get the text color if textbox is <em>not</em> editable.
     * <p>
     * If null, default text color of <code>0xFF707070</code> will be used.
     *
     * @return Text color
     */
    @Nullable
    public Integer getTextColorUneditable() {
        return this.textColorUneditable;
    }

    /**
     * Get the text color if textbox is <em>not</em> editable.
     * <p>
     * If null, default text color of <code>0xFF707070</code> will be used.
     *
     * @param color Text color
     */
    public void setTextColorUneditable(@Nullable Integer color) {
        this.textColorUneditable = color;
    }

    @Override
    @NotNull
    public JsonElement toJson() {
        JsonObjectWrapper json = new JsonObjectWrapper(super.toJson());
        json.addProperty("value", getValue());
        json.addProperty("suggestion", getSuggestion());
        json.addProperty("bordered", isBordered());
        json.addProperty("canLoseFocus", canLoseFocus());
        json.addProperty("maxLength", getMaxLength());
        json.addProperty("editable", isEditable());
        json.addProperty("textColor", getTextColor());
        json.addProperty("textColorUneditable", getTextColorUneditable());
        return json.getJsonObject();
    }

    /**
     * Create a new textbox element from Json.
     *
     * @param json Json representation of a textbox element
     * @return A new textbox element
     */
    @NotNull
    public static Textbox fromJson(@NotNull JsonObject json) {
        Preconditions.checkArgument(json.has("key"), "Key cannot be null");
        return new Textbox(
                Key.of(json.get("key").getAsString()),
                !json.has("pos") ? null : Vec2.fromJson(json.get("pos").getAsJsonObject()),
                !json.has("anchor") ? null : Vec2.fromJson(json.get("anchor").getAsJsonObject()),
                !json.has("offset") ? null : Vec2.fromJson(json.get("offset").getAsJsonObject()),
                !json.has("rotation") ? null : json.get("rotation").getAsFloat(),
                !json.has("scale") ? null : json.get("scale").getAsFloat(),
                !json.has("size") ? null : Vec2.fromJson(json.get("size").getAsJsonObject()),
                !json.has("value") ? null : json.get("value").getAsString(),
                !json.has("suggestion") ? null : GsonComponentSerializer.gson().deserialize(json.get("suggestion").getAsString()),
                !json.has("bordered") ? null : json.get("bordered").getAsBoolean(),
                !json.has("canLoseFocus") ? null : json.get("canLoseFocus").getAsBoolean(),
                !json.has("maxLength") ? null : json.get("maxLength").getAsInt(),
                !json.has("editable") ? null : json.get("editable").getAsBoolean(),
                !json.has("textColor") ? null : json.get("textColor").getAsInt(),
                !json.has("textColorUneditable") ? null : json.get("textColorUneditable").getAsInt()
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
        Textbox other = (Textbox) o;
        return Objects.equals(getValue(), other.getValue())
                && Objects.equals(getSuggestion(), other.getSuggestion())
                && Objects.equals(isBordered(), other.isBordered())
                && Objects.equals(canLoseFocus(), other.canLoseFocus())
                && Objects.equals(getMaxLength(), other.getMaxLength())
                && Objects.equals(isEditable(), other.isEditable())
                && Objects.equals(getTextColor(), other.getTextColor())
                && Objects.equals(getTextColorUneditable(), other.getTextColorUneditable())
                && super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue(), getSuggestion(), isBordered(), canLoseFocus(), getMaxLength(), isEditable(), getTextColor(), getTextColorUneditable(), super.hashCode());
    }

    @Override
    @NotNull
    public String toString() {
        return String.format("Textbox{%s}", getPropertiesAsString());
    }

    @Override
    @NotNull
    protected String getPropertiesAsString() {
        return super.getPropertiesAsString()
                + ",value=" + getValue()
                + ",suggestion=" + getSuggestion()
                + ",bordered=" + isBordered()
                + ",canLoseFocus=" + canLoseFocus()
                + ",maxLength=" + getMaxLength()
                + ",editable=" + isEditable()
                + ",textColor=" + getTextColor()
                + ",textColorUneditable=" + getTextColorUneditable();
    }

    /**
     * Create a new textbox element builder.
     *
     * @param key Unique identifying key for the textbox element
     * @return New textbox element builder
     */
    @NotNull
    public static Builder builder(@NotNull String key) {
        return new Builder(key);
    }

    /**
     * Create a new textbox element builder.
     *
     * @param key Unique identifying key for the textbox element
     * @return New textbox element builder
     */
    @NotNull
    public static Builder builder(@NotNull Key key) {
        return new Builder(key);
    }

    /**
     * Builder for textbox elements.
     */
    public static class Builder extends Rect.Builder<Builder> {
        private String value;
        private Component suggestion;
        private Boolean bordered;
        private Boolean canLoseFocus;
        private Integer maxLength;
        private Boolean editable;
        private Integer textColor;
        private Integer textColorUneditable;

        /**
         * Create a new textbox element builder.
         *
         * @param key Unique identifying key for the textbox element
         */
        public Builder(@NotNull String key) {
            this(Key.of(key));
        }

        /**
         * Create a new textbox element builder.
         *
         * @param key Unique identifying key for the textbox element
         */
        public Builder(@NotNull Key key) {
            super(key);
        }

        /**
         * Get the current value for the input.
         * <p>
         * If null, default empty {@link String} will be used.
         *
         * @return Current input value
         */
        @Nullable
        public String getValue() {
            return this.value;
        }

        /**
         * Set the current value for the input.
         * <p>
         * If null, default empty {@link String} will be used.
         *
         * @param value Current input value
         * @return This builder
         */
        @NotNull
        public Builder setValue(@Nullable String value) {
            this.value = value;
            return this;
        }

        /**
         * Get current suggestion on the cursor.
         * <p>
         * If null, no suggestion will be used.
         *
         * @return Current suggestion
         */
        @Nullable
        public Component getSuggestion() {
            return this.suggestion;
        }

        /**
         * Set current suggestion on the cursor.
         * <p>
         * If null, no suggestion will be used.
         *
         * @param suggestion Current suggestion
         * @return This builder
         */
        @NotNull
        public Builder setSuggestion(@Nullable Component suggestion) {
            this.suggestion = suggestion;
            return this;
        }

        /**
         * Get whether the input box has a background and border.
         * <p>
         * If null, default <code>true</code> will be used.
         *
         * @return True if bordered
         */
        @Nullable
        public Boolean isBordered() {
            return this.bordered;
        }

        /**
         * Set whether the input box has a background and border.
         * <p>
         * If null, default <code>true</code> will be used.
         *
         * @param bordered True if bordered
         * @return This builder
         */
        @NotNull
        public Builder setBordered(@Nullable Boolean bordered) {
            this.bordered = bordered;
            return this;
        }

        /**
         * Get whether the textbox can gain and lose focus.
         * <p>
         * If null, default <code>true</code> will be used.
         *
         * @return True to gain and lose focus
         */
        @Nullable
        public Boolean canLoseFocus() {
            return this.canLoseFocus;
        }

        /**
         * Set whether the textbox can gain and lose focus.
         * <p>
         * If null, default <code>true</code> will be used.
         *
         * @param canLoseFocus True to gain and lose focus
         * @return This builder
         */
        @NotNull
        public Builder setCanLoseFocus(@Nullable Boolean canLoseFocus) {
            this.canLoseFocus = canLoseFocus;
            return this;
        }

        /**
         * Get the maximum length of the input value.
         * <p>
         * If null, default maximum length of <code>32</code> will be used.
         *
         * @return Max length of input value
         */
        @Nullable
        public Integer getMaxLength() {
            return this.maxLength;
        }

        /**
         * Set the maximum length of the input value.
         * <p>
         * If null, default maximum length of <code>32</code> will be used.
         *
         * @param maxLength Max length of input value
         * @return This builder
         */
        @NotNull
        public Builder setMaxLength(@Nullable Integer maxLength) {
            this.maxLength = maxLength;
            return this;
        }

        /**
         * Get whether the textbox can be edited by the player.
         * <p>
         * If null, default <code>true</code> will be used.
         *
         * @return True to let player edit input value
         */
        @Nullable
        public Boolean isEditable() {
            return this.editable;
        }

        /**
         * Set whether the textbox can be edited by the player.
         * <p>
         * If null, default <code>true</code> will be used.
         *
         * @param editable True to let player edit input value
         * @return This builder
         */
        @NotNull
        public Builder setEditable(@Nullable Boolean editable) {
            this.editable = editable;
            return this;
        }

        /**
         * Get the text color if textbox is editable.
         * <p>
         * If null, default text color of <code>0xFFFFFFFF</code> will be used.
         *
         * @return Text color
         */
        @Nullable
        public Integer getTextColor() {
            return this.textColor;
        }

        /**
         * Get the text color if textbox is editable.
         * <p>
         * If null, default text color of <code>0xFFFFFFFF</code> will be used.
         *
         * @param color Text color
         * @return This builder
         */
        @NotNull
        public Builder setTextColor(@Nullable Integer color) {
            this.textColor = color;
            return this;
        }

        /**
         * Get the text color if textbox is <em>not</em> editable.
         * <p>
         * If null, default text color of <code>0xFF707070</code> will be used.
         *
         * @return Text color
         */
        @Nullable
        public Integer getTextColorUneditable() {
            return this.textColorUneditable;
        }

        /**
         * Get the text color if textbox is <em>not</em> editable.
         * <p>
         * If null, default text color of <code>0xFF707070</code> will be used.
         *
         * @param color Text color
         * @return This builder
         */
        @NotNull
        public Builder setTextColorUneditable(@Nullable Integer color) {
            this.textColorUneditable = color;
            return this;
        }

        /**
         * Build a new textbox element from the current properties in this builder.
         *
         * @return New textbox element
         */
        @Override
        @NotNull
        public Textbox build() {
            return new Textbox(getKey(), getPos(), getAnchor(), getOffset(), getRotation(), getScale(), getSize(), getValue(), getSuggestion(), isBordered(), canLoseFocus(), getMaxLength(), isEditable(), getTextColor(), getTextColorUneditable());
        }
    }
}
