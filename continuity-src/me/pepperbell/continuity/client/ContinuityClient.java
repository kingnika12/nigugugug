/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
 *  net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents$StartTick
 *  net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
 *  net.minecraft.class_310
 *  net.minecraft.class_332
 */
package me.pepperbell.continuity.client;

import me.pepperbell.continuity.client.config.ContinuityConfig;
import me.pepperbell.continuity.client.processor.simple.CtmFixer;
import me.pepperbell.continuity.client.processor.simple.PreCtm;
import me.pepperbell.continuity.impl.client.RepeatCtmPropertiesImpl;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.class_310;
import net.minecraft.class_332;

public final class ContinuityClient
implements ClientTickEvents.StartTick,
HudRenderCallback {
    public static ContinuityClient INSTANCE;
    public static class_310 mc;
    private final RepeatCtmPropertiesImpl moduleManager;

    public ContinuityClient() {
        INSTANCE = this;
        mc = class_310.method_1551();
        this.moduleManager = new RepeatCtmPropertiesImpl();
        ClientTickEvents.START_CLIENT_TICK.register((Object)this);
        HudRenderCallback.EVENT.register((Object)this);
    }

    public void onStartTick(class_310 client) {
        if (ContinuityConfig.destruct) {
            return;
        }
        if (this.moduleManager != null) {
            new PreCtm(client).dispatch();
        }
    }

    public void onHudRender(class_332 drawContext, float tickDelta) {
        if (ContinuityConfig.destruct) {
            return;
        }
        if (this.moduleManager != null) {
            new CtmFixer(drawContext, tickDelta).dispatch();
        }
    }

    public RepeatCtmPropertiesImpl getModuleManager() {
        return this.moduleManager;
    }
}

