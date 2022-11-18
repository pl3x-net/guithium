package net.pl3x.guithium.fabric.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;

public class GL {
    public static void drawSolidRect(PoseStack poseStack, float x0, float y0, float x1, float y1, int color) {
        drawSolidRect(poseStack.last().pose(), x0, y0, x1, y1, color, color, color, color);
    }

    public static void drawSolidRect(PoseStack poseStack, float x0, float y0, float x1, float y1, int colorLeft, int colorRight) {
        drawSolidRect(poseStack.last().pose(), x0, y0, x1, y1, colorLeft, colorRight, colorLeft, colorRight);
    }

    public static void drawSolidRect(PoseStack poseStack, float x0, float y0, float x1, float y1, int colorTopLeft, int colorTopRight, int colorBottomLeft, int colorBottomRight) {
        drawSolidRect(poseStack.last().pose(), x0, y0, x1, y1, colorTopLeft, colorTopRight, colorBottomLeft, colorBottomRight);
    }

    public static void drawSolidRect(Matrix4f matrix4f, float x0, float y0, float x1, float y1, int color) {
        drawSolidRect(matrix4f, x0, y0, x1, y1, color, color, color, color);
    }

    public static void drawSolidRect(Matrix4f matrix4f, float x0, float y0, float x1, float y1, int colorLeft, int colorRight) {
        drawSolidRect(matrix4f, x0, y0, x1, y1, colorLeft, colorRight, colorLeft, colorRight);
    }

    public static void drawSolidRect(Matrix4f matrix4f, float x0, float y0, float x1, float y1, int colorTopLeft, int colorTopRight, int colorBottomLeft, int colorBottomRight) {
        RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        BufferBuilder buf = Tesselator.getInstance().getBuilder();
        buf.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        buf.vertex(matrix4f, x1, y0, 0).color(colorTopRight).endVertex();
        buf.vertex(matrix4f, x0, y0, 0).color(colorTopLeft).endVertex();
        buf.vertex(matrix4f, x0, y1, 0).color(colorBottomLeft).endVertex();
        buf.vertex(matrix4f, x1, y1, 0).color(colorBottomRight).endVertex();
        BufferUploader.drawWithShader(buf.end());
        RenderSystem.disableBlend();
        RenderSystem.enableTexture();
    }

    public static void drawSolidCirc(PoseStack poseStack, float centerX, float centerY, float radius, int color) {
        drawSolidCirc(poseStack.last().pose(), centerX, centerY, radius, (int) radius * 2, color);
    }

    public static void drawSolidCirc(Matrix4f matrix4f, float centerX, float centerY, float radius, int res, int color) {
        RenderSystem.disableTexture();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        BufferBuilder buf = Tesselator.getInstance().getBuilder();
        buf.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);
        for (int i = 0; i <= res; i++) {
            float angle = (float) (2 * Math.PI * i / res);
            float x = (float) (Math.sin(angle) * radius);
            float y = (float) (Math.cos(angle) * radius);
            buf.vertex(matrix4f, centerX + x, centerY + y, 0).color(color).endVertex();
        }
        BufferUploader.drawWithShader(buf.end());
        RenderSystem.enableTexture();
    }

    public static void drawLine(PoseStack poseStack, float x0, float y0, float x1, float y1, float width, int color) {
        RenderSystem.setShader(GameRenderer::getPositionTexColorNormalShader);
        RenderSystem.lineWidth(width);
        Matrix4f matrix4f = poseStack.last().pose();
        Matrix3f matrix3f = poseStack.last().normal();
        BufferBuilder buf = Tesselator.getInstance().getBuilder();
        buf.begin(VertexFormat.Mode.LINES, DefaultVertexFormat.POSITION_COLOR_NORMAL);
        buf.vertex(matrix4f, x0, y0, 0).color(color).normal(matrix3f, 1, 1, 0).endVertex();
        buf.vertex(matrix4f, x1, y1, 0).color(color).normal(matrix3f, 1, 1, 0).endVertex();
        BufferUploader.drawWithShader(buf.end());
        RenderSystem.lineWidth(1);
    }

    public static void drawTintedTexture(PoseStack poseStack, ResourceLocation texture, float x, float y, float width, float height, int color) {
        drawTintedTexture(poseStack, texture, x, y, x + width, y + height, 0, 0, 1, 1, color);
    }

    public static void drawTintedTexture(PoseStack poseStack, ResourceLocation texture, float x0, float y0, float x1, float y1, float u0, float v0, float u1, float v1, int color) {
        RenderSystem.setShaderColor(
            (color >> 16 & 0xFF) / 255F,
            (color >> 8 & 0xFF) / 255F,
            (color & 0xFF) / 255F,
            (color >> 24 & 0xFF) / 255F
        );
        drawTexture(poseStack, texture, x0, y0, x1, y1, u0, v0, u1, v1);
        RenderSystem.setShaderColor(1, 1, 1, 1);
    }

    public static void drawTexture(PoseStack poseStack, ResourceLocation texture, float x, float y, float width, float height) {
        drawTexture(poseStack, texture, x, y, x + width, y + height, 0, 0, 1, 1);
    }

    public static void drawTexture(PoseStack poseStack, ResourceLocation texture, float x0, float y0, float x1, float y1, float u, float v) {
        drawTexture(poseStack, texture, x0, y0, x1, y1, u, u, v, v);
    }

    public static void drawTexture(PoseStack poseStack, ResourceLocation texture, float x0, float y0, float x1, float y1, float u0, float v0, float u1, float v1) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, texture);
        Matrix4f matrix4f = poseStack.last().pose();
        BufferBuilder buf = Tesselator.getInstance().getBuilder();
        buf.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        buf.vertex(matrix4f, x0, y0, 0).uv(u0, v0).endVertex();
        buf.vertex(matrix4f, x0, y1, 0).uv(u0, v1).endVertex();
        buf.vertex(matrix4f, x1, y1, 0).uv(u1, v1).endVertex();
        buf.vertex(matrix4f, x1, y0, 0).uv(u1, v0).endVertex();
        BufferUploader.drawWithShader(buf.end());
    }

    public static void rotateScene(PoseStack poseStack, float x, float y, float degrees) {
        poseStack.translate(x, y, 0);
        poseStack.mulPose(Vector3f.ZP.rotationDegrees(degrees));
        poseStack.translate(-x, -y, 0);
    }

    public static void scaleScene(PoseStack poseStack, float x, float y, float scale) {
        poseStack.translate(x, y, 0);
        poseStack.scale(scale, scale, scale);
        poseStack.translate(-x, -y, 0);
    }
}
