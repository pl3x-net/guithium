package net.pl3x.guithium.fabric.gui.screen;

import com.google.common.base.Preconditions;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.pl3x.guithium.api.Key;
import net.pl3x.guithium.api.gui.Screen;
import net.pl3x.guithium.api.net.packet.CloseScreenPacket;
import net.pl3x.guithium.fabric.Guithium;
import net.pl3x.guithium.fabric.gui.element.RenderableElement;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class RenderableScreen extends AbstractScreen {
    private final Screen screen;
    private final Map<Key, RenderableElement> elements = new HashMap<>();

    private final Screen.Background background;

    public RenderableScreen(@NotNull Screen screen) {
        super(Minecraft.getInstance() == null ? null : Minecraft.getInstance().screen);

        Preconditions.checkNotNull(screen, "Screen cannot be null");
        this.screen = screen;

        this.background = screen.getType() == Screen.Type.HUD ? Screen.Background.CLEAR : screen.getBackground() == null ? Screen.Background.GRADIENT : screen.getBackground();

        screen.getElements().forEach((key, element) -> {
            RenderableElement renderableElement = RenderableElement.createRenderableElement(element, this);
            if (renderableElement != null) {
                this.elements.put(renderableElement.getElement().getKey(), renderableElement);
            }
        });
    }

    @NotNull
    public Screen getScreen() {
        return this.screen;
    }

    @NotNull
    public Map<Key, RenderableElement> getElements() {
        return this.elements;
    }

    @Override
    protected void init() {
        this.elements.forEach((key, element) -> {
            element.init(this.minecraft, this.width, this.height);
            if (element.getRenderableWidget() != null) {
                addRenderableWidget(element.getRenderableWidget());
            }
        });
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float delta) {
        switch (this.background) {
            case TEXTURE -> renderDirtBackground(0);
            case GRADIENT -> fillGradient(poseStack, 0, 0, this.width, this.height, 0xC0101010, 0xD0101010);
        }

        this.elements.forEach((key, element) -> element.render(poseStack, mouseX, mouseY, delta));
    }

    @Override
    public void onClose() {
        Guithium.instance().getNetworkHandler().getConnection().send(new CloseScreenPacket(this.screen));
        super.onClose();
    }

    public void close() {
        if (!RenderSystem.isOnRenderThread()) {
            RenderSystem.recordRenderCall(this::close);
            return;
        }
        onClose();
    }

    public void open() {
        if (!RenderSystem.isOnRenderThread()) {
            RenderSystem.recordRenderCall(this::open);
            return;
        }
        Minecraft.getInstance().setScreen(this);
    }

    public void refresh() {
        init(
            Minecraft.getInstance(),
            Minecraft.getInstance().getWindow().getGuiScaledWidth(),
            Minecraft.getInstance().getWindow().getGuiScaledHeight()
        );
    }
}
