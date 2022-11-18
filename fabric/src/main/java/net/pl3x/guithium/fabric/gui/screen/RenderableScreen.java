package net.pl3x.guithium.fabric.gui.screen;

import com.google.common.base.Preconditions;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.pl3x.guithium.api.Key;
import net.pl3x.guithium.api.gui.Screen;
import net.pl3x.guithium.api.gui.element.Tickable;
import net.pl3x.guithium.api.net.packet.CloseScreenPacket;
import net.pl3x.guithium.fabric.Guithium;
import net.pl3x.guithium.fabric.gui.element.RenderableElement;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;

public class RenderableScreen extends AbstractScreen {
    private final Screen screen;
    private final Map<Key, RenderableElement> elements = new LinkedHashMap<>();

    public RenderableScreen(@NotNull Screen screen) {
        super(Minecraft.getInstance() == null ? null : Minecraft.getInstance().screen);

        Preconditions.checkNotNull(screen, "Screen cannot be null");
        this.screen = screen;

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
        if (this.minecraft == null) {
            return;
        }
        this.elements.forEach((key, element) -> {
            element.init(this.minecraft, this.width, this.height);
            if (element.getRenderableWidget() != null) {
                addRenderableWidget(element.getRenderableWidget());
            }
            System.out.println(element.getElement().toJson());
        });
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float delta) {
        this.elements.forEach((key, element) -> element.render(poseStack, mouseX, mouseY, delta));
    }

    @Override
    public void tick() {
        this.elements.forEach((key, element) -> {
            if (element instanceof Tickable tickable) {
                tickable.tick();
            }
        });
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
