package net.pl3x.guithium.fabric.gui.element;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
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
import net.pl3x.guithium.api.gui.element.Slider;
import net.pl3x.guithium.api.gui.element.Text;
import net.pl3x.guithium.api.gui.element.Textbox;
import net.pl3x.guithium.fabric.gui.screen.RenderableScreen;
import org.jetbrains.annotations.NotNull;

public abstract class RenderableElement {
    private final RenderableScreen screen;
    private Element element;

    protected int anchorX = 0;
    protected int anchorY = 0;
    protected int posX = 0;
    protected int posY = 0;
    protected int offsetX = 0;
    protected int offsetY = 0;

    protected int centerX;
    protected int centerY;
    protected int scaleX;
    protected int scaleY;

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

    public void updateElement(@NotNull Element element) {
        this.element = element;
        this.screen.refresh();
    }

    public void init(@NotNull Minecraft minecraft, int width, int height) {
    }

    public abstract void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float delta);

    protected void rotate(PoseStack poseStack, int x, int y, int width, int height, Float degrees) {
        if (degrees == null) {
            return;
        }
        rotate(poseStack, (int) (x + width / 2F), (int) (y + height / 2F), degrees);
    }

    protected void rotate(PoseStack poseStack, int x, int y, Float degrees) {
        if (degrees == null) {
            return;
        }
        poseStack.translate(x, y, 0);
        poseStack.mulPose(Vector3f.ZP.rotationDegrees(degrees));
        poseStack.translate(-x, -y, 0);
    }

    protected void scale(PoseStack poseStack, int x, int y, int width, int height, Float scale) {
        if (scale == null) {
            return;
        }
        scale(poseStack, (int) (x + width / 2F), (int) (y + height / 2F), scale);
    }

    protected void scale(PoseStack poseStack, int x, int y, Float scale) {
        if (scale == null) {
            return;
        }
        poseStack.translate(x, y, 0);
        poseStack.scale(scale, scale, 0);
        poseStack.translate(-x, -y, 0);
    }

    protected void calcScreenPos(float width, float height) {
        Vec2 pos = getElement().getPos();
        if (pos == null) {
            pos = Vec2.ZERO;
        }

        if (getElement().getAnchor() != null) {
            this.anchorX = (int) Math.ceil(this.screen.width * getElement().getAnchor().getX());
            this.anchorY = (int) Math.ceil(this.screen.height * getElement().getAnchor().getY());
        }

        if (getElement().getOffset() != null) {
            this.offsetX = (int) (width * getElement().getOffset().getX());
            this.offsetY = (int) (height * getElement().getOffset().getY());
        }

        this.posX = (int) (this.anchorX + pos.getX() - this.offsetX);
        this.posY = (int) (this.anchorY + pos.getY() - this.offsetY);

        this.scaleX = this.posX + this.offsetX;
        this.scaleY = this.posY + this.offsetY;
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
        if (type == Element.Type.SLIDER) return new RenderableSlider(screen, (Slider) element);
        if (type == Element.Type.TEXT) return new RenderableText(screen, (Text) element);
        if (type == Element.Type.TEXTBOX) return new RenderableTextbox(screen, (Textbox) element);
        return null;
    }
}
