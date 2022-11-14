package net.pl3x.servergui.api.gui.element;

import com.google.gson.JsonObject;
import net.pl3x.servergui.api.Key;
import net.pl3x.servergui.api.ServerGUI;
import net.pl3x.servergui.api.gui.Point;
import net.pl3x.servergui.api.json.JsonSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface Element extends JsonSerializable {
    @NotNull
    Key getKey();

    @NotNull
    String getType();

    @Nullable
    Point getPos();

    void setPos(float x, float y);

    void setPos(@Nullable Point pos);

    @Nullable
    Point getAnchor();

    void setAnchor(float x, float y);

    void setAnchor(@Nullable Point anchor);

    @Nullable
    Point getOffset();

    void setOffset(float x, float y);

    void setOffset(@Nullable Point offset);

    @Nullable
    Float getScale();

    void setScale(@Nullable Float scale);

    @Nullable
    Double getZIndex();

    void setZIndex(@Nullable Double zIndex);

    default void send(UUID uuid) {
        ServerGUI.api().getNetworkManager().send(uuid, this);
    }

    static Element createElement(JsonObject json) {
        return switch (!json.has("type") ? "" : json.get("type").getAsString()) {
            case "image" -> Image.fromJson(json);
            case "text" -> Text.fromJson(json);
            default -> null;
        };
    }
}
