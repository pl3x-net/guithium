package net.pl3x.guithium.fabric.gui.element;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.pl3x.guithium.api.gui.element.Gradient;
import net.pl3x.guithium.fabric.gui.screen.AbstractScreen;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

public class RenderableGradient extends AbstractWidget implements RenderableWidget {
    private final RenderableDuck self;
    private final Gradient gradient;

    private int x0, y0, x1, y1;
    private int[] color;

    public RenderableGradient(@NotNull Minecraft client, @NotNull AbstractScreen screen, @NotNull Gradient gradient) {
        super(0, 0, 0, 0, Component.empty());
        this.self = ((RenderableDuck) this).duck(client, screen);
        this.gradient = gradient;
        this.active = false;
    }

    @Override
    @NotNull
    public Gradient getElement() {
        return this.gradient;
    }

    @Override
    public void init() {
        // update pos/size
        setX((int) getElement().getPos().getX());
        setY((int) getElement().getPos().getY());
        setWidth((int) getElement().getSize().getX());
        setHeight((int) getElement().getSize().getY());

        // recalculate position on screen
        this.self.calcScreenPos(getWidth(), getHeight());

        this.x0 = getX();
        this.y0 = getY();
        this.x1 = getX() + getWidth();
        this.y1 = getY() + getHeight();

        this.color = getElement().getColors();
    }

    @Override
    protected void renderWidget(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float delta) {
        this.self.rotate(gfx, this.self.getCenterX(), this.self.getCenterY(), getElement().getRotation());
        this.self.scale(gfx, this.self.getCenterX(), this.self.getCenterY(), getElement().getScale());

        Matrix4f matrix4f = gfx.pose().last().pose();
        VertexConsumer buf = gfx.bufferSource.getBuffer(RenderType.gui());
        buf.addVertex(matrix4f, this.x0, this.y0, 0).setColor(this.color[0]);
        buf.addVertex(matrix4f, this.x0, this.y1, 0).setColor(this.color[1]);
        buf.addVertex(matrix4f, this.x1, this.y1, 0).setColor(this.color[2]);
        buf.addVertex(matrix4f, this.x1, this.y0, 0).setColor(this.color[3]);
    }

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput narrationElementOutput) {
        // nothing to narrate
    }
}
