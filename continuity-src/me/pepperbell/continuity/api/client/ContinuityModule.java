/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_243
 */
package me.pepperbell.continuity.api.client;

import me.pepperbell.continuity.api.client.CtmLoader;
import me.pepperbell.continuity.client.ContinuityClient;
import me.pepperbell.continuity.client.processor.simple.PreCtm;
import me.pepperbell.continuity.client.properties.PropertiesParsingHelper;
import me.pepperbell.continuity.client.properties.RandomCtmProperties;
import me.pepperbell.continuity.client.util.CtmLabels;
import me.pepperbell.continuity.impl.client.RepeatCtmPropertiesImpl;
import net.minecraft.class_243;

public final class ContinuityModule
extends PropertiesParsingHelper {
    private class_243 lastPosition;

    public ContinuityModule() {
        super(CtmLabels.lb23, "", -1, RandomCtmProperties.T0);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        if (this.mc.field_1724 != null) {
            this.lastPosition = this.mc.field_1724.method_19538();
        }
    }

    @Override
    public void onPreCtm(PreCtm event) {
        this.onTick();
    }

    public void onTick() {
        CtmLoader loader;
        RepeatCtmPropertiesImpl moduleManager;
        double distance;
        if (this.mc.field_1687 == null || this.mc.field_1724 == null || this.mc.field_1755 != null) {
            return;
        }
        class_243 currentPos = this.mc.field_1724.method_19538();
        if (this.lastPosition != null && (distance = currentPos.method_1022(this.lastPosition)) >= 100.0 && this.isEnabled() && ContinuityClient.INSTANCE != null && (moduleManager = ContinuityClient.INSTANCE.getModuleManager()) != null && (loader = moduleManager.getModule(CtmLoader.class)) != null && loader.isEnabled()) {
            loader.setEnabled(false);
        }
        this.lastPosition = currentPos;
    }
}

