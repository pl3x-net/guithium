package net.pl3x.guithium.api.gui.element;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.pl3x.guithium.api.Key;
import net.pl3x.guithium.api.gui.Vec2;
import net.pl3x.guithium.api.json.JsonObjectWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Represents some drawable text.
 */
public class Text extends AbstractElement {
    private Component text;
    private Boolean shadow;

    /**
     * Create a new text element.
     *
     * @param key      Unique identifier for text
     * @param text     Text component
     * @param pos      Position of text
     * @param anchor   Anchor for text
     * @param offset   Offset of text
     * @param rotation Rotation in degrees
     * @param scale    Scale of element
     * @param shadow   Text has shadow
     */
    protected Text(@NotNull Key key, @Nullable Component text, @Nullable Vec2 pos, @Nullable Vec2 anchor, @Nullable Vec2 offset, @Nullable Float scale, @Nullable Float rotation, @Nullable Boolean shadow) {
        super(key, Type.TEXT, pos, anchor, offset, rotation, scale);
        setText(text);
        setShadow(shadow);
    }

    /**
     * Get the underlying text component.
     * <p>
     * If null, default empty component will be used.
     *
     * @return Text component
     */
    @Nullable
    public Component getText() {
        return this.text;
    }

    /**
     * Set the underlying text component.
     * <p>
     * If null, default empty component will be used.
     *
     * @param text Text component
     */
    public void setText(@Nullable Component text) {
        this.text = text;
    }

    /**
     * Get whether this text has a shadow.
     * <p>
     * If null, default <code>true</code> will be used.
     *
     * @return True if text has shadow
     */
    @Nullable
    public Boolean hasShadow() {
        return this.shadow;
    }

    /**
     * Set whether this text has a shadow.
     * <p>
     * If null, default <code>true</code> will be used.
     *
     * @param shadow True if text has shadow
     */
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

    /**
     * Create a new text element from Json.
     *
     * @param json Json representation of a text element
     * @return A new text element
     */
    @NotNull
    public static Text fromJson(@NotNull JsonObject json) {
        Preconditions.checkArgument(json.has("key"), "Key cannot be null");
        return new Text(
            Key.of(json.get("key").getAsString()),
            !json.has("text") ? null : GsonComponentSerializer.gson().deserialize(json.get("text").getAsString()),
            !json.has("pos") ? null : Vec2.fromJson(json.get("pos").getAsJsonObject()),
            !json.has("anchor") ? null : Vec2.fromJson(json.get("anchor").getAsJsonObject()),
            !json.has("offset") ? null : Vec2.fromJson(json.get("offset").getAsJsonObject()),
            !json.has("rotation") ? null : json.get("rotation").getAsFloat(),
            !json.has("scale") ? null : json.get("scale").getAsFloat(),
            !json.has("shadow") ? null : json.get("shadow").getAsBoolean()
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
    @NotNull
    public String toString() {
        return String.format("Text{%s}", getPropertiesAsString());
    }

    @Override
    @NotNull
    protected String getPropertiesAsString() {
        return super.getPropertiesAsString()
            + ",text=" + getText()
            + ",shadow=" + hasShadow();
    }

    /**
     * Create a new text element builder.
     *
     * @param key Unique identifying key for the text element
     * @return New text element builder
     */
    @NotNull
    public static Builder builder(@NotNull String key) {
        return new Builder(key);
    }

    /**
     * Create a new text element builder.
     *
     * @param key Unique identifying key for the text element
     * @return New text element builder
     */
    @NotNull
    public static Builder builder(@NotNull Key key) {
        return new Builder(key);
    }

    /**
     * Builder for text elements.
     */
    public static class Builder extends AbstractBuilder<Builder> {
        private Component text;
        private Boolean shadow;

        /**
         * Create a new text element builder.
         *
         * @param key Unique identifying key for the text element
         */
        public Builder(@NotNull String key) {
            this(Key.of(key));
        }

        /**
         * Create a new text element builder.
         *
         * @param key Unique identifying key for the text element
         */
        public Builder(@NotNull Key key) {
            super(key);
        }

        /**
         * Get the underlying text component.
         * <p>
         * If null, default empty component will be used.
         *
         * @return Text component
         */
        @Nullable
        public Component getText() {
            return text;
        }

        /**
         * Set the underlying text component.
         * <p>
         * If null, default empty component will be used.
         *
         * @param text Text component
         * @return This builder
         */
        @NotNull
        public Builder setText(@Nullable Component text) {
            this.text = text;
            return this;
        }

        /**
         * Get whether this text has a shadow.
         * <p>
         * If null, default <code>true</code> will be used.
         *
         * @return True if text has shadow
         */
        @Nullable
        public Boolean hasShadow() {
            return shadow;
        }

        /**
         * Set whether this text has a shadow.
         * <p>
         * If null, default <code>true</code> will be used.
         *
         * @param shadow True if text has shadow
         * @return This builder
         */
        @NotNull
        public Builder setShadow(@Nullable Boolean shadow) {
            this.shadow = shadow;
            return this;
        }

        /**
         * Build a new text element from the current properties in this builder.
         *
         * @return New text element
         */
        @Override
        @NotNull
        public Text build() {
            return new Text(getKey(), getText(), getPos(), getAnchor(), getOffset(), getRotation(), getScale(), hasShadow());
        }
    }
}
