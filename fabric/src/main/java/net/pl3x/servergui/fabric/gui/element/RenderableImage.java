package net.pl3x.servergui.fabric.gui.element;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;
import net.pl3x.servergui.api.gui.element.Image;
import net.pl3x.servergui.api.gui.element.Point;
import net.pl3x.servergui.fabric.ServerGUIFabric;
import net.pl3x.servergui.fabric.gui.texture.Texture;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RenderableImage extends Image implements RenderableElement {
    private Texture texture;

    public RenderableImage(@NotNull String id, @Nullable Point pos, @Nullable Point size, @Nullable Point anchor, @Nullable Point offset, float scale, double zIndex) {
        super(id, pos, size, anchor, offset, scale, zIndex);
    }

    public Texture getTexture() {
        return this.texture;
    }

    @Override
    public void render(@NotNull MatrixStack matrix) {
        if (getTexture() == null) {
            this.texture = ServerGUIFabric.instance().getTextureManager().get(getId());
            return;
        }

        if (!getTexture().isLoaded()) {
            return;
        }

        matrix.push();

        setupScaleAndZIndex(matrix);

        double anchorX = Math.ceil(ServerGUIFabric.screenWidth * getAnchor().getX() / getScale());
        double anchorY = Math.ceil(ServerGUIFabric.screenHeight * getAnchor().getY() / getScale());

        int offsetX = (int) (getSize().getX() * getOffset().getX());
        int offsetY = (int) (getSize().getY() * getOffset().getY());

        float x0 = (float) (anchorX + getPos().getX() + offsetX);
        float y0 = (float) (anchorY + getPos().getY() + offsetY);
        float x1 = x0 + getSize().getX();
        float y1 = y0 + getSize().getY();

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, getTexture().getIdentifier());
        Matrix4f model = matrix.peek().getPositionMatrix();
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        bufferBuilder.vertex(model, x0, y0, 0F).texture(0, 0).next();
        bufferBuilder.vertex(model, x0, y1, 0F).texture(0, 1).next();
        bufferBuilder.vertex(model, x1, y1, 0F).texture(1, 1).next();
        bufferBuilder.vertex(model, x1, y0, 0F).texture(1, 0).next();
        BufferRenderer.drawWithShader(bufferBuilder.end());

        matrix.pop();
    }
}
