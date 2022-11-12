package net.pl3x.servergui.fabric.gui.element;

import net.minecraft.client.util.math.MatrixStack;
import net.pl3x.servergui.api.gui.element.Container;
import net.pl3x.servergui.api.gui.element.Element;
import net.pl3x.servergui.api.gui.element.Image;
import net.pl3x.servergui.api.gui.element.Point;
import net.pl3x.servergui.api.gui.element.Text;
import net.pl3x.servergui.fabric.ServerGUIFabric;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class RenderableElement {
    private Element element;
    private RenderableElement parent;
    private final List<RenderableElement> children = new ArrayList<>();

    public RenderableElement(Element element, RenderableElement parent) {
        this.element = element;
        this.parent = parent;
    }

    public Element getElement() {
        return this.element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public RenderableElement getParent() {
        return this.parent;
    }

    public void setParent(RenderableElement parent) {
        this.parent = parent;
    }

    public List<RenderableElement> getChildren() {
        return this.children;
    }

    protected float setupScaleAndZIndex(@NotNull MatrixStack matrix) {
        Float scale = getElement().getScale();
        if (scale != null && scale != 1.0F) {
            matrix.scale(scale, scale, 1.0F);
        }
        Double zIndex = getElement().getZIndex();
        if (zIndex != null && zIndex != 0.0D) {
            matrix.translate(0.0D, 0.0D, zIndex);
        }
        return scale == null ? 1.0F : scale;
    }

    protected Point getRenderPos(float width, float height, float scale) {
        Point pos = getElement().getPos();
        if (pos == null) {
            pos = Point.ZERO;
        }

        double anchorX = 0;
        double anchorY = 0;
        if (getElement().getAnchor() != null) {
            anchorX = Math.ceil(ServerGUIFabric.screenWidth * getElement().getAnchor().getX() / scale);
            anchorY = Math.ceil(ServerGUIFabric.screenHeight * getElement().getAnchor().getY() / scale);
        }

        int offsetX = 0;
        int offsetY = 0;
        if (getElement().getOffset() != null) {
            offsetX = (int) (width * getElement().getOffset().getX());
            offsetY = (int) (height * getElement().getOffset().getY());
        }

        return Point.of(
            (int) (anchorX + pos.getX() - offsetX),
            (int) (anchorY + pos.getY() - offsetY)
        );
    }

    public static RenderableElement createRenderableElement(Element element) {
        RenderableElementManager manager = ServerGUIFabric.instance().getRenderableElementManager();
        RenderableElement parent = element.getParent() == null ? null : manager.getElement(element.getParent());
        return switch (element.getType()) {
            case "container" -> new RenderableContainer((Container) element, parent);
            case "image" -> new RenderableImage((Image) element, parent);
            case "text" -> new RenderableText((Text) element, parent);
            default -> null;
        };
    }

    public abstract void render(@NotNull MatrixStack matrix, float delta);
}
