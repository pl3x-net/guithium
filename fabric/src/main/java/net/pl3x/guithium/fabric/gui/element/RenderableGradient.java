package net.pl3x.guithium.fabric.gui.element;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.pl3x.guithium.api.gui.element.Gradient;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

public class RenderableGradient extends AbstractWidget implements RenderableWidget {
    private final Gradient gradient;
    private int x0, y0, x1, y1;
    private int[] color;

    public RenderableGradient(@NotNull Gradient gradient) {
        super(
                gradient.getPos().x(),
                gradient.getPos().y(),
                gradient.getSize().x(),
                gradient.getSize().y(),
                Component.empty()
        );
        this.gradient = gradient;
    }

    @Override
    @NotNull
    public Gradient getElement() {
        return this.gradient;
    }

    @Override
    public void init(@NotNull Minecraft client) {
        // update pos/size
        setX(getElement().getPos().getX());
        setY(getElement().getPos().getY());
        setWidth(getElement().getSize().getX());
        setHeight(getElement().getSize().getY());

        // recalculate position on screen
        RenderableDuck self = (RenderableDuck) this;
        self.calcScreenPos(getWidth(), getHeight());

        this.x0 = getX();
        this.y0 = getY();
        this.x1 = getX() + getWidth();
        this.y1 = getY() + getHeight();

        this.color = getElement().getColors();
    }

    @Override
    protected void renderWidget(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float delta) {
        RenderableDuck self = (RenderableDuck) this;
        self.rotate(gfx, self.getCenterX(), self.getCenterY(), getElement().getRotation());
        self.scale(gfx, self.getCenterX(), self.getCenterY(), getElement().getScale());

        Matrix4f matrix4f = gfx.pose().last().pose();
        VertexConsumer vertexConsumer = gfx.bufferSource.getBuffer(RenderType.gui());
        vertexConsumer.addVertex(matrix4f, this.x0, this.y0, 0).setColor(this.color[0]);
        vertexConsumer.addVertex(matrix4f, this.x0, this.y1, 0).setColor(this.color[1]);
        vertexConsumer.addVertex(matrix4f, this.x1, this.y1, 0).setColor(this.color[2]);
        vertexConsumer.addVertex(matrix4f, this.x1, this.y0, 0).setColor(this.color[3]);
    }

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput narrationElementOutput) {
        // nothing to narrate
    }
}
