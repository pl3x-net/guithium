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
import net.pl3x.guithium.api.gui.Vec2;
import net.pl3x.guithium.api.gui.element.Line;
import net.pl3x.guithium.fabric.gui.screen.RenderableScreen;
import org.jetbrains.annotations.NotNull;

public class RenderableLine extends RenderableElement {
    private float x0, y0, x1, y1;
    private float width;
    private int startColor;
    private int endColor;

    private Vec2 endPos = Vec2.ZERO;

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

        this.x0 = this.pos.getX();
        this.y0 = this.pos.getY();
        this.x1 = this.endPos.getX();
        this.y1 = this.endPos.getY();

        this.cX = (int) (this.x0 + ((this.x1 - this.x0) / 2));
        this.cY = (int) (this.y0 + ((this.y1 - this.y0) / 2));

        this.startColor = getElement().getStartColor();
        this.endColor = getElement().getEndColor();
    }

    @Override
    public void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float delta) {
        poseStack.pushPose();

        rotate(poseStack, this.cX, this.cY, getElement().getRotation());
        scale(poseStack, this.cX, this.cY, getElement().getScale());

        // I'm not sure what this is about, but it puts it in the correct "zIndex"
        poseStack.translate(0, 0, -7.8431);

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableTexture();
        RenderSystem.setShader(GameRenderer::getRendertypeLinesShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.lineWidth(this.width);

        Matrix4f model = poseStack.last().pose();
        Matrix3f normal = poseStack.last().normal();

        BufferBuilder buf = Tesselator.getInstance().getBuilder();
        buf.begin(VertexFormat.Mode.LINES, DefaultVertexFormat.POSITION_COLOR_NORMAL);
        if (getElement().getRotation() == null || getElement().getRotation() <= 180F) {
            buf.vertex(model, this.x0, this.y0, 0).color(this.startColor).normal(normal, 1, 0, 0).endVertex();
            buf.vertex(model, this.x1, this.y1, 0).color(this.endColor).normal(normal, 1, 0, 0).endVertex();
        } else {
            buf.vertex(model, this.x1, this.y1, 0).color(this.endColor).normal(normal, 1, 0, 0).endVertex();
            buf.vertex(model, this.x0, this.y0, 0).color(this.startColor).normal(normal, 1, 0, 0).endVertex();
        }
        BufferUploader.drawWithShader(buf.end());

        RenderSystem.lineWidth(1);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();

        poseStack.popPose();
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

        this.pos = Vec2.of(
            (int) (anchorX + pos.getX() + offsetX),
            (int) (anchorY + pos.getY() + offsetY)
        );
        this.endPos = Vec2.of(
            (int) (endAnchorX + endPos.getX() + offsetX),
            (int) (endAnchorY + endPos.getY() + offsetY)
        );
    }
}
