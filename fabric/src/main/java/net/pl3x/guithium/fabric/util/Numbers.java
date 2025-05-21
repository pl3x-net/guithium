package net.pl3x.guithium.fabric.util;

import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;

public abstract class Numbers {
    public static double unbox(@Nullable Double val, double def) {
        return val == null ? def : val;
    }

    public static float unbox(@Nullable Float val, float def) {
        return val == null ? def : val;
    }

    public static int unbox(@Nullable Integer val, int def) {
        return val == null ? def : val;
    }

    public static double invLerp(double val, double min, double max) {
        return (Mth.clamp(val, min, max) - min) / (max - min);
    }
}
