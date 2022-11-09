package net.pl3x.servergui.plugin.json;

import com.google.gson.JsonObject;
import net.pl3x.servergui.api.gui.element.Element;
import net.pl3x.servergui.api.gui.element.Image;
import net.pl3x.servergui.api.gui.element.Point;
import net.pl3x.servergui.api.gui.element.Text;

public class ElementAdapter extends net.pl3x.servergui.api.json.ElementAdapter {
    @Override
    public Element createElement(JsonObject json) {
        return switch (json.get("type").getAsString()) {
            case "text" -> new Text(
                json.get("id").getAsString(),
                json.get("text").getAsString(),
                new Point(json.get("pos").getAsJsonObject()),
                new Point(json.get("anchor").getAsJsonObject()),
                new Point(json.get("offset").getAsJsonObject()),
                json.get("shadow").getAsBoolean(),
                json.get("scale").getAsFloat(),
                json.get("zIndex").getAsDouble()
            );
            case "image" -> new Image(
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
}
