package net.pl3x.guithium.api.gui.element;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.pl3x.guithium.api.Key;
import net.pl3x.guithium.api.gui.Point;
import net.pl3x.guithium.api.json.JsonObjectWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class Textbox extends Rect {
    private String value;
    private String suggestion;
    private Boolean bordered;
    private Boolean canLoseFocus;
    private Integer maxLength;
    private Boolean editable;
    private Integer textColor;
    private Integer textColorUneditable;

    protected Textbox(@NotNull Key key, @Nullable Point pos, @Nullable Point anchor, @Nullable Point offset, @Nullable Point size, @Nullable String value, @Nullable String suggestion, @Nullable Boolean bordered, @Nullable Boolean canLoseFocus, @Nullable Integer maxLength, @Nullable Boolean editable, @Nullable Integer textColor, @Nullable Integer textColorUneditable) {
        super(key, Type.TEXTBOX, pos, anchor, offset, size);
        setValue(value);
        setSuggestion(suggestion);
        setBordered(bordered);
        setCanLoseFocus(canLoseFocus);
        setMaxLength(maxLength);
        setEditable(editable);
        setTextColor(textColor);
        setTextColorUneditable(textColorUneditable);
    }

    @Nullable
    public String getValue() {
        return this.value;
    }

    public void setValue(@Nullable String value) {
        this.value = value;
    }

    @Nullable
    public String getSuggestion() {
        return this.suggestion;
    }

    public void setSuggestion(@Nullable String suggestion) {
        this.suggestion = suggestion;
    }

    @Nullable
    public Boolean isBordered() {
        return this.bordered;
    }

    public void setBordered(@Nullable Boolean bordered) {
        this.bordered = bordered;
    }

    @Nullable
    public Boolean canLoseFocus() {
        return this.canLoseFocus;
    }

    public void setCanLoseFocus(@Nullable Boolean canLoseFocus) {
        this.canLoseFocus = canLoseFocus;
    }

    @Nullable
    public Integer getMaxLength() {
        return this.maxLength;
    }

    public void setMaxLength(@Nullable Integer maxLength) {
        this.maxLength = maxLength;
    }

    @Nullable
    public Boolean isEditable() {
        return this.editable;
    }

    public void setEditable(@Nullable Boolean editable) {
        this.editable = editable;
    }

    @Nullable
    public Integer getTextColor() {
        return this.textColor;
    }

    public void setTextColor(@Nullable Integer textColor) {
        this.textColor = textColor;
    }

    @Nullable
    public Integer getTextColorUneditable() {
        return this.textColorUneditable;
    }

    public void setTextColorUneditable(@Nullable Integer textColorUneditable) {
        this.textColorUneditable = textColorUneditable;
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

    @NotNull
    public static Textbox fromJson(@NotNull JsonObject json) {
        Preconditions.checkArgument(json.has("key"), "Key cannot be null");
        return new Textbox(
            Key.of(json.get("key").getAsString()),
            !json.has("pos") ? null : Point.fromJson(json.get("pos").getAsJsonObject()),
            !json.has("anchor") ? null : Point.fromJson(json.get("anchor").getAsJsonObject()),
            !json.has("offset") ? null : Point.fromJson(json.get("offset").getAsJsonObject()),
            !json.has("size") ? null : Point.fromJson(json.get("size").getAsJsonObject()),
            !json.has("value") ? null : json.get("value").getAsString(),
            !json.has("suggestion") ? null : json.get("suggestion").getAsString(),
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
        return String.format("%s{%s}", "Textbox", getPropertiesAsString());
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

    @NotNull
    public static Builder builder(@NotNull String key) {
        return new Builder(key);
    }

    @NotNull
    public static Builder builder(@NotNull Key key) {
        return new Builder(key);
    }

    public static class Builder extends Rect.Builder<Builder> {
        private String value;
        private String suggestion;
        private Boolean bordered;
        private Boolean canLoseFocus;
        private Integer maxLength;
        private Boolean editable;
        private Integer textColor;
        private Integer textColorUneditable;

        public Builder(@NotNull String key) {
            this(Key.of(key));
        }

        public Builder(@NotNull Key key) {
            super(key);
        }

        @Nullable
        public String getValue() {
            return this.value;
        }

        @NotNull
        public Builder setValue(@Nullable String value) {
            this.value = value;
            return this;
        }

        @Nullable
        public String getSuggestion() {
            return this.suggestion;
        }

        @NotNull
        public Builder setSuggestion(@Nullable String suggestion) {
            this.suggestion = suggestion;
            return this;
        }

        @Nullable
        public Boolean isBordered() {
            return this.bordered;
        }

        @NotNull
        public Builder setBordered(@Nullable Boolean bordered) {
            this.bordered = bordered;
            return this;
        }

        @Nullable
        public Boolean canLoseFocus() {
            return this.canLoseFocus;
        }

        @NotNull
        public Builder setCanLoseFocus(@Nullable Boolean canLoseFocus) {
            this.canLoseFocus = canLoseFocus;
            return this;
        }

        @Nullable
        public Integer getMaxLength() {
            return this.maxLength;
        }

        @NotNull
        public Builder setMaxLength(@Nullable Integer maxLength) {
            this.maxLength = maxLength;
            return this;
        }

        @Nullable
        public Boolean isEditable() {
            return this.editable;
        }

        @NotNull
        public Builder setEditable(@Nullable Boolean editable) {
            this.editable = editable;
            return this;
        }

        @Nullable
        public Integer getTextColor() {
            return this.textColor;
        }

        @NotNull
        public Builder setTextColor(@Nullable Integer textColor) {
            this.textColor = textColor;
            return this;
        }

        @Nullable
        public Integer getTextColorUneditable() {
            return this.textColorUneditable;
        }

        @NotNull
        public Builder setTextColorUneditable(@Nullable Integer textColorUneditable) {
            this.textColorUneditable = textColorUneditable;
            return this;
        }

        @Override
        @NotNull
        public Textbox build() {
            return new Textbox(getKey(), getPos(), getAnchor(), getOffset(), getSize(), getValue(), getSuggestion(), isBordered(), canLoseFocus(), getMaxLength(), isEditable(), getTextColor(), getTextColorUneditable());
        }
    }
}
