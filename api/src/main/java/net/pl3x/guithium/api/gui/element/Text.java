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
 * Represents a text element.
 */
public class Text extends AbstractElement<Text> {
    private Component text;
    private Boolean shadow;

    /**
     * Create a new text element.
     *
     * @param key Unique identifier
     */
    public Text(@NotNull String key) {
        this(Key.of(key));
    }

    /**
     * Create a new text element.
     *
     * @param key Unique identifier
     */
    public Text(@NotNull Key key) {
        super(key);
    }

    /**
     * Create a new text element.
     *
     * @param key Unique identifier
     * @return New text element
     */
    @NotNull
    public static Text of(@NotNull String key) {
        return of(Key.of(key));
    }

    /**
     * Create a new text element.
     *
     * @param key Unique identifier
     * @return New text element
     */
    @NotNull
    public static Text of(@NotNull Key key) {
        return new Text(key);
    }

    /**
     * Get the text component.
     *
     * @return Text component
     */
    @Nullable
    public Component getText() {
        return this.text;
    }

    /**
     * Set the text component.
     *
     * @param text Component to set
     * @return This text
     */
    @NotNull
    public Text setText(@Nullable Component text) {
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
        return this.shadow;
    }

    /**
     * Set whether to draw a drop shadow behind the text.
     *
     * @param shadow {@code true} if text should have shadow, otherwise {@code false}
     * @return This text
     */
    @NotNull
    public Text setShadow(@Nullable Boolean shadow) {
        this.shadow = shadow;
        return this;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        Text other = (Text) obj;
        return Objects.equals(getText(), other.getText())
                && Objects.equals(hasShadow(), other.hasShadow());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                super.hashCode(),
                getText(),
                hasShadow()
        );
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
        Text text = new Text(Key.of(json.get("key").getAsString()));
        AbstractElement.fromJson(text, json);
        text.setText(!json.has("text") ? null : GsonComponentSerializer.gson().deserialize(json.get("text").getAsString()));
        text.setShadow(!json.has("shadow") ? null : json.get("shadow").getAsBoolean());
        return text;
    }
}
