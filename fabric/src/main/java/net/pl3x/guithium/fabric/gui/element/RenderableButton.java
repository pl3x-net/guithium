package net.pl3x.guithium.fabric.gui.element;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.pl3x.guithium.api.gui.Vec2;
import net.pl3x.guithium.api.gui.element.Button;
import net.pl3x.guithium.api.network.packet.ButtonClickPacket;
import net.pl3x.guithium.fabric.Guithium;
import net.pl3x.guithium.fabric.gui.screen.RenderableScreen;
import org.jetbrains.annotations.NotNull;

public class RenderableButton extends RenderableWidget {
    public RenderableButton(@NotNull RenderableScreen screen, @NotNull Button button) {
        super(screen, button);
    }

    @Override
    @NotNull
    public Button getElement() {
        return (Button) super.getElement();
    }

    @Override
    @NotNull
    public net.minecraft.client.gui.components.Button getWidget() {
        return (net.minecraft.client.gui.components.Button) super.getWidget();
    }

    @Override
    public void init(@NotNull Minecraft client, int width, int height) {
        this.label = adventureToVanilla(getElement().getLabel());
        this.tooltip = processTooltip(getElement().getTooltip());

        Vec2 size = getElement().getSize();
        if (size == null) {
            size = Vec2.of(30 + client.font.width(this.label), 20);
        }

        calcScreenPos(size.getX(), size.getY());

        setWidget(new net.minecraft.client.gui.components.Button(
                this.posX,
                this.posY,
                (int) size.getX(),
                (int) size.getY(),
                this.label,
                (button) -> {
                    client.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                    ButtonClickPacket packet = new ButtonClickPacket(getScreen().getScreen(), getElement());
                    Guithium.instance().getNetworkHandler().getConnection().send(packet);
                },
                Supplier::get
        ) {
            @Override
            public void render(GuiGraphics gfx, int mouseX, int mouseY, float delta) {
                if (!this.visible) {
                    return;
                }
                rotate(gfx, this.getX(), this.getY(), this.width, this.height, getElement().getRotation());
                this.isHovered = mouseX >= this.getX() && mouseY >= this.getY() && mouseX < this.getX() + this.width && mouseY < this.getY() + this.height;
                renderWidget(gfx, mouseX, mouseY, delta);
            }

            @Override
            public void renderWidget(GuiGraphics gfx, int mouseX, int mouseY, float delta) {
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();
                RenderSystem.setShaderColor(1, 1, 1, 1);

                int yOffset = getTextureY(isActive(), isHoveredOrFocused());
                gfx.blitNineSliced(WIDGETS_LOCATION, this.getX(), this.getY(), this.getWidth(), this.getHeight(), 20, 4, 200, 20, 0, yOffset);

                RenderSystem.setShaderColor(1, 1, 1, 1);

                renderString(gfx, client.font, (this.active ? 0xFFFFFFFF : 0xFFA0A0A0) | Mth.ceil(this.alpha * 255.0F) << 24);
            }
        });
    }
}
