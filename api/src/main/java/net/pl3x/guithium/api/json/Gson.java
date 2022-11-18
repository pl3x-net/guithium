package net.pl3x.guithium.api.json;

import com.google.gson.GsonBuilder;
import net.pl3x.guithium.api.gui.Screen;
import net.pl3x.guithium.api.gui.element.Element;
import net.pl3x.guithium.api.json.adapter.ElementAdapter;
import net.pl3x.guithium.api.json.adapter.ScreenAdapter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;

public class Gson {
    private static final com.google.gson.Gson gson = new GsonBuilder()
        .registerTypeHierarchyAdapter(Element.class, new ElementAdapter())
        .registerTypeHierarchyAdapter(Screen.class, new ScreenAdapter())
        .disableHtmlEscaping()
        .setLenient()
        .setPrettyPrinting()
        .create();

    @NotNull
    public static com.google.gson.Gson gson() {
        return gson;
    }

    @NotNull
    public static String toJson(@Nullable Object src) {
        return gson().toJson(src);
    }

    @Nullable
    public static <T> T fromJson(@Nullable String json, @NotNull Class<T> classOfT) {
        return gson().fromJson(json, classOfT);
    }

    @Nullable
    public static <T> T fromJson(@Nullable String json, @NotNull Type typeOfT) {
        return gson().fromJson(json, typeOfT);
    }
}
