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
import net.pl3x.guithium.api.gui.element.Circle;
import net.pl3x.guithium.fabric.gui.screen.RenderableScreen;
import org.jetbrains.annotations.NotNull;

public class RenderableCircle extends RenderableElement {
    private float x, y, radius;
    private int resolution;
    private int innerColor;
    private int outerColor;

    public RenderableCircle(@NotNull RenderableScreen screen, @NotNull Circle circle) {
        super(screen, circle);
    }

    @Override
    @NotNull
    public Circle getElement() {
        return (Circle) super.getElement();
    }

    @Override
    public void init(@NotNull Minecraft minecraft, int width, int height) {
        float radius;
        if (getElement().getRadius() == null) {
            radius = Math.min(getScreen().width, getScreen().height) / 2F;
        } else {
            radius = getElement().getRadius();
        }

        float size = radius * 2;
        calcScreenPos(size, size);

        this.x = this.posX + radius;
        this.y = this.posY + radius;
        this.radius = radius;

        if (getElement().getResolution() == null) {
            this.resolution = (int) radius;
        } else {
            this.resolution = getElement().getResolution();
        }

        this.innerColor = getElement().getInnerColor();
        this.outerColor = getElement().getOuterColor();

        this.centerX = (int) this.x;
        this.centerY = (int) this.y;
    }

    @Override
    public void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float delta) {
        poseStack.pushPose();

        rotate(poseStack, this.centerX, this.centerY, getElement().getRotation());
        scale(poseStack, this.scaleX, this.scaleY, getElement().getScale());

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableTexture();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);

        Matrix4f model = poseStack.last().pose();
        BufferBuilder buf = Tesselator.getInstance().getBuilder();
        buf.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);
        buf.vertex(model, this.x, this.y, 0).color(this.innerColor).endVertex();
        for (int i = 0; i <= this.resolution; i++) {
            float angle = 2F * (float) Math.PI * i / this.resolution;
            float x = (float) Math.sin(angle) * this.radius;
            float y = (float) Math.cos(angle) * this.radius;
            buf.vertex(model, this.x + x, this.y + y, 0).color(this.outerColor).endVertex();
        }
        BufferUploader.drawWithShader(buf.end());

        RenderSystem.enableTexture();
        RenderSystem.disableBlend();

        poseStack.popPose();
    }
}
