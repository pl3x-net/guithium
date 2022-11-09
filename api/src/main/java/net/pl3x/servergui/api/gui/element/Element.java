package net.pl3x.servergui.api.gui.element;

import net.pl3x.servergui.api.json.JsonSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Element extends JsonSerializable {
    @NotNull
    String getId();

    @NotNull
    String getType();

    @NotNull
    Point getPos();

    void setPos(@Nullable Point pos);

    @NotNull
    Point getAnchor();

    void setAnchor(@Nullable Point anchor);

    @NotNull
    Point getOffset();

    void setOffset(@Nullable Point offset);

    float getScale();

    void setScale(float scale);

    double getZIndex();

    void setZIndex(double zIndex);
}
