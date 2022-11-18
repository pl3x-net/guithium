package net.pl3x.guithium.fabric.gui.element;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.pl3x.guithium.api.gui.element.Text;
import net.pl3x.guithium.fabric.gui.screen.RenderableScreen;
import org.jetbrains.annotations.NotNull;

public class RenderableText extends RenderableElement {
    private MutableComponent text;

    public RenderableText(@NotNull Text text, @NotNull RenderableScreen screen) {
        super(text, screen);
    }

    @Override
    @NotNull
    public Text getElement() {
        return (Text) super.getElement();
    }

    @Override
    public void init(@NotNull Minecraft minecraft, int width, int height) {
        if (getElement().getText() == null) {
            return;
        }

        String json = GsonComponentSerializer.gson().serialize(getElement().getText());
        try {
            this.text = Component.Serializer.fromJson(json);
        } catch (Throwable t) {
            this.text = Component.translatable(json);
        }

        calcScreenPos(
            Minecraft.getInstance().font.width(this.text),
            Minecraft.getInstance().font.lineHeight
        );
    }

    @Override
    public void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float delta) {
        if (this.text == null) {
            return;
        }

        poseStack.pushPose();

        MultiBufferSource.BufferSource immediate = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
        Minecraft.getInstance().font.drawInBatch(
            this.text,
            this.pos.getX(),
            this.pos.getY(),
            0xFFFFFF,
            Boolean.TRUE.equals(getElement().hasShadow()),
            poseStack.last().pose(),
            immediate,
            false,
            0,
            0xF000F0
        );
        immediate.endBatch();

        poseStack.popPose();
    }
}
