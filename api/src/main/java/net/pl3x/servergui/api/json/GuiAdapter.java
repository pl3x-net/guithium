package net.pl3x.servergui.api.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import net.pl3x.servergui.api.gui.Gui;
import net.pl3x.servergui.api.gui.element.Element;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;

public abstract class GuiAdapter implements JsonSerializer<Gui>, JsonDeserializer<Gui> {
    @Override
    @NotNull
    public JsonElement serialize(@NotNull Gui gui, @NotNull Type type, @NotNull JsonSerializationContext context) {
        return gui.toJson();
    }

    @Override
    public Gui deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        Gui gui = createGui(obj);
        obj.get("elements").getAsJsonArray().forEach(val -> gui.addElement(createElement(val.getAsJsonObject())));
        return gui;
    }

    protected abstract Element createElement(JsonObject json);

    protected abstract Gui createGui(JsonObject json);
}
