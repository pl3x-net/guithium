package net.pl3x.servergui.api.gui.element;

import com.google.gson.JsonObject;
import net.pl3x.servergui.api.json.JsonSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Element extends JsonSerializable {
    @NotNull
    String getId();

    @NotNull
    String getType();

    @Nullable
    String getParent();

    void setParent(@Nullable String parent);

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

    static Element createElement(JsonObject json) {
        return switch (!json.has("type") ? "" : json.get("type").getAsString()) {
            case "container" -> Container.fromJson(json);
            case "image" -> Image.fromJson(json);
            case "text" -> Text.fromJson(json);
            default -> null;
        };
    }
}
