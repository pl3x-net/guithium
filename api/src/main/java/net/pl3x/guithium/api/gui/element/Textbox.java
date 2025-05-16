package net.pl3x.guithium.api.gui.element;

import java.util.Objects;
import net.kyori.adventure.text.Component;
import net.pl3x.guithium.api.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a textbox element.
 */
public class Textbox extends Rect<Textbox> {
    private String value;
    private Component suggestion;
    private Boolean bordered;
    private Boolean canLoseFocus;
    private Integer maxLength;
    private Boolean editable;
    private Integer textColor;
    private Integer uneditableTextColor;

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
        super(key, Type.TEXTBOX);
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
    public Integer getUneditableTextColor() {
        return this.uneditableTextColor;
    }

    /**
     * Get the text color if textbox is <em>not</em> editable.
     * <p>
     * If null, default text color of <code>0xFF707070</code> will be used.
     *
     * @param color Text color
     */
    public void setUneditableTextColor(@Nullable Integer color) {
        this.uneditableTextColor = color;
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
                && Objects.equals(getUneditableTextColor(), other.getUneditableTextColor());
    }

    @Override
    public int hashCode() {
        // pacifies codefactor.io
        return super.hashCode();
    }
}
