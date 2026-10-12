/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1657
 *  net.minecraft.class_243
 *  net.minecraft.class_310
 *  net.minecraft.class_3532
 */
package me.pepperbell.continuity.client.util;

import me.pepperbell.continuity.client.util.rotation.BakedModelManagerMixin;
import net.minecraft.class_1657;
import net.minecraft.class_243;
import net.minecraft.class_310;
import net.minecraft.class_3532;

public class ReloadableResourceManagerImplAccessor {
    public static BakedModelManagerMixin getDirection(class_1657 player, class_243 target) {
        class_243 eyePos = player.method_33571();
        class_243 diff = target.method_1020(eyePos);
        double horizontalDistance = Math.sqrt(diff.field_1352 * diff.field_1352 + diff.field_1350 * diff.field_1350);
        float yaw = (float)Math.toDegrees(Math.atan2(diff.field_1350, diff.field_1352)) - 90.0f;
        float pitch = (float)(-Math.toDegrees(Math.atan2(diff.field_1351, horizontalDistance)));
        return new BakedModelManagerMixin(yaw, pitch);
    }

    public static double getAngleToRotation(BakedModelManagerMixin rotation) {
        if (class_310.method_1551().field_1724 == null) {
            return 0.0;
        }
        float yawDiff = class_3532.method_15393((float)(rotation.yaw() - class_310.method_1551().field_1724.method_36454()));
        float pitchDiff = class_3532.method_15393((float)(rotation.pitch() - class_310.method_1551().field_1724.method_36455()));
        return Math.sqrt(yawDiff * yawDiff + pitchDiff * pitchDiff);
    }
}

