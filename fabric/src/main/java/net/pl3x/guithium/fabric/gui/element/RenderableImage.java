package net.pl3x.guithium.fabric.gui.element;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.pl3x.guithium.api.gui.Vec2;
import net.pl3x.guithium.api.gui.Vec4;
import net.pl3x.guithium.api.gui.element.Image;
import net.pl3x.guithium.fabric.Guithium;
import net.pl3x.guithium.fabric.gui.screen.RenderableScreen;
import net.pl3x.guithium.fabric.gui.texture.Texture;
import org.jetbrains.annotations.NotNull;

public class RenderableImage extends RenderableElement {
    private final Texture texture;
    private float x0, y0, x1, y1;
    private float u0, v0, u1, v1;
    private int vertexColor;

    public RenderableImage(@NotNull RenderableScreen screen, @NotNull Image image) {
        super(screen, image);
        this.texture = Guithium.instance().getTextureManager().getOrCreate(image);
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
        Vec2 size = getElement().getSize();
        if (size == null) {
            size = Vec2.of(width, height);
        }

        calcScreenPos(size.getX(), size.getY());

        this.x0 = this.posX;
        this.y0 = this.posY;
        this.x1 = this.x0 + size.getX();
        this.y1 = this.y0 + size.getY();

        this.centerX = (int) (this.x0 + size.getX() / 2);
        this.centerY = (int) (this.y0 + size.getY() / 2);

        Vec4 uv = getElement().getUV();
        Float tileMod = getElement().getTileModifier();
        if (uv != null) {
            this.u0 = uv.getX0();
            this.v0 = uv.getY0();
            this.u1 = uv.getX1();
            this.v1 = uv.getY1();
            if (tileMod != null) {
                this.u1 /= tileMod;
                this.v1 /= tileMod;
            }
        } else {
            this.u0 = 0;
            this.v0 = 0;
            if (tileMod == null) {
                this.u1 = 1;
                this.v1 = 1;
            } else {
                this.u1 = this.x1 / tileMod;
                this.v1 = this.y1 / tileMod;
            }
        }

        this.vertexColor = getElement().getVertexColor() == null ? 0xFFFFFFFF : getElement().getVertexColor();
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        if (!getTexture().isLoaded()) {
            return;
        }

        rotate(guiGraphics, this.centerX, this.centerY, getElement().getRotation());
        scale(guiGraphics, this.centerX, this.centerY, getElement().getScale());

        getTexture().render(guiGraphics, this.x0, this.y0, this.x1, this.y1, this.u0, this.v0, this.u1, this.v1, this.vertexColor);
    }
}
