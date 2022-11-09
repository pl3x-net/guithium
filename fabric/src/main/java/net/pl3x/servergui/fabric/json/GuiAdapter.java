package net.pl3x.servergui.fabric.json;

import com.google.gson.JsonObject;
import net.pl3x.servergui.api.gui.Gui;
import net.pl3x.servergui.api.gui.element.Element;
import net.pl3x.servergui.api.gui.element.Point;
import net.pl3x.servergui.fabric.gui.RenderableGui;
import net.pl3x.servergui.fabric.gui.element.RenderableImage;
import net.pl3x.servergui.fabric.gui.element.RenderableText;

import java.util.Locale;

public class GuiAdapter extends net.pl3x.servergui.api.json.GuiAdapter {
    @Override
    protected Element createElement(JsonObject json) {
        return switch (json.get("type").getAsString()) {
            case "text" -> new RenderableText(
                json.get("id").getAsString(),
                json.get("text").getAsString(),
                new Point(json.get("pos").getAsJsonObject()),
                new Point(json.get("anchor").getAsJsonObject()),
                new Point(json.get("offset").getAsJsonObject()),
                json.get("shadow").getAsBoolean(),
                json.get("scale").getAsFloat(),
                json.get("zIndex").getAsDouble()
            );
            case "image" -> new RenderableImage(
                json.get("id").getAsString(),
                new Point(json.get("pos").getAsJsonObject()),
                new Point(json.get("size").getAsJsonObject()),
                new Point(json.get("anchor").getAsJsonObject()),
                new Point(json.get("offset").getAsJsonObject()),
                json.get("scale").getAsFloat(),
                json.get("zIndex").getAsDouble()
            );
            default -> null;
        };
    }

    @Override
    protected Gui createGui(JsonObject json) {
        return new RenderableGui(
            json.get("id").getAsString(),
            Gui.Type.valueOf(json.get("type").getAsString().toUpperCase(Locale.ROOT))
        );
    }
}
