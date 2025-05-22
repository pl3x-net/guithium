package net.pl3x.guithium.fabric.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import net.pl3x.guithium.api.gui.Screen;
import net.pl3x.guithium.api.key.Key;
import net.pl3x.guithium.fabric.GuithiumMod;
import net.pl3x.guithium.fabric.gui.element.RenderableWidget;
import net.pl3x.guithium.fabric.util.RenderQueue;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractScreen extends net.minecraft.client.gui.screens.Screen {
    protected final GuithiumMod mod;
    protected final Screen screen;
    protected final Minecraft client;
    protected final Map<Key, AbstractWidget> widgets = new LinkedHashMap<>();

    private net.minecraft.client.gui.screens.Screen previousScreen;

    public AbstractScreen(@NotNull GuithiumMod mod, @NotNull Screen screen) {
        super(Component.empty());
        this.mod = mod;
        this.screen = screen;
        this.client = Minecraft.getInstance();

        screen.getElements().forEach(element -> {
            AbstractWidget widget = RenderableWidget.create(client, this, element);
            this.widgets.put(element.getKey(), widget);
        });
    }

    @NotNull
    public Key getKey() {
        return this.screen.getKey();
    }

    @NotNull
    public Screen getScreen() {
        return this.screen;
    }

    @NotNull
    public Map<Key, AbstractWidget> getWidgets() {
        return this.widgets;
    }

    @Override
    protected void init() {
        this.widgets.forEach((key, widget) -> {
            ((RenderableWidget) widget).init();
            addRenderableWidget(widget);
        });
    }

    @Override
    public void render(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float delta) {
        gfx.pose().pushPose();
        super.render(gfx, mouseX, mouseY, delta);
        gfx.pose().popPose();
    }

    public void open() {
        if (!RenderSystem.isOnRenderThread()) {
            RenderQueue.recordRenderCall(this::open);
            return;
        }
        this.previousScreen = this.client.screen;
        this.client.setScreen(this);
    }

    public void close() {
        if (!RenderSystem.isOnRenderThread()) {
            RenderQueue.recordRenderCall(this::close);
            return;
        }
        onClose();
    }

    @Override
    public void onClose() {
        this.client.setScreen(this.previousScreen);
        this.previousScreen = null;
    }

    public void refresh() {
        if (!RenderSystem.isOnRenderThread()) {
            RenderQueue.recordRenderCall(this::refresh);
            return;
        }
        this.widgets.forEach((key, widget) -> ((RenderableWidget) widget).init());
    }
}
