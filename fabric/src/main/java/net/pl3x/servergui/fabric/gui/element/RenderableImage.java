package net.pl3x.servergui.fabric.gui.element;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import net.minecraft.client.renderer.GameRenderer;
import net.pl3x.servergui.api.gui.element.Image;
import net.pl3x.servergui.fabric.ServerGUI;
import net.pl3x.servergui.fabric.gui.screen.RenderableScreen;
import net.pl3x.servergui.fabric.gui.texture.Texture;
import org.jetbrains.annotations.NotNull;

public class RenderableImage extends RenderableElement {
    private Texture texture;

    public RenderableImage(Image image, RenderableScreen screen) {
        super(image, screen);
    }

    @Override
    public Image getElement() {
        return (Image) super.getElement();
    }

    public Texture getTexture() {
        return this.texture;
    }

    @Override
    public void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float delta) {
        Image image = getElement();
        if (image.getSize() == null) {
            return;
        }

        if (getTexture() == null) {
            this.texture = ServerGUI.instance().getTextureManager().get(image.getKey());
            return;
        }

        if (!getTexture().isLoaded()) {
            return;
        }

        poseStack.pushPose();

        calcScreenPos(image.getSize().getX(), image.getSize().getY(), setupScaleAndZIndex(poseStack));

        float x0 = getScreenPos().getX();
        float y0 = getScreenPos().getY();
        float x1 = x0 + image.getSize().getX();
        float y1 = y0 + image.getSize().getY();

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, getTexture().getIdentifier());
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();

        Matrix4f model = poseStack.last().pose();
        BufferBuilder buf = Tesselator.getInstance().getBuilder();
        buf.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        buf.vertex(model, x0, y0, 0F).uv(0, 0).endVertex();
        buf.vertex(model, x0, y1, 0F).uv(0, 1).endVertex();
        buf.vertex(model, x1, y1, 0F).uv(1, 1).endVertex();
        buf.vertex(model, x1, y0, 0F).uv(1, 0).endVertex();
        BufferUploader.drawWithShader(buf.end());

        poseStack.popPose();
    }
}
