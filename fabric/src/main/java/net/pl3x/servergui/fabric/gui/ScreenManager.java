package net.pl3x.servergui.fabric.gui;

import net.minecraft.client.util.math.MatrixStack;
import net.pl3x.servergui.api.Key;
import net.pl3x.servergui.fabric.ServerGUIFabric;
import net.pl3x.servergui.fabric.gui.screen.RenderableScreen;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ScreenManager {
    private final Map<Key, RenderableScreen> screens = new ConcurrentHashMap<>();

    public void add(RenderableScreen renderableScreen) {
        this.screens.put(renderableScreen.getScreen().getKey(), renderableScreen);
    }

    public Map<Key, RenderableScreen> getAll() {
        return this.screens;
    }

    public RenderableScreen get(Key key) {
        return this.screens.get(key);
    }

    public RenderableScreen remove(Key key) {
        return this.screens.remove(key);
    }

    public void clear() {
        this.screens.clear();
    }

    public void render(MatrixStack matrix, float delta) {
        if (!ServerGUIFabric.client.options.debugEnabled) {
            this.screens.forEach((key, screen) -> screen.render(matrix, 0, 0, delta));
        }
    }
}
