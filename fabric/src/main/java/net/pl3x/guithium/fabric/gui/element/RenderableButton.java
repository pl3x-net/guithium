package net.pl3x.guithium.fabric.gui.element;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.pl3x.guithium.api.gui.element.Button;
import net.pl3x.guithium.api.gui.element.Element;
import net.pl3x.guithium.api.network.packet.ElementClickedPacket;
import net.pl3x.guithium.fabric.gui.screen.AbstractScreen;
import net.pl3x.guithium.fabric.util.ComponentHelper;
import org.jetbrains.annotations.NotNull;

public class RenderableButton extends net.minecraft.client.gui.components.Button implements RenderableWidget {
    private final RenderableDuck self;

    private Button button;

    public RenderableButton(@NotNull Minecraft client, @NotNull AbstractScreen screen, @NotNull Button button) {
        super(0, 0, 0, 0, Component.empty(), null, Supplier::get);
        this.self = ((RenderableDuck) this).duck(client, screen);
        this.button = button;
    }

    @NotNull
    public Button getElement() {
        return this.button;
    }

    @Override
    public void updateElement(@NotNull Element element) {
        this.button = (Button) element;
        this.self.getScreen().refresh();
    }

    @Override
    public void init() {
        // update contents
        setMessage(ComponentHelper.toVanilla(this.button.getLabel()));
        setTooltip(Tooltip.create(ComponentHelper.toVanilla(this.button.getTooltip())));

        // update pos/size
        setX((int) getElement().getPos().getX());
        setY((int) getElement().getPos().getY());
        setWidth((int) getElement().getSize().getX());
        setHeight((int) getElement().getSize().getY());

        // recalculate position on screen
        this.self.calcScreenPos(getWidth(), getHeight());
    }

    @Override
    public void renderWidget(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float delta) {
        this.self.rotate(gfx, this.self.getCenterX(), this.self.getCenterY(), getElement().getRotation());
        this.self.scale(gfx, this.self.getCenterX(), this.self.getCenterY(), getElement().getScale());

        super.renderWidget(gfx, mouseX, mouseY, delta);
    }

    @Override
    public void onPress() {
        this.self.getClient().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        conn().send(new ElementClickedPacket<>(this.self.getScreen().getScreen(), getElement()));
    }
}
