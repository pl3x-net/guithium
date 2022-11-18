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
    private float x0;
    private float y0;
    private float x1;
    private float y1;
    private Texture texture;

    public RenderableImage(Image image, RenderableScreen screen) {
        super(image, screen);
    }

    @Override
    @NotNull
    public Image getElement() {
        return (Image) super.getElement();
    }

    public Texture getTexture() {
        return this.texture;
    }

    @Override
    public void init(Minecraft minecraft, int width, int height) {
        Point size = getElement().getSize();
        if (size == null) {
            return;
        }

        calcScreenPos(size.getX(), size.getY());

        this.x0 = this.pos.getX();
        this.y0 = this.pos.getY();
        this.x1 = this.x0 + size.getX();
        this.y1 = this.y0 + size.getY();
    }

    @Override
    public void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float delta) {
        Image image = getElement();
        if (image.getSize() == null) {
            return;
        }

        if (getTexture() == null) {
            this.texture = Guithium.instance().getTextureManager().get(image.getKey());
            return;
        }

        if (!getTexture().isLoaded()) {
            return;
        }

        poseStack.pushPose();

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, getTexture().getIdentifier());
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();

        Matrix4f model = poseStack.last().pose();
        BufferBuilder buf = Tesselator.getInstance().getBuilder();
        buf.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        buf.vertex(model, this.x0, this.y0, 0F).uv(0, 0).endVertex();
        buf.vertex(model, this.x0, this.y1, 0F).uv(0, 1).endVertex();
        buf.vertex(model, this.x1, this.y1, 0F).uv(1, 1).endVertex();
        buf.vertex(model, this.x1, this.y0, 0F).uv(1, 0).endVertex();
        BufferUploader.drawWithShader(buf.end());

        poseStack.popPose();
    }
}
