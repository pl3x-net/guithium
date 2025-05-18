package net.pl3x.guithium.api.json.adapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import net.pl3x.guithium.api.gui.element.Element;
import net.pl3x.guithium.api.json.JsonSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A custom JSON (de)serializer for the GSON library specifically for elements.
 */
public class ElementAdapter implements JsonSerializer<Element>, JsonDeserializer<Element> {
    /**
     * Create a new element adapter for gson.
     */
    public ElementAdapter() {
        // Empty constructor to pacify javadoc lint
    }

    @Override
    @NotNull
    public JsonElement serialize(@NotNull Element element, @NotNull Type type, @NotNull JsonSerializationContext context) {
        return JsonSerializable.GSON.toJsonTree(element, type);
    }

    @Override
    @Nullable
    public Element deserialize(@NotNull JsonElement json, @NotNull Type type, @NotNull JsonDeserializationContext context) throws JsonParseException {
        return Element.Type.createElement(json.getAsJsonObject());
    }
}
