package net.pl3x.guithium.api.json;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.FormattingStyle;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import com.google.gson.Strictness;
import java.lang.reflect.Type;
import net.kyori.adventure.text.Component;
import net.pl3x.guithium.api.gui.Screen;
import net.pl3x.guithium.api.gui.element.Element;
import net.pl3x.guithium.api.json.adapter.ComponentAdapter;
import net.pl3x.guithium.api.json.adapter.ElementAdapter;
import net.pl3x.guithium.api.json.adapter.ScreenAdapter;
import net.pl3x.guithium.api.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents some conveniences for using Gson.
 */
public abstract class Gson {
    private Gson() {
        // Empty constructor to pacify javadoc lint
    }

    /**
     * Gson instance for (de)serializing objects to/from JSON.
     */
    private static final com.google.gson.Gson GSON = new GsonBuilder()
            .disableHtmlEscaping()
            .setStrictness(Strictness.LENIENT)
            .setFormattingStyle(FormattingStyle.COMPACT)
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .setLongSerializationPolicy(LongSerializationPolicy.DEFAULT)
            .registerTypeAdapter(Component.class, new ComponentAdapter())
            .registerTypeAdapter(Key.class, new Key.Adapter())
            .registerTypeHierarchyAdapter(Element.class, new ElementAdapter())
            .registerTypeHierarchyAdapter(Screen.class, new ScreenAdapter())
            .create();

    /**
     * Get Guithium's stored Gson instance.
     *
     * @return Guithium's Gson instance
     */
    @NotNull
    public static com.google.gson.Gson gson() {
        return GSON;
    }

    /**
     * Serialize an object to a json string.
     *
     * @param src Object to serialize
     * @return Json string
     */
    @NotNull
    public static String toJson(@Nullable Object src) {
        return gson().toJson(src);
    }

    /**
     * Create object from json string.
     *
     * @param json     Json string
     * @param classOfT Type of object
     * @param <T>      Type of object
     * @return New object of specified type
     */
    @Nullable
    public static <T> T fromJson(@Nullable String json, @NotNull Class<T> classOfT) {
        return gson().fromJson(json, classOfT);
    }

    /**
     * Create object from json string.
     *
     * @param json    Json string
     * @param typeOfT Type of object
     * @param <T>     Type of object
     * @return New object of specified type
     */
    @Nullable
    public static <T> T fromJson(@Nullable String json, @NotNull Type typeOfT) {
        return gson().fromJson(json, typeOfT);
    }
}
