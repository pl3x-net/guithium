package net.pl3x.guithium.fabric.gui.element;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.pl3x.guithium.api.gui.Vec2;
import net.pl3x.guithium.api.gui.element.Gradient;
import net.pl3x.guithium.fabric.gui.screen.RenderableScreen;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

public class RenderableGradient extends RenderableElement {
    private float x0, y0, x1, y1;

    public RenderableGradient(@NotNull RenderableScreen screen, @NotNull Gradient gradient) {
        super(screen, gradient);
    }

    @Override
    @NotNull
    public Gradient getElement() {
        return (Gradient) super.getElement();
    }

    @Override
    public void init(@NotNull Minecraft client, int width, int height) {
        Vec2 size = getElement().getSize();
        if (size == null) {
            size = Vec2.ONE;
        } else {
            width = (int) size.getX();
            height = (int) size.getY();
        }

        calcScreenPos(size.getX(), size.getY());

        this.x0 = this.posX;
        this.y0 = this.posY;
        this.x1 = this.x0 + width;
        this.y1 = this.y0 + height;

        this.centerX = (int) (this.x0 + width / 2);
        this.centerY = (int) (this.y0 + height / 2);
    }

    @Override
    public void render(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float delta) {
        Gradient gradient = getElement();

        rotate(gfx, this.centerX, this.centerY, gradient.getRotation());
        scale(gfx, this.scaleX, this.scaleY, gradient.getScale());

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);

        Matrix4f model = gfx.pose.last().pose();
        BufferBuilder buf = Tesselator.getInstance().getBuilder();
        buf.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        buf.vertex(model, this.x1, this.y0, 0).color(gradient.getColorTopRight()).endVertex();
        buf.vertex(model, this.x0, this.y0, 0).color(gradient.getColorTopLeft()).endVertex();
        buf.vertex(model, this.x0, this.y1, 0).color(gradient.getColorBottomLeft()).endVertex();
        buf.vertex(model, this.x1, this.y1, 0).color(gradient.getColorBottomRight()).endVertex();
        BufferUploader.drawWithShader(buf.end());
    }
}
