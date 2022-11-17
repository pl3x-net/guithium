package net.pl3x.guithium.fabric.gui.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public abstract class AbstractScreen extends Screen {
    private static final Component BLANK = Component.literal("");

    protected final Screen parent;

    protected int centerX;

    public AbstractScreen(Screen parent) {
        super(BLANK);
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();
        this.centerX = (int) (this.width / 2F);
    }

    @Override
    public void onClose() {
        Minecraft.getInstance().setScreen(this.parent);
    }
}
