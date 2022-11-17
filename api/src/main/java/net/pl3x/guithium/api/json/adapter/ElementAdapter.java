package net.pl3x.guithium.api.json.adapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import net.pl3x.guithium.api.gui.element.Element;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;

public class ElementAdapter implements JsonSerializer<Element>, JsonDeserializer<Element> {
    @Override
    @NotNull
    public JsonElement serialize(@NotNull Element element, @NotNull Type type, @NotNull JsonSerializationContext context) {
        return element.toJson();
    }

    @Override
    public Element deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return Element.createElement(json.getAsJsonObject());
    }
}
