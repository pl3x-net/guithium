package net.pl3x.guithium.fabric.gui.element;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.pl3x.guithium.api.gui.Vec2;
import net.pl3x.guithium.api.gui.element.Gradient;
import net.pl3x.guithium.fabric.gui.screen.RenderableScreen;
import org.jetbrains.annotations.NotNull;

public class RenderableGradient extends RenderableElement {
    private float x0;
    private float y0;
    private float x1;
    private float y1;

    public RenderableGradient(@NotNull RenderableScreen screen, @NotNull Gradient gradient) {
        super(screen, gradient);
    }

    @Override
    @NotNull
    public Gradient getElement() {
        return (Gradient) super.getElement();
    }

    @Override
    public void init(@NotNull Minecraft minecraft, int width, int height) {
        Vec2 size = getElement().getSize();
        if (size == null) {
            size = Vec2.ONE;
        } else {
            width = (int) size.getX();
            height = (int) size.getY();
        }

        calcScreenPos(size.getX(), size.getY());

        this.x0 = this.pos.getX();
        this.y0 = this.pos.getY();
        this.x1 = this.x0 + width;
        this.y1 = this.y0 + height;

        this.cX = (int) (this.x0 + width / 2);
        this.cY = (int) (this.y0 + height / 2);
    }

    @Override
    public void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float delta) {
        Gradient gradient = getElement();

        poseStack.pushPose();

        rotate(poseStack, this.cX, this.cY, getElement().getRotation());
        scale(poseStack, this.cX, this.cY, getElement().getScale());

        RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);

        Matrix4f model = poseStack.last().pose();
        BufferBuilder buf = Tesselator.getInstance().getBuilder();
        buf.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        buf.vertex(model, this.x1, this.y0, 0).color(gradient.getColorTopRight()).endVertex();
        buf.vertex(model, this.x0, this.y0, 0).color(gradient.getColorTopLeft()).endVertex();
        buf.vertex(model, this.x0, this.y1, 0).color(gradient.getColorBottomLeft()).endVertex();
        buf.vertex(model, this.x1, this.y1, 0).color(gradient.getColorBottomRight()).endVertex();
        BufferUploader.drawWithShader(buf.end());

        RenderSystem.disableBlend();
        RenderSystem.enableTexture();

        poseStack.popPose();
    }
}
