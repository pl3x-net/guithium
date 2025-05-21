package net.pl3x.guithium.fabric.gui.element;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import net.pl3x.guithium.api.Guithium;
import net.pl3x.guithium.api.gui.element.Radio;
import net.pl3x.guithium.api.key.Key;
import net.pl3x.guithium.api.network.Connection;
import net.pl3x.guithium.api.network.packet.RadioTogglePacket;
import net.pl3x.guithium.fabric.GuithiumMod;
import net.pl3x.guithium.fabric.gui.screen.AbstractScreen;
import net.pl3x.guithium.fabric.util.ComponentHelper;
import org.apache.commons.lang3.BooleanUtils;
import org.jetbrains.annotations.NotNull;

public class RenderableRadio extends RenderableCheckbox implements RenderableWidget, Tickable {
    public static final ResourceLocation RADIO_SELECTED_HIGHLIGHTED_SPRITE = ResourceLocation.fromNamespaceAndPath(Guithium.MOD_ID, "widget/radio_selected_highlighted");
    public static final ResourceLocation RADIO_SELECTED_SPRITE = ResourceLocation.fromNamespaceAndPath(Guithium.MOD_ID, "widget/radio_selected");
    public static final ResourceLocation RADIO_HIGHLIGHTED_SPRITE = ResourceLocation.fromNamespaceAndPath(Guithium.MOD_ID, "widget/radio_highlighted");
    public static final ResourceLocation RADIO_SPRITE = ResourceLocation.fromNamespaceAndPath(Guithium.MOD_ID, "widget/radio");

    private final Radio radio;

    public RenderableRadio(@NotNull Radio radio) {
        super(
                radio.getPos().getX(),
                radio.getPos().getY(),
                radio.getSize().getX(), // max width
                ComponentHelper.toVanilla(radio.getLabel()),
                Minecraft.getInstance().font,
                BooleanUtils.isTrue(radio.isSelected()),
                (chk, bl) -> {
                }
        );
        this.radio = radio;
        setTooltip(Tooltip.create(ComponentHelper.toVanilla(radio.getTooltip())));
    }

    @NotNull
    public Radio getElement() {
        return this.radio;
    }

    @Override
    public void init(@NotNull Minecraft client) {
        // update contents
        setMessage(ComponentHelper.toVanilla(this.radio.getLabel()));
        setTooltip(Tooltip.create(ComponentHelper.toVanilla(this.radio.getTooltip())));

        // update pos/size
        setX(this.radio.getPos().getX());
        setY(this.radio.getPos().getY());
        this.width = this.getAdjustedWidth(this.radio.getSize().getX(), this.message, client.font);
        this.height = this.getAdjustedHeight(client.font);

        // recalculate position on screen
        RenderableDuck self = (RenderableDuck) this;
        self.calcScreenPos(this.width, this.height);
    }

    @Override
    public void renderWidget(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float delta) {
        RenderableDuck self = (RenderableDuck) this;
        self.rotate(gfx, self.getCenterX(), self.getCenterY(), this.radio.getRotation());
        self.scale(gfx, self.getCenterX(), self.getCenterY(), this.radio.getScale());
        //super.renderWidget(gfx, mouseX, mouseY, delta);

        ResourceLocation resourceLocation;
        if (selected()) {
            resourceLocation = this.isHoveredOrFocused() ? RADIO_SELECTED_HIGHLIGHTED_SPRITE : RADIO_SELECTED_SPRITE;
        } else {
            resourceLocation = this.isHoveredOrFocused() ? RADIO_HIGHLIGHTED_SPRITE : RADIO_SPRITE;
        }

        int size = getBoxSize(Minecraft.getInstance().font);
        gfx.blitSprite(RenderType::guiTextured, resourceLocation, this.getX(), this.getY(), size, size, ARGB.white(this.alpha));
        int textX = this.getX() + size + 4;
        int textY = this.getY() + size / 2 - this.textWidget.getHeight() / 2;
        this.textWidget.setPosition(textX, textY);
        this.textWidget.renderWidget(gfx, mouseX, mouseY, delta);
    }

    @Override
    public void tick() {
        //
    }

    @Override
    public void onPress() {
        super.onPress();

        // make sure the value is actually changed
        if (Boolean.TRUE.equals(getElement().isSelected()) == selected()) {
            return;
        }

        // toggle this radio
        getElement().setSelected(selected());

        Connection conn = ((GuithiumMod) Guithium.api()).getNetworkHandler().getConnection();
        AbstractScreen screen = (AbstractScreen) Minecraft.getInstance().screen;
        if (screen == null) {
            return;
        }

        // tell the server
        conn.send(new RadioTogglePacket(screen.getKey(), getElement().getKey(), selected()));

        // if this radio was toggled off, we're done.
        if (!selected()) {
            return;
        }

        // check if this radio is in a group
        Key group = getElement().getGroup();
        if (group == null) {
            return;
        }

        // toggle off other radios in the same group
        screen.renderables.forEach(element -> {
            // ignore self
            if (element == this) {
                return;
            }

            // ensure is radio
            if (!(element instanceof RenderableRadio other)) {
                return;
            }

            // ensure group is the same
            Radio otherRadio = other.getElement();
            if (!group.equals(otherRadio.getGroup())) {
                return;
            }

            // only toggle it off if it's currently on
            if (!Boolean.TRUE.equals(otherRadio.isSelected())) {
                return;
            }

            // finally turn off the other radio and tell the server
            other.selected = false;
            otherRadio.setSelected(false);
            conn.send(new RadioTogglePacket(screen.getKey(), otherRadio.getKey(), false));
        });
    }
}
