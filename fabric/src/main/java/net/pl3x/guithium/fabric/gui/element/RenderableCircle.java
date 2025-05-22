package net.pl3x.guithium.fabric.gui.element;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.pl3x.guithium.api.gui.element.Circle;
import net.pl3x.guithium.fabric.gui.screen.AbstractScreen;
import net.pl3x.guithium.fabric.gui.screen.RenderableScreen;
import net.pl3x.guithium.fabric.util.Numbers;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

public class RenderableCircle extends AbstractWidget implements RenderableWidget {
    private final Minecraft client;
    private final AbstractScreen screen;
    private final Circle circle;

    private int radius;
    private int resolution;
    private int innerColor;
    private int outerColor;

    public RenderableCircle(@NotNull Minecraft client, @NotNull AbstractScreen screen, @NotNull Circle circle) {
        super(0, 0, 0, 0, Component.empty());
        this.client = client;
        this.screen = screen;
        this.circle = circle;
        this.active = false;
    }

    @Override
    @NotNull
    public Circle getElement() {
        return this.circle;
    }

    @Override
    public void init() {
        // update pos/size
        setX((int) getElement().getPos().getX());
        setY((int) getElement().getPos().getY());

        if (getElement().getRadius() != null) {
            this.radius = getElement().getRadius();
        } else {
            this.radius = Math.min(
                    RenderableScreen.windowWidth() / 2,
                    RenderableScreen.windowHeight() / 2
            );
        }

        setWidth(this.radius * 2);
        setHeight(this.radius * 2);

        // recalculate position on screen
        RenderableDuck self = (RenderableDuck) this;
        self.calcScreenPos(getWidth(), getHeight());

        this.innerColor = getElement().getInnerColor();
        this.outerColor = getElement().getOuterColor();

        this.resolution = Numbers.unbox(getElement().getResolution(), this.radius);
    }

    @Override
    protected void renderWidget(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float delta) {
        RenderableDuck self = (RenderableDuck) this;
        self.rotate(gfx, self.getCenterX(), self.getCenterY(), getElement().getRotation());
        self.scale(gfx, self.getCenterX(), self.getCenterY(), getElement().getScale());

        Matrix4f matrix4f = gfx.pose().last().pose();
        VertexConsumer buf = gfx.bufferSource.getBuffer(RenderType.debugTriangleFan());
        buf.addVertex(matrix4f, getX(), getY(), 0).setColor(this.innerColor);
        for (int i = 0; i <= this.resolution; i++) {
            float angle = (float) (2 * Math.PI * i / this.resolution);
            float x = getX() + (float) Math.sin(angle) * this.radius;
            float y = getY() + (float) Math.cos(angle) * this.radius;
            buf.addVertex(matrix4f, x, y, 0).setColor(this.outerColor);
        }
    }

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput narrationElementOutput) {
        // nothing to narrate
    }
}
