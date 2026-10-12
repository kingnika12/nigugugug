/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1657
 *  net.minecraft.class_239
 *  net.minecraft.class_243
 *  net.minecraft.class_310
 *  net.minecraft.class_3959
 *  net.minecraft.class_3959$class_242
 *  net.minecraft.class_3959$class_3960
 */
package me.pepperbell.continuity.client.util;

import java.util.Comparator;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_239;
import net.minecraft.class_243;
import net.minecraft.class_310;
import net.minecraft.class_3959;

public class WorldUtils {
    private static final class_310 mc = class_310.method_1551();

    public static class_1657 findNearestPlayer(class_1657 localPlayer, float radius, boolean seeOnly, boolean ignoreFriends) {
        if (WorldUtils.mc.field_1687 == null) {
            return null;
        }
        return WorldUtils.mc.field_1687.method_18456().stream().filter(player -> player != localPlayer).filter(player -> localPlayer.method_5739((class_1297)player) <= radius).filter(player -> !seeOnly || localPlayer.method_6057((class_1297)player)).min(Comparator.comparingDouble(player -> localPlayer.method_5739((class_1297)player))).orElse(null);
    }

    public static class_239 getHitResult(double reach) {
        if (WorldUtils.mc.field_1724 == null || WorldUtils.mc.field_1687 == null) {
            return null;
        }
        class_243 start = WorldUtils.mc.field_1724.method_5836(1.0f);
        class_243 direction = WorldUtils.mc.field_1724.method_5828(1.0f);
        class_243 end = start.method_1019(direction.method_1021(reach));
        return WorldUtils.mc.field_1687.method_17742(new class_3959(start, end, class_3959.class_3960.field_17559, class_3959.class_242.field_1348, (class_1297)WorldUtils.mc.field_1724));
    }
}

