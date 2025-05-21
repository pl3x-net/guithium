package net.pl3x.guithium.api.json;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.FormattingStyle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.google.gson.LongSerializationPolicy;
import com.google.gson.Strictness;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Type;
import net.kyori.adventure.text.Component;
import net.pl3x.guithium.api.gui.element.Element;
import net.pl3x.guithium.api.json.adapter.ComponentAdapter;
import net.pl3x.guithium.api.key.Key;
import org.apache.commons.lang3.StringUtils;
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
            .registerTypeAdapter(Key.class, new Key.Adapter())
            .registerTypeAdapter(Element.class, new Adapter<>())
            .registerTypeAdapter(JsonSerializable.class, new Adapter<>())
            .addSerializationExclusionStrategy(new Exclude.Strategy())
            .create();

    /**
     * This method serializes this object into its equivalent JSON string representation.
     *
     * @return JSON representation of this object
     */
    @NotNull
    default String toJson() {
        return GSON.toJson(this);
    }

    /**
     * This method deserializes the specified JSON string into an object of the specified class.
     *
     * @param json  The string from which the object is to be deserialized
     * @param clazz The class of T
     * @param <T>   The type of the desired object
     * @return an object of type T from the string
     * @throws JsonSyntaxException      if json is not a valid representation for an object of type clazz
     * @throws IllegalArgumentException if json is {@code null} or empty
     */
    @NotNull
    static <T extends JsonSerializable> T fromJson(@NotNull String json, @NotNull Class<T> clazz) throws JsonSyntaxException {
        if (StringUtils.isEmpty(json)) {
            throw new IllegalArgumentException("json cannot be null or empty");
        }
        T obj;
        try {
            obj = GSON.fromJson(json, clazz);
        } catch (Throwable e) {
            throw new JsonSyntaxException("Cannot deserialize " + clazz.getSimpleName() + ": " + json, e);
        }
        if (obj == null) {
            throw new JsonSyntaxException("Cannot deserialize null " + clazz.getSimpleName() + ": " + json);
        }
        return obj;
    }

    /**
     * A custom JSON (de)serializer for the GSON library specifically for JsonSerializable objects.
     *
     * @param <T> Type of JsonSerializable object
     */
    class Adapter<T extends JsonSerializable> implements JsonSerializer<T>, JsonDeserializer<T> {
        /**
         * Create a new JsonSerializable adapter for gson.
         */
        public Adapter() {
            // Empty constructor to pacify javadoc lint
        }

        @Override
        @NotNull
        public JsonElement serialize(@NotNull T src, @NotNull Type type, @NotNull JsonSerializationContext context) {
            JsonObject json = new JsonObject();
            json.add("class", new JsonPrimitive(src.getClass().getName()));
            json.add("data", context.serialize(src, src.getClass()));
            return json;
        }

        @Override
        @Nullable
        public T deserialize(@NotNull JsonElement json, @NotNull Type type, @NotNull JsonDeserializationContext context) throws JsonParseException {
            try {
                JsonObject obj = json.getAsJsonObject();
                Type clazz = obj.has("class") ? Class.forName(obj.get("class").getAsString()) : type;
                return context.deserialize(obj.get("data"), clazz);
            } catch (ClassNotFoundException e) {
                throw new JsonParseException("unknown class type", e);
            }
        }
    }

    /**
     * Annotation for marking fields to exclude from json serialization.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface Exclude {
        /**
         * Strategy to register with gson to handle exclude annotation.
         */
        class Strategy implements ExclusionStrategy {
            /**
             * Create a new exclude strategy for gson.
             */
            Strategy() {
                // Empty constructor to pacify javadoc lint
            }

            @Override
            public boolean shouldSkipClass(@NotNull Class<?> clazz) {
                return false;
            }

            @Override
            public boolean shouldSkipField(@NotNull FieldAttributes field) {
                return field.getAnnotation(Exclude.class) != null;
            }
        }
    }
}
