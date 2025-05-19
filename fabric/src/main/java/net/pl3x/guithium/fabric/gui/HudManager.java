package net.pl3x.guithium.fabric.gui;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.pl3x.guithium.api.key.Key;
import net.pl3x.guithium.fabric.gui.screen.AbstractScreen;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HudManager {
    private final Map<Key, AbstractScreen> screens = new HashMap<>();

    public void add(@NotNull AbstractScreen screen) {
        this.screens.put(screen.getKey(), screen);
        screen.refresh();
    }

    @NotNull
    public Map<Key, AbstractScreen> getAll() {
        return this.screens;
    }

    @Nullable
    public AbstractScreen get(@NotNull Key key) {
        return this.screens.get(key);
    }

    @Nullable
    public AbstractScreen remove(@NotNull Key key) {
        return this.screens.remove(key);
    }

    public void clear() {
        this.screens.clear();
    }

    public void render(@NotNull GuiGraphics gfx, float delta) {
        if (!Minecraft.getInstance().gui.getDebugOverlay().showDebugScreen()) {
            this.screens.forEach((key, screen) -> screen.render(gfx, 0, 0, delta));
        }
    }

    public void refresh() {
        this.screens.forEach((key, screen) -> screen.refresh());
    }
}
