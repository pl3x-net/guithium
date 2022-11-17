package net.pl3x.guithium.api.json;

import com.google.gson.GsonBuilder;
import net.pl3x.guithium.api.gui.Screen;
import net.pl3x.guithium.api.gui.element.Element;
import net.pl3x.guithium.api.json.adapter.ElementAdapter;
import net.pl3x.guithium.api.json.adapter.ScreenAdapter;

import java.lang.reflect.Type;

public class Gson {
    private static final com.google.gson.Gson gson = new GsonBuilder()
        .registerTypeHierarchyAdapter(Element.class, new ElementAdapter())
        .registerTypeHierarchyAdapter(Screen.class, new ScreenAdapter())
        .disableHtmlEscaping()
        .setLenient()
        .create();

    public static com.google.gson.Gson gson() {
        return gson;
    }

    public static String toJson(Object src) {
        return gson().toJson(src);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return gson().fromJson(json, classOfT);
    }

    public static <T> T fromJson(String json, Type typeOfT) {
        return gson().fromJson(json, typeOfT);
    }
}
