/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_243
 *  net.minecraft.class_310
 *  net.minecraft.class_3532
 */
package me.pepperbell.continuity.client.util;

import net.minecraft.class_1297;
import net.minecraft.class_243;
import net.minecraft.class_310;
import net.minecraft.class_3532;

public class CtmMath {
    private static final class_310 mc = class_310.method_1551();

    public static boolean isInFOV(class_1297 entity, double fov) {
        class_243 lookDirection;
        if (CtmMath.mc.field_1724 == null || entity == null) {
            return false;
        }
        class_243 cameraPos = CtmMath.mc.field_1724.method_5836(1.0f);
        class_243 targetPos = entity.method_5836(1.0f);
        class_243 direction = targetPos.method_1020(cameraPos).method_1029();
        double dot = direction.method_1026(lookDirection = CtmMath.mc.field_1724.method_5828(1.0f));
        double angle = Math.acos(class_3532.method_15350((double)dot, (double)-1.0, (double)1.0));
        double angleDegrees = Math.toDegrees(angle);
        return angleDegrees <= fov / 2.0;
    }
}

