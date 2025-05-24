package net.pl3x.guithium.fabric.util;

import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ComponentHelper {
    @NotNull
    public static Component toVanilla(@Nullable net.kyori.adventure.text.Component adventure) {
        return toVanilla(adventure == null ? null : GsonComponentSerializer.gson().serialize(adventure));
    }

    @NotNull
    public static Component toVanilla(@Nullable String json) {
        Component vanilla = null;
        if (json != null) {
            try {
                vanilla = Component.Serializer.fromJson(json, RegistryAccess.EMPTY);
            } catch (Throwable t) {
                vanilla = Component.translatable(json);
            }
        }
        return vanilla == null ? Component.empty() : vanilla;
    }
}
