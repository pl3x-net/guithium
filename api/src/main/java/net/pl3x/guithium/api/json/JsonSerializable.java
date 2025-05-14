package net.pl3x.guithium.api.json;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.FormattingStyle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.LongSerializationPolicy;
import com.google.gson.Strictness;
import net.kyori.adventure.text.Component;
import net.pl3x.guithium.api.json.adapter.ComponentAdapter;
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
    @Nullable
    static <T extends JsonSerializable> T fromJson(@NotNull String json, @NotNull Class<T> clazz) {
        if (StringUtils.isEmpty(json)) {
            throw new IllegalArgumentException("json cannot be null or empty");
        }
        return GSON.fromJson(json, clazz);
    }
}
