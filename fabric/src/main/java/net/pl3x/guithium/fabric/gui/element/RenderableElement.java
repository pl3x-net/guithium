package net.pl3x.guithium.fabric.gui.element;

import java.util.List;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.FormattedCharSequence;
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
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;

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

    public void init(@NotNull Minecraft client, int width, int height) {
    }

    public abstract void render(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float delta);

    @NotNull
    protected MutableComponent adventureToVanilla(@Nullable net.kyori.adventure.text.Component component) {
        return adventureToVanilla(component == null ? null : GsonComponentSerializer.gson().serialize(component));
    }

    @NotNull
    protected MutableComponent adventureToVanilla(@Nullable String json) {
        MutableComponent nmsComponent = null;
        if (json != null) {
            try {
                nmsComponent = Component.Serializer.fromJson(json);
            } catch (Throwable t) {
                nmsComponent = Component.translatable(json);
            }
        }
        return nmsComponent == null ? Component.empty() : nmsComponent;
    }

    @Nullable
    protected List<FormattedCharSequence> processTooltip(@Nullable net.kyori.adventure.text.Component tooltip) {
        return tooltip == null ? null : Minecraft.getInstance().font.split(adventureToVanilla(tooltip), 200);
    }

    protected void rotate(GuiGraphics gfx, int x, int y, int width, int height, Float degrees) {
        if (degrees == null) {
            return;
        }
        rotate(gfx, (int) (x + width / 2F), (int) (y + height / 2F), degrees);
    }

    protected void rotate(GuiGraphics gfx, int x, int y, Float degrees) {
        if (degrees == null) {
            return;
        }
        gfx.pose.translate(x, y, 0);
        gfx.pose.mulPose((new Quaternionf()).rotateZ(degrees * 0.017453292F));
        gfx.pose.translate(-x, -y, 0);
    }

    protected void scale(GuiGraphics gfx, int x, int y, int width, int height, Float scale) {
        if (scale == null) {
            return;
        }
        scale(gfx, (int) (x + width / 2F), (int) (y + height / 2F), scale);
    }

    protected void scale(GuiGraphics gfx, int x, int y, Float scale) {
        if (scale == null) {
            return;
        }
        gfx.pose.translate(x, y, 0);
        gfx.pose.scale(scale, scale, 0);
        gfx.pose.translate(-x, -y, 0);
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
        if (type == Element.Type.CIRCLE) return new RenderableCircle(screen, (Circle) element); // done
        if (type == Element.Type.GRADIENT) return new RenderableGradient(screen, (Gradient) element); // done
        if (type == Element.Type.IMAGE) return new RenderableImage(screen, (Image) element);
        if (type == Element.Type.LINE) return new RenderableLine(screen, (Line) element); // done
        if (type == Element.Type.RADIO) return new RenderableRadio(screen, (Radio) element);
        if (type == Element.Type.SLIDER) return new RenderableSlider(screen, (Slider) element);
        if (type == Element.Type.TEXT) return new RenderableText(screen, (Text) element); // done
        if (type == Element.Type.TEXTBOX) return new RenderableTextbox(screen, (Textbox) element);
        return null;
    }
}
