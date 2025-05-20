package net.pl3x.guithium.fabric.mixin;

import net.minecraft.client.gui.components.AbstractWidget;
import net.pl3x.guithium.api.gui.Vec2;
import net.pl3x.guithium.fabric.gui.element.RenderableDuck;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(AbstractWidget.class)
public abstract class AbstractWidgetMixin implements RenderableDuck {
    @Unique
    private int centerX;
    @Unique
    private int centerY;

    @Shadow
    private int x;
    @Shadow
    private int y;
    @Shadow
    protected int width;
    @Shadow
    protected int height;

    @Override
    public int getCenterX() {
        return this.centerX;
    }

    @Override
    public void setCenterX(int x) {
        this.centerX = x;
    }

    @Override
    public int getCenterY() {
        return this.centerY;
    }

    @Override
    public void setCenterY(int y) {
        this.centerY = y;
    }

    @Override
    public void calcScreenPos(int sizeX, int sizeY) {
        int anchorX, anchorY;
        Vec2 anchor = getElement().getAnchor();
        if (anchor == Vec2.ZERO) {
            anchorX = 0;
            anchorY = 0;
        } else if (anchor == Vec2.ONE) {
            anchorX = this.width;
            anchorY = this.height;
        } else {
            anchorX = this.width * anchor.getX();
            anchorY = this.height * anchor.getY();
        }

        int offsetX, offsetY;
        Vec2 offset = getElement().getOffset();
        if (offset == Vec2.ZERO) {
            offsetX = 0;
            offsetY = 0;
        } else if (offset == Vec2.ONE) {
            offsetX = this.width;
            offsetY = this.height;
        } else {
            offsetX = sizeX * offset.getX();
            offsetY = sizeY * offset.getY();
        }

        Vec2 pos = getElement().getPos();
        this.x = anchorX + pos.getX() - offsetX;
        this.y = anchorY + pos.getY() - offsetY;

        setCenterX(this.x + offsetX);
        setCenterY(this.y + offsetY);
    }
}
