package net.pl3x.guithium.api.json;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.FormattingStyle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.LongSerializationPolicy;
import com.google.gson.Strictness;
import java.lang.reflect.Type;
import net.kyori.adventure.text.Component;
import net.pl3x.guithium.api.gui.Screen;
import net.pl3x.guithium.api.gui.element.Element;
import net.pl3x.guithium.api.gui.element.Rect;
import net.pl3x.guithium.api.gui.element.Text;
import net.pl3x.guithium.api.json.adapter.ComponentAdapter;
import net.pl3x.guithium.api.json.adapter.ElementAdapter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an object that can be (de)serialized to/from JSON.
 */
public interface JsonSerializable {
    /**
     * Gson instance for (de)serializing objects to/from JSON.
     */
    Gson GSON = new GsonBuilder()
            .disableHtmlEscaping()
            .setStrictness(Strictness.LENIENT)
            .setFormattingStyle(FormattingStyle.COMPACT)
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .setLongSerializationPolicy(LongSerializationPolicy.DEFAULT)
            .registerTypeAdapter(Component.class, new ComponentAdapter())
            .registerTypeAdapter(Element.class, new ElementAdapter())
            .registerTypeAdapter(Screen.class, new Adapter<Screen>())
            .registerTypeAdapter(Rect.class, new Adapter<Rect>())
            .registerTypeAdapter(Text.class, new Adapter<Text>())
            .create();

    /**
     * A custom JSON (de)serializer for the GSON library specifically for json serializable objects.
     *
     * @param <T> Type of json serializable object
     */
    class Adapter<T extends JsonSerializable> implements JsonSerializer<T>, JsonDeserializer<T> {
        /**
         * Create a new json serializable adapter for gson.
         */
        public Adapter() {
            // Empty constructor to pacify javadoc lint
        }

        @Override
        @NotNull
        public JsonElement serialize(@NotNull T src, @NotNull Type type, @NotNull JsonSerializationContext context) {
            return GSON.toJsonTree(src, type);
        }

        @Override
        @Nullable
        public T deserialize(@NotNull JsonElement json, @NotNull Type type, @NotNull JsonDeserializationContext context) throws JsonParseException {
            return GSON.fromJson(json, type);
        }
    }
}
