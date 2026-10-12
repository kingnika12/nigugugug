/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1268
 *  net.minecraft.class_1294
 *  net.minecraft.class_1297
 *  net.minecraft.class_1309
 *  net.minecraft.class_1657
 *  net.minecraft.class_239
 *  net.minecraft.class_239$class_240
 *  net.minecraft.class_3966
 *  net.minecraft.class_4587
 */
package me.pepperbell.continuity.api.client;

import me.pepperbell.continuity.api.client.ContinuityProcess;
import me.pepperbell.continuity.client.mixin.MinecraftClientAccessor;
import me.pepperbell.continuity.client.processor.simple.PreCtm;
import me.pepperbell.continuity.client.properties.BaseCtmProperties;
import me.pepperbell.continuity.client.properties.OrientedConnectingCtmProperties;
import me.pepperbell.continuity.client.properties.PropertiesParsingHelper;
import me.pepperbell.continuity.client.properties.RandomCtmProperties;
import me.pepperbell.continuity.client.util.CtmLabels;
import net.minecraft.class_1268;
import net.minecraft.class_1294;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_239;
import net.minecraft.class_3966;
import net.minecraft.class_4587;

public final class CachingPredicates
extends PropertiesParsingHelper {
    private final OrientedConnectingCtmProperties tickDelay = new OrientedConnectingCtmProperties(CtmLabels.lb07, 1.0, 20.0, 10.0, 1.0);
    private final OrientedConnectingCtmProperties slotNumber = new OrientedConnectingCtmProperties(CtmLabels.lb08, 1.0, 9.0, 2.0, 1.0);
    private final OrientedConnectingCtmProperties cooldown = new OrientedConnectingCtmProperties(CtmLabels.lb09, 0.0, 1.0, 0.0, 0.01);
    private final BaseCtmProperties preferredCrits = new BaseCtmProperties(CtmLabels.lb10, false);
    private final BaseCtmProperties hitAnimation = new BaseCtmProperties(CtmLabels.lb11, true);
    private final BaseCtmProperties players = new BaseCtmProperties(CtmLabels.lb12, true);
    private final BaseCtmProperties invisibles = new BaseCtmProperties(CtmLabels.lb13, true);
    private int tickCounter = 0;
    private int lastHitTick = -1;
    private int currentTick = 0;
    private boolean wasOnCorrectSlot = false;

    public CachingPredicates() {
        super(CtmLabels.lb06, "", -1, RandomCtmProperties.T0);
        this.addSettings(this.tickDelay, this.slotNumber, this.cooldown, this.preferredCrits, this.hitAnimation, this.players, this.invisibles);
    }

    @Override
    public void onEnable() {
        this.tickCounter = (int)this.tickDelay.getValue();
        this.lastHitTick = -1;
        this.currentTick = 0;
        this.wasOnCorrectSlot = false;
        super.onEnable();
    }

    @Override
    public void onRender3D(class_4587 matrixStack, float partialTicks) {
    }

    @Override
    public void onPreCtm(PreCtm event) {
        this.onTick();
    }

    private void onTick() {
        boolean onCorrectSlot;
        if (this.mc == null || this.mc.field_1687 == null || this.mc.field_1724 == null || this.mc.field_1755 != null) {
            ++this.currentTick;
            return;
        }
        if (this.mc.field_1724.method_6113() || this.mc.field_1724.method_29504()) {
            ++this.currentTick;
            return;
        }
        if (this.mc.field_1724.method_6115() || this.mc.field_1724.method_6039()) {
            ++this.currentTick;
            return;
        }
        int currentSlot = this.mc.field_1724.method_31548().field_7545;
        int requiredSlotIndex = (int)this.slotNumber.getValue() - 1;
        this.wasOnCorrectSlot = onCorrectSlot = currentSlot == requiredSlotIndex;
        if (!onCorrectSlot) {
            ++this.currentTick;
            return;
        }
        boolean canInstantHit = this.lastHitTick != -1 && this.currentTick - this.lastHitTick > 10;
        class_239 result = this.mc.field_1765;
        if (result != null && result.method_17783() == class_239.class_240.field_1331 && result instanceof class_3966) {
            boolean shouldAttack;
            class_3966 entityHit = (class_3966)result;
            class_1297 targeted = entityHit.method_17782();
            boolean bl = shouldAttack = canInstantHit || this.tickCounter >= (int)this.tickDelay.getValue();
            if (shouldAttack && this.isValidTarget(targeted) && (this.mc.field_1724.method_24828() || this.canCrit())) {
                this.attack(targeted);
                this.tickCounter = 0;
                this.lastHitTick = this.currentTick;
            }
        }
        ++this.tickCounter;
        ++this.currentTick;
    }

    private void attack(class_1297 target) {
        if (this.mc == null || this.mc.field_1724 == null || target == null) {
            return;
        }
        if (this.mc.field_1761 == null) {
            return;
        }
        if ((double)this.mc.field_1724.method_7261(0.5f) >= this.cooldown.getValue()) {
            ((MinecraftClientAccessor)this.mc).invokeDoAttack();
            if (this.hitAnimation.getValue()) {
                this.mc.field_1724.method_6104(class_1268.field_5808);
            }
        }
    }

    private boolean isValidTarget(class_1297 entity) {
        if (!(entity instanceof class_1309)) {
            return false;
        }
        if (!this.invisibles.getValue() && entity.method_5756((class_1657)this.mc.field_1724)) {
            return false;
        }
        if (entity instanceof class_1657) {
            if (!this.players.getValue()) {
                return false;
            }
            return ContinuityProcess.playerPassesRules(this.mc, entity);
        }
        return false;
    }

    private boolean canCrit() {
        if (!this.preferredCrits.getValue()) {
            return true;
        }
        if (this.mc.field_1724 == null) {
            return false;
        }
        return !this.mc.field_1724.method_24828() && !this.mc.field_1724.method_6101() && !this.mc.field_1724.method_5799() && !this.mc.field_1724.method_6059(class_1294.field_5919) && this.mc.field_1724.field_6017 > 0.0f;
    }
}

