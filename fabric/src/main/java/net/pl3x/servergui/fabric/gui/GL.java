package net.pl3x.servergui.fabric.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3f;

public class GL {
    public static void drawSolidRect(MatrixStack matrix, float x0, float y0, float x1, float y1, int color) {
        drawSolidRect(matrix.peek().getPositionMatrix(), x0, y0, x1, y1, color, color, color, color);
    }

    public static void drawSolidRect(MatrixStack matrix, float x0, float y0, float x1, float y1, int colorLeft, int colorRight) {
        drawSolidRect(matrix.peek().getPositionMatrix(), x0, y0, x1, y1, colorLeft, colorRight, colorLeft, colorRight);
    }

    public static void drawSolidRect(MatrixStack matrix, float x0, float y0, float x1, float y1, int colorTopLeft, int colorTopRight, int colorBottomLeft, int colorBottomRight) {
        drawSolidRect(matrix.peek().getPositionMatrix(), x0, y0, x1, y1, colorTopLeft, colorTopRight, colorBottomLeft, colorBottomRight);
    }

    public static void drawSolidRect(Matrix4f matrix4f, float x0, float y0, float x1, float y1, int color) {
        drawSolidRect(matrix4f, x0, y0, x1, y1, color, color, color, color);
    }

    public static void drawSolidRect(Matrix4f matrix4f, float x0, float y0, float x1, float y1, int colorLeft, int colorRight) {
        drawSolidRect(matrix4f, x0, y0, x1, y1, colorLeft, colorRight, colorLeft, colorRight);
    }

    public static void drawSolidRect(Matrix4f matrix4f, float x0, float y0, float x1, float y1, int colorTopLeft, int colorTopRight, int colorBottomLeft, int colorBottomRight) {
        RenderSystem.disableTexture();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        BufferBuilder buf = Tessellator.getInstance().getBuffer();
        buf.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        buf.vertex(matrix4f, x1, y0, 0).color(colorTopRight).next();
        buf.vertex(matrix4f, x0, y0, 0).color(colorTopLeft).next();
        buf.vertex(matrix4f, x0, y1, 0).color(colorBottomLeft).next();
        buf.vertex(matrix4f, x1, y1, 0).color(colorBottomRight).next();
        BufferRenderer.drawWithShader(buf.end());
        RenderSystem.enableTexture();
    }

    public static void drawSolidCirc(MatrixStack matrix, float centerX, float centerY, float radius, int color) {
        drawSolidCirc(matrix.peek().getPositionMatrix(), centerX, centerY, radius, (int) radius * 2, color);
    }

    public static void drawSolidCirc(Matrix4f matrix4f, float centerX, float centerY, float radius, int res, int color) {
        RenderSystem.disableTexture();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        BufferBuilder buf = Tessellator.getInstance().getBuffer();
        buf.begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_COLOR);
        for (int i = 0; i <= res; i++) {
            float angle = (float) (2 * Math.PI * i / res);
            float x = (float) (Math.sin(angle) * radius);
            float y = (float) (Math.cos(angle) * radius);
            buf.vertex(matrix4f, centerX + x, centerY + y, 0).color(color).next();
        }
        BufferRenderer.drawWithShader(buf.end());
        RenderSystem.enableTexture();
    }

    public static void drawLine(MatrixStack matrix, float x0, float y0, float x1, float y1, float width, int color) {
        RenderSystem.setShader(GameRenderer::getRenderTypeLinesShader);
        RenderSystem.lineWidth(width);
        Matrix4f matrix4f = matrix.peek().getPositionMatrix();
        Matrix3f matrix3f = matrix.peek().getNormalMatrix();
        BufferBuilder buf = Tessellator.getInstance().getBuffer();
        buf.begin(VertexFormat.DrawMode.LINES, VertexFormats.LINES);
        buf.vertex(matrix4f, x0, y0, 0).color(color).normal(matrix3f, 1, 1, 0).next();
        buf.vertex(matrix4f, x1, y1, 0).color(color).normal(matrix3f, 1, 1, 0).next();
        BufferRenderer.drawWithShader(buf.end());
        RenderSystem.lineWidth(1);
    }

    public static void drawTintedTexture(MatrixStack matrix, Identifier texture, float x, float y, float width, float height, int color) {
        drawTintedTexture(matrix, texture, x, y, x + width, y + height, 0, 0, 1, 1, color);
    }

    public static void drawTintedTexture(MatrixStack matrix, Identifier texture, float x0, float y0, float x1, float y1, float u0, float v0, float u1, float v1, int color) {
        RenderSystem.setShaderColor(
            (color >> 16 & 0xFF) / 255F,
            (color >> 8 & 0xFF) / 255F,
            (color & 0xFF) / 255F,
            (color >> 24 & 0xFF) / 255F
        );
        drawTexture(matrix, texture, x0, y0, x1, y1, u0, v0, u1, v1);
        RenderSystem.setShaderColor(1, 1, 1, 1);
    }

    public static void drawTexture(MatrixStack matrix, Identifier texture, float x, float y, float width, float height) {
        drawTexture(matrix, texture, x, y, x + width, y + height, 0, 0, 1, 1);
    }

    public static void drawTexture(MatrixStack matrix, Identifier texture, float x0, float y0, float x1, float y1, float u, float v) {
        drawTexture(matrix, texture, x0, y0, x1, y1, u, u, v, v);
    }

    public static void drawTexture(MatrixStack matrix, Identifier texture, float x0, float y0, float x1, float y1, float u0, float v0, float u1, float v1) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, texture);
        Matrix4f matrix4f = matrix.peek().getPositionMatrix();
        BufferBuilder buf = Tessellator.getInstance().getBuffer();
        buf.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        buf.vertex(matrix4f, x0, y0, 0).texture(u0, v0).next();
        buf.vertex(matrix4f, x0, y1, 0).texture(u0, v1).next();
        buf.vertex(matrix4f, x1, y1, 0).texture(u1, v1).next();
        buf.vertex(matrix4f, x1, y0, 0).texture(u1, v0).next();
        BufferRenderer.drawWithShader(buf.end());
    }

    public static void rotateScene(MatrixStack matrix, float x, float y, float degrees) {
        matrix.translate(x, y, 0);
        matrix.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(degrees));
        matrix.translate(-x, -y, 0);
    }

    public static void scaleScene(MatrixStack matrix, float x, float y, float scale) {
        matrix.translate(x, y, 0);
        matrix.scale(scale, scale, scale);
        matrix.translate(-x, -y, 0);
    }
}
