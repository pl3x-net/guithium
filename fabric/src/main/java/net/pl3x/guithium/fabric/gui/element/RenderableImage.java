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
import net.pl3x.guithium.api.gui.Point;
import net.pl3x.guithium.api.gui.element.Image;
import net.pl3x.guithium.fabric.Guithium;
import net.pl3x.guithium.fabric.gui.screen.RenderableScreen;
import net.pl3x.guithium.fabric.gui.texture.Texture;
import org.jetbrains.annotations.NotNull;

public class RenderableImage extends RenderableElement {
    private final Texture texture;
    private float x0, y0, x1, y1;
    private float u0, v0, u1, v1;
    private int tint;

    public RenderableImage(@NotNull Image image, @NotNull RenderableScreen screen) {
        super(image, screen);
        this.texture = Guithium.instance().getTextureManager().getOrAdd(image);
    }

    @Override
    @NotNull
    public Image getElement() {
        return (Image) super.getElement();
    }

    @NotNull
    public Texture getTexture() {
        return this.texture;
    }

    @Override
    public void init(@NotNull Minecraft minecraft, int width, int height) {
        Point size = getElement().getSize();
        if (size == null) {
            size = Point.of(width, height);
        }

        calcScreenPos(size.getX(), size.getY());

        this.x0 = this.pos.getX();
        this.y0 = this.pos.getY();
        this.x1 = this.x0 + size.getX();
        this.y1 = this.y0 + size.getY();

        this.u0 = 0;
        this.v0 = 0;

        Float tileMod = getElement().getTileModifier();
        if (tileMod == null) {
            this.u1 = 1;
            this.v1 = 1;
        } else {
            this.u1 = this.x1 / tileMod;
            this.v1 = this.y1 / tileMod;
        }

        this.tint = getElement().getTint() == null ? 0xFFFFFFFF : getElement().getTint();
    }

    @Override
    public void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float delta) {
        if (!getTexture().isLoaded()) {
            return;
        }

        poseStack.pushPose();

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.setShaderTexture(0, getTexture().getIdentifier());
        RenderSystem.setShaderColor(1, 1, 1, 1);

        Matrix4f model = poseStack.last().pose();
        BufferBuilder buf = Tesselator.getInstance().getBuilder();
        buf.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        buf.vertex(model, this.x1, this.y0, 0).uv(this.u1, this.v0).color(this.tint).endVertex();
        buf.vertex(model, this.x0, this.y0, 0).uv(this.u0, this.v0).color(this.tint).endVertex();
        buf.vertex(model, this.x0, this.y1, 0).uv(this.u0, this.v1).color(this.tint).endVertex();
        buf.vertex(model, this.x1, this.y1, 0).uv(this.u1, this.v1).color(this.tint).endVertex();
        BufferUploader.drawWithShader(buf.end());

        RenderSystem.disableBlend();

        poseStack.popPose();
    }
}
