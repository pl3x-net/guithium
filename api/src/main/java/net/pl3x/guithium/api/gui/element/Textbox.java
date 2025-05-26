package net.pl3x.guithium.api.gui.element;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Objects;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.pl3x.guithium.api.json.JsonObjectWrapper;
import net.pl3x.guithium.api.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a textbox element.
 */
public class Textbox extends Rect<Textbox> implements ValueElement<Textbox, String> {
    private String value;
    private Component suggestion;
    private Boolean bordered;
    private Boolean canLoseFocus;
    private Integer maxLength;
    private Boolean editable;
    private Integer textColor;
    private Integer uneditableTextColor;

    private OnChange<Textbox, String> onChange = (screen, textbox, player, value) -> {
    };

    /**
     * Create a new textbox element.
     *
     * @param key Unique identifier
     */
    public Textbox(@NotNull String key) {
        this(Key.of(key));
    }

    /**
     * Create a new textbox element.
     *
     * @param key Unique identifier
     */
    public Textbox(@NotNull Key key) {
        super(key);
    }

    /**
     * Create a new textbox element.
     *
     * @param key Unique identifier
     * @return New textbox element
     */
    @NotNull
    public static Textbox of(@NotNull String key) {
        return of(Key.of(key));
    }

    /**
     * Create a new textbox element.
     *
     * @param key Unique identifier
     * @return New textbox element
     */
    @NotNull
    public static Textbox of(@NotNull Key key) {
        return new Textbox(key);
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
     * @return This textbox
     */
    @NotNull
    public Textbox setSuggestion(@Nullable Component suggestion) {
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
     * @return This textbox
     */
    @NotNull
    public Textbox setBordered(@Nullable Boolean bordered) {
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
     * @return This textbox
     */
    @NotNull
    public Textbox setCanLoseFocus(@Nullable Boolean canLoseFocus) {
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
     * @return This textbox
     */
    @NotNull
    public Textbox setMaxLength(@Nullable Integer maxLength) {
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
     * @return This textbox
     */
    @NotNull
    public Textbox setEditable(@Nullable Boolean editable) {
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
     * @return This textbox
     */
    @NotNull
    public Textbox setTextColor(@Nullable Integer color) {
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
    public Integer getUneditableTextColor() {
        return this.uneditableTextColor;
    }

    /**
     * Get the text color if textbox is <em>not</em> editable.
     * <p>
     * If null, default text color of <code>0xFF707070</code> will be used.
     *
     * @param color Text color
     * @return This textbox
     */
    @NotNull
    public Textbox setUneditableTextColor(@Nullable Integer color) {
        this.uneditableTextColor = color;
        return this;
    }

    @Override
    @NotNull
    public String getValue() {
        return this.value == null ? "" : this.value;
    }

    @Override
    @NotNull
    public Textbox setValue(@NotNull String value) {
        this.value = value;
        return this;
    }

    @Override
    @Nullable
    public OnChange<Textbox, String> onChange() {
        return this.onChange;
    }

    @Override
    @NotNull
    public Textbox onChange(@Nullable OnChange<Textbox, String> onChange) {
        this.onChange = onChange;
        return this;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        Textbox other = (Textbox) obj;
        return Objects.equals(getValue(), other.getValue())
                && Objects.equals(getSuggestion(), other.getSuggestion())
                && Objects.equals(isBordered(), other.isBordered())
                && Objects.equals(canLoseFocus(), other.canLoseFocus())
                && Objects.equals(getMaxLength(), other.getMaxLength())
                && Objects.equals(isEditable(), other.isEditable())
                && Objects.equals(getTextColor(), other.getTextColor())
                && Objects.equals(getUneditableTextColor(), other.getUneditableTextColor())
                && Objects.equals(onChange(), other.onChange());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                super.hashCode(),
                getValue(),
                getSuggestion(),
                isBordered(),
                canLoseFocus(),
                getMaxLength(),
                isEditable(),
                getTextColor(),
                getUneditableTextColor(),
                onChange()
        );
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
        json.addProperty("uneditableTextColor", getUneditableTextColor());
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
        Textbox textbox = new Textbox(Key.of(json.get("key").getAsString()));
        Rect.fromJson(textbox, json);
        textbox.setValue(!json.has("value") ? "" : json.get("value").getAsString());
        textbox.setSuggestion(!json.has("suggestion") ? null : GsonComponentSerializer.gson().deserialize(json.get("suggestion").getAsString()));
        textbox.setBordered(!json.has("bordered") ? null : json.get("bordered").getAsBoolean());
        textbox.setCanLoseFocus(!json.has("canLoseFocus") ? null : json.get("canLoseFocus").getAsBoolean());
        textbox.setMaxLength(!json.has("maxLength") ? null : json.get("maxLength").getAsInt());
        textbox.setEditable(!json.has("editable") ? null : json.get("editable").getAsBoolean());
        textbox.setTextColor(!json.has("textColor") ? null : json.get("textColor").getAsInt());
        textbox.setUneditableTextColor(!json.has("uneditableTextColor") ? null : json.get("uneditableTextColor").getAsInt());
        return textbox;
    }
}
