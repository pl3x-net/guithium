package net.pl3x.guithium.fabric.util;

import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ComponentHelper {
    @NotNull
    public static Component toVanilla(@Nullable net.kyori.adventure.text.Component adventure) {
        if (adventure == null) {
            return Component.empty();
        }
        String json = GsonComponentSerializer.gson().serialize(adventure);
        Component vanilla;
        try {
            vanilla = Component.Serializer.fromJson(json, RegistryAccess.EMPTY);
        } catch (Throwable t) {
            vanilla = Component.translatable(json);
        }
        return vanilla == null ? Component.empty() : vanilla;
    }
}
