package net.pl3x.guithium.fabric.gui.element;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.MultiLineTextWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.resources.ResourceLocation;
import net.pl3x.guithium.api.Guithium;
import net.pl3x.guithium.api.gui.element.Element;
import net.pl3x.guithium.api.gui.element.Radio;
import net.pl3x.guithium.api.key.Key;
import net.pl3x.guithium.api.network.Connection;
import net.pl3x.guithium.api.network.packet.ElementChangedValuePacket;
import net.pl3x.guithium.fabric.GuithiumMod;
import net.pl3x.guithium.fabric.gui.screen.AbstractScreen;
import net.pl3x.guithium.fabric.util.ComponentHelper;
import org.apache.commons.lang3.BooleanUtils;
import org.jetbrains.annotations.NotNull;

public class RenderableRadio extends net.minecraft.client.gui.components.Checkbox implements TextureSwappableWidget, RenderableWidget {
    public static final ResourceLocation RADIO_SELECTED_HIGHLIGHTED_SPRITE = ResourceLocation.fromNamespaceAndPath(Guithium.MOD_ID, "widget/radio_selected_highlighted");
    public static final ResourceLocation RADIO_SELECTED_SPRITE = ResourceLocation.fromNamespaceAndPath(Guithium.MOD_ID, "widget/radio_selected");
    public static final ResourceLocation RADIO_HIGHLIGHTED_SPRITE = ResourceLocation.fromNamespaceAndPath(Guithium.MOD_ID, "widget/radio_highlighted");
    public static final ResourceLocation RADIO_SPRITE = ResourceLocation.fromNamespaceAndPath(Guithium.MOD_ID, "widget/radio");

    private final RenderableDuck self;

    private Radio radio;

    public RenderableRadio(@NotNull Minecraft client, @NotNull AbstractScreen screen, @NotNull Radio radio) {
        super(
                0, 0, (int) radio.getSize().getX(),
                ComponentHelper.toVanilla(radio.getLabel()),
                client.font, false, null
        );
        this.self = ((RenderableDuck) this).duck(client, screen);
        this.radio = radio;
        setTooltip(Tooltip.create(ComponentHelper.toVanilla(radio.getTooltip())));
    }

    @NotNull
    public Radio getElement() {
        return this.radio;
    }

    @Override
    public void updateElement(@NotNull Element element) {
        this.radio = (Radio) element;
        this.self.getScreen().refresh();
    }

    @Override
    public float getAlpha() {
        return this.alpha;
    }

    @Override
    @NotNull
    public MultiLineTextWidget getTextWidget() {
        return this.textWidget;
    }

    @Override
    @NotNull
    public ResourceLocation getTexture() {
        if (selected()) {
            return isHoveredOrFocused() ? RADIO_SELECTED_HIGHLIGHTED_SPRITE : RADIO_SELECTED_SPRITE;
        } else {
            return isHoveredOrFocused() ? RADIO_HIGHLIGHTED_SPRITE : RADIO_SPRITE;
        }
    }

    @Override
    public void init() {
        // update contents
        setMessage(ComponentHelper.toVanilla(getElement().getLabel()));
        setTooltip(Tooltip.create(ComponentHelper.toVanilla(getElement().getTooltip())));
        this.selected = BooleanUtils.isTrue(getElement().getValue());

        // update pos/size
        setX((int) getElement().getPos().getX());
        setY((int) getElement().getPos().getY());
        setWidth(getAdjustedWidth((int) getElement().getSize().getX(), getMessage(), this.self.getClient().font));
        setHeight(getAdjustedHeight(this.self.getClient().font));

        // recalculate position on screen
        this.self.calcScreenPos(getWidth(), getHeight());
    }

    @Override
    public void renderWidget(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float delta) {
        TextureSwappableWidget.super.renderWidget(gfx, mouseX, mouseY, delta);
    }

    @Override
    public void onPress() {
        this.selected = !this.selected;

        // make sure the value is actually changed
        if (getElement().getValue() == selected()) {
            return;
        }

        // toggle this radio
        getElement().setValue(selected());

        // tell the server
        Connection conn = ((GuithiumMod) Guithium.api()).getNetworkHandler().getConnection();
        conn.send(new ElementChangedValuePacket<>(this.self.getScreen().getKey(), getElement().getKey(), selected()));

        // if this radio was toggled off, we're done.
        if (!selected()) {
            return;
        }

        // otherwise, check if this radio is in a group
        Key group = getElement().getGroup();
        if (group == null) {
            return;
        }

        // toggle off other radios in the same group
        this.self.getScreen().renderables.forEach(element -> {
            // ignore self
            if (element == this) {
                return;
            }

            // ensure is radio
            if (!(element instanceof RenderableRadio otherWidget)) {
                return;
            }

            // ensure group is the same
            Radio otherRadio = otherWidget.getElement();
            if (!group.equals(otherRadio.getGroup())) {
                return;
            }

            // only toggle it off if it's currently on
            if (!otherRadio.getValue()) {
                return;
            }

            // turn off the other radio
            otherWidget.selected = false;
            otherRadio.setValue(false);

            // tell the server
            conn.send(
                    new ElementChangedValuePacket<>(
                            this.self.getScreen().getKey(),
                            otherRadio.getKey(),
                            false
                    )
            );
        });
    }
}
