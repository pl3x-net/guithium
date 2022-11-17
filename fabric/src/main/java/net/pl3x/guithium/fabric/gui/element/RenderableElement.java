package net.pl3x.guithium.fabric.gui.element;

import com.mojang.blaze3d.vertex.PoseStack;
import net.pl3x.guithium.api.gui.Point;
import net.pl3x.guithium.api.gui.element.Button;
import net.pl3x.guithium.api.gui.element.Element;
import net.pl3x.guithium.api.gui.element.Image;
import net.pl3x.guithium.api.gui.element.Text;
import net.pl3x.guithium.fabric.Guithium;
import net.pl3x.guithium.fabric.gui.screen.RenderableScreen;
import org.jetbrains.annotations.NotNull;

public abstract class RenderableElement {
    private Element element;
    private final RenderableScreen screen;
    private final Point screenPos = new Point();

    private boolean hovered;

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

    public RenderableScreen getScreen() {
        return this.screen;
    }

    public Point getScreenPos() {
        return this.screenPos;
    }

    protected float setupScaleAndZIndex(@NotNull PoseStack poseStack) {
        Float scale = getElement().getScale();
        if (scale != null && scale != 1.0F) {
            poseStack.scale(scale, scale, 1.0F);
        }
        Double zIndex = getElement().getZIndex();
        if (zIndex != null && zIndex != 0.0D) {
            poseStack.translate(0.0D, 0.0D, zIndex);
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
            anchorX = Math.ceil(Guithium.screenWidth * getElement().getAnchor().getX() / scale);
            anchorY = Math.ceil(Guithium.screenHeight * getElement().getAnchor().getY() / scale);
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
            case "button" -> new RenderableButton((Button) element, screen);
            case "image" -> new RenderableImage((Image) element, screen);
            case "text" -> new RenderableText((Text) element, screen);
            default -> null;
        };
    }

    public abstract void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float delta);

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return false;
    }

    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return false;
    }

    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        return false;
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        return false;
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return false;
    }
}
