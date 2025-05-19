package net.pl3x.guithium.fabric.gui.element;

import java.util.function.Supplier;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Tooltip;
import net.pl3x.guithium.api.gui.element.Button;
import net.pl3x.guithium.fabric.util.ComponentHelper;
import org.jetbrains.annotations.NotNull;

public class TestButton extends net.minecraft.client.gui.components.Button implements RenderableWidget, Tickable {
    private final Button button;

    public TestButton(@NotNull net.pl3x.guithium.api.gui.element.Button button) {
        super(
                button.getPos().x(),
                button.getPos().y(),
                button.getSize().x(),
                button.getSize().y(),
                ComponentHelper.toVanilla(button.getLabel()),
                null,
                Supplier::get
        );
        this.button = button;

        setTooltip(Tooltip.create(ComponentHelper.toVanilla(button.getTooltip())));
    }

    @Override
    public void init() {
        this.x = button.getPos().x();
        this.y = button.getPos().y();
        this.width = button.getSize().x();
        this.height = button.getSize().y();
        setMessage(ComponentHelper.toVanilla(this.button.getLabel()));
        setTooltip(Tooltip.create(ComponentHelper.toVanilla(button.getTooltip())));
    }

    @Override
    public void render(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float delta) {
        super.render(gfx, mouseX, mouseY, delta);
    }

    @Override
    public void tick() {
        //
    }
}
