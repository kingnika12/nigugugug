/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1657
 *  net.minecraft.class_238
 *  net.minecraft.class_239$class_240
 *  net.minecraft.class_241
 *  net.minecraft.class_243
 *  net.minecraft.class_3959
 *  net.minecraft.class_3959$class_242
 *  net.minecraft.class_3959$class_3960
 *  net.minecraft.class_4587
 */
package me.pepperbell.continuity.api.client;

import me.pepperbell.continuity.api.client.ContinuityProcess;
import me.pepperbell.continuity.client.processor.simple.CtmFixer;
import me.pepperbell.continuity.client.properties.BaseCtmProperties;
import me.pepperbell.continuity.client.properties.OrientedConnectingCtmProperties;
import me.pepperbell.continuity.client.properties.PropertiesParsingHelper;
import me.pepperbell.continuity.client.properties.RandomCtmProperties;
import me.pepperbell.continuity.client.util.CtmLabels;
import me.pepperbell.continuity.client.util.CtmMath;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_238;
import net.minecraft.class_239;
import net.minecraft.class_241;
import net.minecraft.class_243;
import net.minecraft.class_3959;
import net.minecraft.class_4587;

public final class CtmLoader
extends PropertiesParsingHelper {
    private final BaseCtmProperties playerOnly = new BaseCtmProperties(CtmLabels.lb26, true);
    private final BaseCtmProperties invisibles = new BaseCtmProperties(CtmLabels.lb13, true);
    private final BaseCtmProperties requireLeftClick = new BaseCtmProperties(CtmLabels.lb27, true);
    private final OrientedConnectingCtmProperties maxDistance = new OrientedConnectingCtmProperties(CtmLabels.lb28, 0.5, 12.0, 6.0, 0.5);
    private final OrientedConnectingCtmProperties fov = new OrientedConnectingCtmProperties(CtmLabels.lb29, 1.0, 360.0, 360.0, 1.0);
    private final OrientedConnectingCtmProperties maxAngle = new OrientedConnectingCtmProperties(CtmLabels.lb30, 10.0, 180.0, 70.0, 1.0);
    private final BaseCtmProperties slot = new BaseCtmProperties(CtmLabels.lb31, true);
    private final OrientedConnectingCtmProperties slotNumber = new OrientedConnectingCtmProperties(CtmLabels.lb08, 1.0, 9.0, 1.0, 1.0);
    private final BaseCtmProperties noWall = new BaseCtmProperties(CtmLabels.lb32, false);
    private class_1297 lockedTarget = null;

    public CtmLoader() {
        super(CtmLabels.lb24, CtmLabels.lb25, -1, RandomCtmProperties.T0);
        this.addSettings(this.playerOnly, this.invisibles, this.requireLeftClick, this.maxDistance, this.fov, this.maxAngle, this.slot, this.slotNumber, this.noWall);
    }

    @Override
    public void onRender3D(class_4587 matrixStack, float partialTicks) {
    }

    @Override
    public void onCtmFixer(CtmFixer event) {
        this.onRenderHud(event.getDrawContext().method_51448(), event.getTickDelta());
    }

    private void onRenderHud(class_4587 matrixStack, float tickDelta) {
        if (this.mc.field_1724 == null || this.mc.field_1687 == null) {
            this.lockedTarget = null;
            return;
        }
        if (this.requireLeftClick.getValue() && !this.mc.field_1690.field_1886.method_1434()) {
            this.lockedTarget = null;
            return;
        }
        class_243 cameraPos = this.mc.field_1724.method_5836(tickDelta);
        class_241 cameraRot = this.clamp180(this.mc.field_1724.method_5802());
        class_1297 selectedTarget = null;
        class_241 rotationDelta = null;
        float bestTotalAngle = Float.MAX_VALUE;
        for (class_1297 entity : this.mc.field_1687.method_18112()) {
            float totalAngle;
            if (entity == null || entity == this.mc.field_1724 || !entity.method_5805() || !CtmMath.isInFOV(entity, this.fov.getValueInt()) || this.invisibles.getValue() && entity.method_5756((class_1657)this.mc.field_1724) || entity.method_5655() || this.slot.getValue() && this.mc.field_1724.method_31548().field_7545 != this.slotNumber.getValueInt() - 1 || this.playerOnly.getValue() && !(entity instanceof class_1657) || !ContinuityProcess.entityPassesRules(this.mc, entity, this.playerOnly.getValue())) continue;
            class_238 box = entity.method_5829();
            double headY = box.field_1322 + (box.field_1325 - box.field_1322) * 0.85;
            class_243 headPos = new class_243((box.field_1323 + box.field_1320) * 0.5, headY, (box.field_1321 + box.field_1324) * 0.5);
            if (this.noWall.getValue() && this.mc.field_1687.method_17742(new class_3959(cameraPos, headPos, class_3959.class_3960.field_17558, class_3959.class_242.field_1348, (class_1297)this.mc.field_1724)).method_17783() != class_239.class_240.field_1333) continue;
            class_241 targetRotationDelta = this.getRotationDelta(cameraRot, cameraPos, headPos);
            if (cameraPos.method_1022(headPos) > this.maxDistance.getValue() || Math.abs(targetRotationDelta.field_1342) > this.maxAngle.getValueFloat() || !((totalAngle = Math.abs(targetRotationDelta.field_1343) + Math.abs(targetRotationDelta.field_1342)) < bestTotalAngle)) continue;
            bestTotalAngle = totalAngle;
            selectedTarget = entity;
            rotationDelta = targetRotationDelta;
        }
        if (rotationDelta == null || selectedTarget == null) {
            this.lockedTarget = null;
            return;
        }
        this.lockedTarget = selectedTarget;
        float speed = 0.1f;
        this.mc.field_1724.method_36457(this.mc.field_1724.method_36455() + speed * rotationDelta.field_1343);
        this.mc.field_1724.method_36456(this.mc.field_1724.method_36454() + speed * rotationDelta.field_1342);
    }

    private class_241 getRotationDelta(class_241 cameraRot, class_243 cameraPos, class_243 targetPos) {
        class_243 delta = targetPos.method_1020(cameraPos);
        double hypxz = Math.sqrt(delta.field_1352 * delta.field_1352 + delta.field_1350 * delta.field_1350);
        if (hypxz == 0.0) {
            return new class_241(0.0f, 0.0f);
        }
        float targetPitch = (float)(-Math.toDegrees(Math.atan2(delta.field_1351, hypxz)));
        float targetYaw = (float)Math.toDegrees(Math.atan2(delta.field_1350, delta.field_1352)) - 90.0f;
        float deltaPitch = this.clamp180(targetPitch - cameraRot.field_1343);
        float deltaYaw = this.clamp180(targetYaw - cameraRot.field_1342);
        return new class_241(deltaPitch, deltaYaw);
    }

    private class_241 clamp180(class_241 angles) {
        return new class_241(this.clamp180(angles.field_1343), this.clamp180(angles.field_1342));
    }

    private float clamp180(float angle) {
        while (angle <= -180.0f) {
            angle += 360.0f;
        }
        while (angle > 180.0f) {
            angle -= 360.0f;
        }
        return angle;
    }
}

