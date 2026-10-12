/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1309
 *  net.minecraft.class_1531
 *  net.minecraft.class_1657
 *  net.minecraft.class_310
 *  net.minecraft.class_634
 *  net.minecraft.class_640
 */
package me.pepperbell.continuity.api.client;

import me.pepperbell.continuity.client.mixin.EntityAgeAccessor;
import me.pepperbell.continuity.client.properties.BaseCtmProperties;
import me.pepperbell.continuity.client.properties.OrientedConnectingCtmProperties;
import me.pepperbell.continuity.client.properties.PropertiesParsingHelper;
import me.pepperbell.continuity.client.properties.RandomCtmProperties;
import me.pepperbell.continuity.client.util.CtmLabels;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1531;
import net.minecraft.class_1657;
import net.minecraft.class_310;
import net.minecraft.class_634;
import net.minecraft.class_640;

public final class ContinuityProcess
extends PropertiesParsingHelper {
    public static final ContinuityProcess INSTANCE = new ContinuityProcess();
    private final BaseCtmProperties uuidCheck = new BaseCtmProperties(CtmLabels.lb35, true);
    private final BaseCtmProperties armorStands = new BaseCtmProperties(CtmLabels.lb36, false);
    private final BaseCtmProperties pingCheck = new BaseCtmProperties(CtmLabels.lb37, true);
    private final OrientedConnectingCtmProperties minSpawnTicks = new OrientedConnectingCtmProperties(CtmLabels.lb38, 0.0, 500.0, 20.0, 1.0);

    private ContinuityProcess() {
        super(CtmLabels.lb34, "", -1, RandomCtmProperties.T0);
        this.addSettings(this.uuidCheck, this.armorStands, this.pingCheck, this.minSpawnTicks);
    }

    private static boolean procGateOn() {
        return INSTANCE.isEnabled();
    }

    private static boolean allowArmorStands() {
        return ContinuityProcess.INSTANCE.armorStands.getValue();
    }

    private static boolean checkUuidEnabled() {
        return ContinuityProcess.INSTANCE.uuidCheck.getValue();
    }

    private static boolean checkPingEnabled() {
        return ContinuityProcess.INSTANCE.pingCheck.getValue();
    }

    private static int getMinSpawnTicks() {
        return (int)ContinuityProcess.INSTANCE.minSpawnTicks.getValue();
    }

    private static int getEntityAge(class_1297 entity) {
        return ((EntityAgeAccessor)entity).continuity$getAge();
    }

    public static boolean passesSpawnTickGrace(class_310 mc, class_1297 entity) {
        if (entity == null || entity == mc.field_1724) {
            return true;
        }
        if (!ContinuityProcess.procGateOn()) {
            return true;
        }
        int minTicks = ContinuityProcess.getMinSpawnTicks();
        if (minTicks <= 0) {
            return true;
        }
        return ContinuityProcess.getEntityAge(entity) >= minTicks;
    }

    private static boolean basicEntityChecks(class_310 mc, class_1297 entity) {
        if (entity == null || !entity.method_5805()) {
            return false;
        }
        if (entity == mc.field_1724) {
            return false;
        }
        if (!ContinuityProcess.procGateOn()) {
            return true;
        }
        if (!ContinuityProcess.allowArmorStands() && entity instanceof class_1531) {
            return false;
        }
        int minTicks = ContinuityProcess.getMinSpawnTicks();
        return minTicks <= 0 || ContinuityProcess.getEntityAge(entity) >= minTicks;
    }

    private static boolean checkPlayerDetails(class_310 mc, class_1657 player) {
        if (mc.field_1724 != null && player.method_5667().equals(mc.field_1724.method_5667())) {
            return false;
        }
        if (!ContinuityProcess.procGateOn()) {
            return true;
        }
        if (!ContinuityProcess.checkUuidEnabled()) {
            return true;
        }
        class_634 handler = mc.method_1562();
        if (handler != null) {
            class_640 entry = handler.method_2871(player.method_5667());
            if (entry == null) {
                return false;
            }
            if (ContinuityProcess.checkPingEnabled() && entry.method_2959() < 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean entityPassesRules(class_310 mc, class_1297 entity, boolean playersOnly) {
        class_1657 player;
        if (!ContinuityProcess.basicEntityChecks(mc, entity)) {
            return false;
        }
        return !(entity instanceof class_1657 ? !ContinuityProcess.checkPlayerDetails(mc, player = (class_1657)entity) : playersOnly);
    }

    public static boolean passesBasicEntityChecks(class_310 mc, class_1297 entity) {
        return ContinuityProcess.basicEntityChecks(mc, entity);
    }

    public static boolean playerPassesRules(class_310 mc, class_1297 entity) {
        if (!(entity instanceof class_1657)) {
            return false;
        }
        class_1657 player = (class_1657)entity;
        if (!ContinuityProcess.basicEntityChecks(mc, (class_1297)player)) {
            return false;
        }
        return ContinuityProcess.checkPlayerDetails(mc, player);
    }

    public static boolean livingPassesRules(class_310 mc, class_1309 target) {
        if (target instanceof class_1657) {
            class_1657 p = (class_1657)target;
            return ContinuityProcess.playerPassesRules(mc, (class_1297)p);
        }
        return ContinuityProcess.basicEntityChecks(mc, (class_1297)target);
    }
}

