package net.pl3x.servergui.fabric.gui.hud;

import net.minecraft.client.util.math.MatrixStack;
import net.pl3x.servergui.api.gui.Gui;
import net.pl3x.servergui.fabric.ServerGUIFabric;
import net.pl3x.servergui.fabric.gui.RenderableGui;

public class HudManager {
    private final ServerGUIFabric mod;

    public HudManager(ServerGUIFabric mod) {
        this.mod = mod;
    }

    public void render(MatrixStack matrix, float delta) {
        this.mod.getGuiManager().getAll().forEach(gui -> {
            if (gui.getType() != Gui.Type.HUD) {
                return;
            }
            ((RenderableGui) gui).render(matrix);
        });
    }
}
