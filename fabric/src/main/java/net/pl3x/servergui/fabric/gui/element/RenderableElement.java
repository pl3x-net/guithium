package net.pl3x.servergui.fabric.gui.element;

import net.minecraft.client.util.math.MatrixStack;
import net.pl3x.servergui.api.gui.element.Element;
import net.pl3x.servergui.api.gui.element.Image;
import net.pl3x.servergui.api.gui.Point;
import net.pl3x.servergui.api.gui.element.Text;
import net.pl3x.servergui.fabric.ServerGUIFabric;
import net.pl3x.servergui.fabric.gui.screen.RenderableScreen;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class RenderableElement {
    private Element element;
    private RenderableScreen screen;
    private final List<RenderableElement> children = new ArrayList<>();

    private final Point screenPos = new Point();

    public RenderableElement(Element element, RenderableScreen screen) {
        this.element = element;
        this.screen = screen;
    }

    public Element getElement() {
        return this.element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public List<RenderableElement> getChildren() {
        return this.children;
    }

    public Point getScreenPos() {
        return this.screenPos;
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

    protected void calcScreenPos(float width, float height, float scale) {
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

        getScreenPos().setX((int) (anchorX + pos.getX() - offsetX));
        getScreenPos().setY((int) (anchorY + pos.getY() - offsetY));
    }

    public static RenderableElement createRenderableElement(Element element, RenderableScreen screen) {
        return switch (element.getType()) {
            case "image" -> new RenderableImage((Image) element, screen);
            case "text" -> new RenderableText((Text) element, screen);
            default -> null;
        };
    }

    public abstract void render(@NotNull MatrixStack matrix, float delta);
}
