package net.pl3x.servergui.api.json;

import com.google.gson.GsonBuilder;
import net.pl3x.servergui.api.gui.Gui;
import net.pl3x.servergui.api.gui.element.Element;

import java.lang.reflect.Type;

public class Gson {
    private final com.google.gson.Gson gson;

    public Gson(GuiAdapter guiAdapter, ElementAdapter elementAdapter) {
        this.gson = new GsonBuilder()
            .registerTypeHierarchyAdapter(Gui.class, guiAdapter)
            .registerTypeHierarchyAdapter(Element.class, elementAdapter)
            .disableHtmlEscaping()
            .setLenient()
            .create();
    }

    public com.google.gson.Gson gson() {
        return this.gson;
    }

    public String toJson(Object src) {
        return gson().toJson(src);
    }

    public <T> T fromJson(String json, Class<T> classOfT) {
        return gson().fromJson(json, classOfT);
    }

    public <T> T fromJson(String json, Type typeOfT) {
        return gson().fromJson(json, typeOfT);
    }
}
