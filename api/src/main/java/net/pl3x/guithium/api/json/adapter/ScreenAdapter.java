package net.pl3x.guithium.api.json.adapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import net.pl3x.guithium.api.gui.Screen;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an adapter for serializing screens to/from Gson.
 */
public class ScreenAdapter implements JsonSerializer<Screen>, JsonDeserializer<Screen> {
    @Override
    @NotNull
    public JsonElement serialize(@NotNull Screen screen, @NotNull Type type, @NotNull JsonSerializationContext context) {
        return screen.toJson();
    }

    @Override
    @Nullable
    public Screen deserialize(@NotNull JsonElement json, @NotNull Type typeOfT, @NotNull JsonDeserializationContext context) throws JsonParseException {
        return Screen.fromJson(json.getAsJsonObject());
    }
}
