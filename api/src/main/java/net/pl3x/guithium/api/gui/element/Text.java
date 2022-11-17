package net.pl3x.guithium.api.gui.element;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.pl3x.guithium.api.json.JsonObjectWrapper;
import net.pl3x.guithium.api.Key;
import net.pl3x.guithium.api.gui.Point;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class Text extends AbstractElement {
    private String text;
    private Boolean shadow;

    public Text(@NotNull Key key, @Nullable String text, @Nullable Point pos, @Nullable Point anchor, @Nullable Point offset, @Nullable Boolean shadow, @Nullable Float scale, @Nullable Double zIndex) {
        super(key, "text", pos, anchor, offset, scale, zIndex);
        setText(text);
        setShadow(shadow);
    }

    @Nullable
    public String getText() {
        return this.text;
    }

    public void setText(@Nullable String text) {
        this.text = text;
    }

    @Nullable
    public Boolean hasShadow() {
        return this.shadow;
    }

    public void setShadow(@Nullable Boolean shadow) {
        this.shadow = shadow;
    }

    @Override
    @NotNull
    public JsonElement toJson() {
        JsonObjectWrapper json = new JsonObjectWrapper(super.toJson());
        json.addProperty("text", getText());
        json.addProperty("shadow", hasShadow());
        return json.getJsonObject();
    }

    @NotNull
    public static Text fromJson(@NotNull JsonObject json) {
        Preconditions.checkArgument(json.has("key"), "Key cannot be null");
        return new Text(
            Key.of(json.get("key").getAsString()),
            !json.has("text") ? null : json.get("text").getAsString(),
            !json.has("pos") ? null : Point.fromJson(json.get("pos").getAsJsonObject()),
            !json.has("anchor") ? null : Point.fromJson(json.get("anchor").getAsJsonObject()),
            !json.has("offset") ? null : Point.fromJson(json.get("offset").getAsJsonObject()),
            !json.has("shadow") ? null : json.get("shadow").getAsBoolean(),
            !json.has("scale") ? null : json.get("scale").getAsFloat(),
            !json.has("zIndex") ? null : json.get("zIndex").getAsDouble()
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
        Text other = (Text) o;
        return Objects.equals(getText(), other.getText())
            && Objects.equals(hasShadow(), other.hasShadow())
            && super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getText(), hasShadow(), super.hashCode());
    }

    @Override
    public String toString() {
        return String.format("%s{%s}", "Text", getPropertiesAsString());
    }

    @Override
    @NotNull
    public String getPropertiesAsString() {
        return super.getPropertiesAsString()
            + ",text=" + getText()
            + ",shadow=" + hasShadow();
    }

    public static Builder builder(@NotNull String key) {
        return new Builder(key);
    }

    public static Builder builder(@NotNull Key key) {
        return new Builder(key);
    }

    public static class Builder extends AbstractBuilder<Builder> {
        private String text;
        private Boolean shadow;

        public Builder(@NotNull String key) {
            this(Key.of(key));
        }

        public Builder(@NotNull Key key) {
            super(key);
        }

        @Nullable
        public String getText() {
            return text;
        }

        @NotNull
        public Builder setText(@Nullable String text) {
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
            return new Text(getKey(), getText(), getPos(), getAnchor(), getOffset(), hasShadow(), getScale(), getZIndex());
        }
    }
}
