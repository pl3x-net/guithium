package net.pl3x.guithium.fabric.gui.element;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.pl3x.guithium.api.gui.Vec2;
import net.pl3x.guithium.api.gui.element.Button;
import net.pl3x.guithium.api.gui.element.Checkbox;
import net.pl3x.guithium.api.gui.element.Circle;
import net.pl3x.guithium.api.gui.element.Element;
import net.pl3x.guithium.api.gui.element.Gradient;
import net.pl3x.guithium.api.gui.element.Image;
import net.pl3x.guithium.api.gui.element.Line;
import net.pl3x.guithium.api.gui.element.Radio;
import net.pl3x.guithium.api.gui.element.Text;
import net.pl3x.guithium.api.gui.element.Textbox;
import net.pl3x.guithium.fabric.gui.screen.RenderableScreen;
import org.jetbrains.annotations.NotNull;

public abstract class RenderableElement {
    private final RenderableScreen screen;
    private Element element;

    protected Vec2 pos = Vec2.ZERO;

    public RenderableElement(@NotNull RenderableScreen screen, @NotNull Element element) {
        this.screen = screen;
        this.element = element;
    }

    @NotNull
    public RenderableScreen getScreen() {
        return this.screen;
    }

    @NotNull
    public Element getElement() {
        return this.element;
    }

    public void setElement(@NotNull Element element) {
        this.element = element;
        this.screen.refresh();
    }

    public void init(@NotNull Minecraft minecraft, int width, int height) {
    }

    public abstract void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float delta);

    protected void calcScreenPos(float width, float height) {
        Vec2 pos = getElement().getPos();
        if (pos == null) {
            pos = Vec2.ZERO;
        }

        double anchorX = 0;
        double anchorY = 0;
        if (getElement().getAnchor() != null) {
            anchorX = Math.ceil(this.screen.width * getElement().getAnchor().getX());
            anchorY = Math.ceil(this.screen.height * getElement().getAnchor().getY());
        }

        int offsetX = 0;
        int offsetY = 0;
        if (getElement().getOffset() != null) {
            offsetX = (int) (width * getElement().getOffset().getX());
            offsetY = (int) (height * getElement().getOffset().getY());
        }

        this.pos = Vec2.of(
            (int) (anchorX + pos.getX() - offsetX),
            (int) (anchorY + pos.getY() - offsetY)
        );
    }

    public static RenderableElement createRenderableElement(@NotNull Element element, @NotNull RenderableScreen screen) {
        Element.Type type = element.getType();
        if (type == Element.Type.BUTTON) return new RenderableButton(screen, (Button) element);
        if (type == Element.Type.CHECKBOX) return new RenderableCheckbox(screen, (Checkbox) element);
        if (type == Element.Type.CIRCLE) return new RenderableCircle(screen, (Circle) element);
        if (type == Element.Type.GRADIENT) return new RenderableGradient(screen, (Gradient) element);
        if (type == Element.Type.IMAGE) return new RenderableImage(screen, (Image) element);
        if (type == Element.Type.LINE) return new RenderableLine(screen, (Line) element);
        if (type == Element.Type.RADIO) return new RenderableRadio(screen, (Radio) element);
        if (type == Element.Type.TEXT) return new RenderableText(screen, (Text) element);
        if (type == Element.Type.TEXTBOX) return new RenderableTextbox(screen, (Textbox) element);
        return null;
    }
}
