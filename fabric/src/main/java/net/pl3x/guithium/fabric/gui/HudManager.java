package net.pl3x.guithium.fabric.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.pl3x.guithium.api.Key;
import net.pl3x.guithium.fabric.gui.screen.RenderableScreen;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class HudManager {
    private final Map<Key, RenderableScreen> screens = new HashMap<>();

    public void add(@NotNull RenderableScreen screen) {
        this.screens.put(screen.getScreen().getKey(), screen);
        screen.refresh();
    }

    @NotNull
    public Map<Key, RenderableScreen> getAll() {
        return this.screens;
    }

    @Nullable
    public RenderableScreen get(@NotNull Key key) {
        return this.screens.get(key);
    }

    @Nullable
    public RenderableScreen remove(@NotNull Key key) {
        return this.screens.remove(key);
    }

    public void clear() {
        this.screens.clear();
    }

    public void render(@NotNull PoseStack poseStack, float delta) {
        if (!Minecraft.getInstance().options.renderDebug) {
            this.screens.forEach((key, screen) -> screen.render(poseStack, 0, 0, delta));
        }
    }

    public void refresh() {
        this.screens.forEach((key, screen) -> screen.refresh());
    }
}
