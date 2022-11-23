package net.pl3x.guithium.fabric.gui.element;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.pl3x.guithium.api.gui.Point;
import net.pl3x.guithium.api.gui.element.Line;
import net.pl3x.guithium.fabric.gui.screen.RenderableScreen;
import org.jetbrains.annotations.NotNull;

public class RenderableLine extends RenderableElement {
    private float x0, y0, x1, y1;
    private float width;
    private int startColor;
    private int endColor;

    private Point endPos = Point.ZERO;

    public RenderableLine(@NotNull RenderableScreen screen, @NotNull Line line) {
        super(screen, line);
    }

    @Override
    @NotNull
    public Line getElement() {
        return (Line) super.getElement();
    }

    @Override
    public void init(@NotNull Minecraft minecraft, int width, int height) {
        if (getElement().getWidth() == null) {
            this.width = 1.0F;
        } else {
            this.width = getElement().getWidth();
        }

        calcScreenPos(0, 0);
        calcScreenEndPos();

        this.x0 = this.pos.getX();
        this.y0 = this.pos.getY();
        this.x1 = this.endPos.getX();
        this.y1 = this.endPos.getY();

        this.startColor = getElement().getStartColor();
        this.endColor = getElement().getEndColor();
    }

    @Override
    public void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float delta) {
        poseStack.pushPose();

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableTexture();
        RenderSystem.setShader(GameRenderer::getRendertypeLinesShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.lineWidth((float) (this.width * Minecraft.getInstance().getWindow().getGuiScale()));

        Matrix4f model = poseStack.last().pose();
        Matrix3f normal = poseStack.last().normal();

        BufferBuilder buf = Tesselator.getInstance().getBuilder();
        buf.begin(VertexFormat.Mode.LINES, DefaultVertexFormat.POSITION_COLOR_NORMAL);
        buf.vertex(model, this.x0, this.y0, 0).color(this.startColor).normal(normal, 1, 0, 0).endVertex();
        buf.vertex(model, this.x1, this.y1, 0).color(this.endColor).normal(normal, 1, 0, 0).endVertex();
        BufferUploader.drawWithShader(buf.end());

        RenderSystem.lineWidth(1);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();

        poseStack.popPose();
    }

    private void calcScreenEndPos() {
        Point pos = getElement().getEndPos();
        if (pos == null) {
            pos = Point.ZERO;
        }

        double anchorX = 0;
        double anchorY = 0;
        if (getElement().getEndAnchor() != null) {
            anchorX = Math.ceil(getScreen().width * getElement().getEndAnchor().getX());
            anchorY = Math.ceil(getScreen().height * getElement().getEndAnchor().getY());
        }

        this.endPos = Point.of(
            // mojang shrinks lines by 1/256 of the screen size for some reason
            // in the line shader, so add it back here to get full line lengths
            (int) (anchorX + pos.getX() + (getScreen().width * (1 / 256F))),
            (int) (anchorY + pos.getY() + (getScreen().height * (1 / 256F)))
        );
    }
}
