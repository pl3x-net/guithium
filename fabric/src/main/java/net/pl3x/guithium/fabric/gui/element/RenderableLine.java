package net.pl3x.guithium.fabric.gui.element;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.pl3x.guithium.api.gui.Vec2;
import net.pl3x.guithium.api.gui.element.Line;
import net.pl3x.guithium.fabric.gui.screen.RenderableScreen;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class RenderableLine extends RenderableElement {
    private float x0, y0, x1, y1;
    private float width;
    private int startColor;
    private int endColor;

    private int endPosX = 0;
    private int endPosY = 0;

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
            // compensate for shader weirdness
            this.width = getElement().getWidth() * (float) Minecraft.getInstance().getWindow().getGuiScale();
        }

        calcScreenPos(width, height);

        this.x0 = this.posX;
        this.y0 = this.posY;
        this.x1 = this.endPosX;
        this.y1 = this.endPosY;

        this.centerX = (int) (this.x0 + ((this.x1 - this.x0) / 2));
        this.centerY = (int) (this.y0 + ((this.y1 - this.y0) / 2));

        this.startColor = getElement().getStartColor();
        this.endColor = getElement().getEndColor();
    }

    @Override
    public void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float delta) {
        rotate(poseStack, this.centerX, this.centerY, getElement().getRotation());
        scale(poseStack, this.scaleX, this.scaleY, getElement().getScale());

        // I'm not sure what this is about, but it puts it in the correct "zIndex"
        poseStack.translate(0, 0, -7.8431);

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getRendertypeLinesShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.lineWidth(this.width);

        Matrix4f model = poseStack.last().pose();
        Matrix3f normal = poseStack.last().normal();

        BufferBuilder buf = Tesselator.getInstance().getBuilder();
        buf.begin(VertexFormat.Mode.LINES, DefaultVertexFormat.POSITION_COLOR_NORMAL);
        if ((getElement().getRotation() == null || getElement().getRotation() <= 180F) && this.x0 < this.x1) {
            buf.vertex(model, this.x0, this.y0, 0).color(this.startColor).normal(normal, 1, 0, 0).endVertex();
            buf.vertex(model, this.x1, this.y1, 0).color(this.endColor).normal(normal, 1, 0, 0).endVertex();
        } else {
            buf.vertex(model, this.x1, this.y1, 0).color(this.endColor).normal(normal, 1, 0, 0).endVertex();
            buf.vertex(model, this.x0, this.y0, 0).color(this.startColor).normal(normal, 1, 0, 0).endVertex();
        }
        BufferUploader.drawWithShader(buf.end());
    }

    @Override
    protected void calcScreenPos(float width, float height) {
        Vec2 pos = getElement().getPos();
        if (pos == null) {
            pos = Vec2.ZERO;
        }
        Vec2 endPos = getElement().getEndPos();
        if (endPos == null) {
            endPos = Vec2.ZERO;
        }

        double anchorX = 0;
        double anchorY = 0;
        if (getElement().getAnchor() != null) {
            anchorX = Math.ceil(getScreen().width * getElement().getAnchor().getX());
            anchorY = Math.ceil(getScreen().height * getElement().getAnchor().getY());
        }
        double endAnchorX = 0;
        double endAnchorY = 0;
        if (getElement().getEndAnchor() != null) {
            endAnchorX = Math.ceil(getScreen().width * getElement().getEndAnchor().getX());
            endAnchorY = Math.ceil(getScreen().height * getElement().getEndAnchor().getY());
        }

        // mojang shrinks lines by 1/256 of the screen size for some reason
        // in the line shader, so add it back here to get full line lengths
        float offsetX = getScreen().width * (1 / 256F);
        float offsetY = getScreen().height * (1 / 256F);

        this.posX = (int) (anchorX + pos.getX() + offsetX);
        this.posY = (int) (anchorY + pos.getY() + offsetY);
        this.endPosX = (int) (endAnchorX + endPos.getX() + offsetX);
        this.endPosY = (int) (endAnchorY + endPos.getY() + offsetY);
    }
}
