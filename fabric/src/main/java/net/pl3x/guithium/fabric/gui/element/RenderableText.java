package net.pl3x.guithium.fabric.gui.element;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.MutableComponent;
import net.pl3x.guithium.api.gui.element.Text;
import net.pl3x.guithium.fabric.gui.screen.RenderableScreen;
import org.jetbrains.annotations.NotNull;

public class RenderableText extends RenderableElement {
    private MutableComponent vanillaComponent;

    public RenderableText(@NotNull RenderableScreen screen, @NotNull Text text) {
        super(screen, text);
    }

    @Override
    @NotNull
    public Text getElement() {
        return (Text) super.getElement();
    }

    @Override
    public void init(@NotNull Minecraft client, int width, int height) {
        if (getElement().getText() == null) {
            return;
        }

        this.vanillaComponent = adventureToVanilla(getElement().getText());

        int textWidth = client.font.width(this.vanillaComponent);
        int textHeight = client.font.lineHeight;

        calcScreenPos(textWidth, textHeight);

        this.centerX = this.posX + textWidth / 2;
        this.centerY = this.posY + textHeight / 2;
    }

    @Override
    public void render(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float delta) {
        if (this.vanillaComponent == null) {
            return;
        }

        rotate(gfx, this.centerX, this.centerY, getElement().getRotation());
        scale(gfx, this.scaleX, this.scaleY, getElement().getScale());

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1, 1, 1, 1);

        gfx.drawString(
                Minecraft.getInstance().font,
                this.vanillaComponent,
                this.posX,
                this.posY,
                0xFFFFFF,
                Boolean.TRUE.equals(getElement().hasShadow())
        );
    }
}
