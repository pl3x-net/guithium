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
 * A custom JSON (de)serializer for the GSON library specifically for screens.
 */
public class ScreenAdapter implements JsonSerializer<Screen>, JsonDeserializer<Screen> {
    /**
     * Create a new screen adapter for gson.
     */
    public ScreenAdapter() {
        // Empty constructor to pacify javadoc lint
    }

    @Override
    @NotNull
    public JsonElement serialize(@NotNull Screen screen, @NotNull Type type, @NotNull JsonSerializationContext context) {
        return screen.toJson();
    }

    @Override
    @Nullable
    public Screen deserialize(@NotNull JsonElement json, @NotNull Type type, @NotNull JsonDeserializationContext context) throws JsonParseException {
        return Screen.fromJson(json.getAsJsonObject());
    }
}
