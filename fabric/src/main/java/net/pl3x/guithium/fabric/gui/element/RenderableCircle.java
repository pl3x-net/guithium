package net.pl3x.guithium.fabric.gui.element;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.pl3x.guithium.api.gui.element.Circle;
import net.pl3x.guithium.fabric.gui.screen.RenderableScreen;
import net.pl3x.guithium.fabric.util.Numbers;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

public class RenderableCircle extends AbstractWidget implements RenderableWidget {
    private final Circle circle;
    private int resolution;
    private int innerColor;
    private int outerColor;

    public RenderableCircle(@NotNull Circle circle) {
        super(
                circle.getPos().x(),
                circle.getPos().y(),
                Numbers.unbox(circle.getRadius(), RenderableScreen.windowWidth() / 2),
                Numbers.unbox(circle.getRadius(), RenderableScreen.windowHeight() / 2),
                Component.empty()
        );
        this.circle = circle;
    }

    @Override
    @NotNull
    public Circle getElement() {
        return this.circle;
    }

    @Override
    public void init(@NotNull Minecraft client) {
        // update pos/size
        setX(getElement().getPos().getX());
        setY(getElement().getPos().getY());

        int size = Numbers.unbox(
                getElement().getRadius(),
                RenderableScreen.windowWidth() / 2
        );
        setWidth(size);
        setHeight(size);

        // recalculate position on screen
        RenderableDuck self = (RenderableDuck) this;
        self.calcScreenPos(getWidth(), getHeight());

        this.innerColor = getElement().getInnerColor();
        this.outerColor = getElement().getOuterColor();

        if (getElement().getResolution() == null) {
            this.resolution = getWidth();
        } else {
            this.resolution = getElement().getResolution();
        }
    }

    @Override
    protected void renderWidget(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float delta) {
        RenderableDuck self = (RenderableDuck) this;
        self.rotate(gfx, self.getCenterX(), self.getCenterY(), getElement().getRotation());
        self.scale(gfx, self.getCenterX(), self.getCenterY(), getElement().getScale());

        Matrix4f matrix4f = gfx.pose().last().pose();
        VertexConsumer vertexConsumer = gfx.bufferSource.getBuffer(RenderType.debugTriangleFan());
        vertexConsumer.addVertex(matrix4f, getX(), getY(), 0).setColor(this.innerColor);
        for (int i = 0; i <= this.resolution; i++) {
            float angle = 2F * (float) Math.PI * i / this.resolution;
            float x = (float) Math.sin(angle) * getWidth();
            float y = (float) Math.cos(angle) * getHeight();
            vertexConsumer.addVertex(matrix4f, getX() + x, getY() + y, 0).setColor(this.outerColor);
        }
    }

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput narrationElementOutput) {
        // nothing to narrate
    }
}
