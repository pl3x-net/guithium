package net.pl3x.servergui.fabric.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.pl3x.servergui.fabric.ServerGUIFabric;

public abstract class AbstractScreen extends Screen {
    private static final Text BLANK = Text.of("");

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
    public void close() {
        if (!RenderSystem.isOnRenderThread()) {
            RenderSystem.recordRenderCall(this::close);
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
