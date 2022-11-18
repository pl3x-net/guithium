package net.pl3x.guithium.api.gui.element;

import com.google.gson.JsonObject;
import net.pl3x.guithium.api.Key;
import net.pl3x.guithium.api.gui.Point;
import net.pl3x.guithium.api.json.JsonSerializable;
import net.pl3x.guithium.api.net.packet.ElementPacket;
import net.pl3x.guithium.api.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

    default void send(@NotNull Player player) {
        player.getConnection().send(new ElementPacket(this));
    }

    @Nullable
    static Element fromJson(@NotNull JsonObject json) {
        return switch (!json.has("type") ? "" : json.get("type").getAsString()) {
            case "button" -> Button.fromJson(json);
            case "gradient" -> Gradient.fromJson(json);
            case "image" -> Image.fromJson(json);
            case "text" -> Text.fromJson(json);
            default -> null;
        };
    }
}
