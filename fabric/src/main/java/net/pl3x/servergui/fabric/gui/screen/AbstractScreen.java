package net.pl3x.servergui.fabric.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.pl3x.servergui.fabric.ServerGUIFabric;

public abstract class AbstractScreen extends Screen {
    private static final Component BLANK = Component.literal("");

    protected final Screen parent;

    protected int centerX;

    public AbstractScreen(Screen parent) {
        super(BLANK);
        this.parent = parent;
    }

    @Override
    public void init() {
        super.init();
        this.centerX = (int) (this.width / 2F);
    }

    @Override
    public void onClose() {
        if (!RenderSystem.isOnRenderThread()) {
            RenderSystem.recordRenderCall(this::onClose);
            return;
        }
        ServerGUIFabric.client.setScreen(this.parent);
    }

    public void open() {
        if (!RenderSystem.isOnRenderThread()) {
            RenderSystem.recordRenderCall(this::open);
            return;
        }
        ServerGUIFabric.client.setScreen(this);
    }
}
