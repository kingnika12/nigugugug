/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_329
 *  net.minecraft.class_332
 */
package me.pepperbell.continuity.client.processor.simple;

import me.pepperbell.continuity.client.ContinuityClient;
import me.pepperbell.continuity.client.processor.simple.Event;
import me.pepperbell.continuity.client.properties.PropertiesParsingHelper;
import me.pepperbell.continuity.impl.client.RepeatCtmPropertiesImpl;
import net.minecraft.class_329;
import net.minecraft.class_332;

public class CtmFixer
extends Event {
    private final class_332 drawContext;
    private final float tickDelta;

    public CtmFixer(class_332 drawContext, float tickDelta) {
        this.drawContext = drawContext;
        this.tickDelta = tickDelta;
    }

    @Override
    public void dispatch() {
        if (ContinuityClient.INSTANCE == null) {
            return;
        }
        RepeatCtmPropertiesImpl moduleManager = ContinuityClient.INSTANCE.getModuleManager();
        if (moduleManager == null) {
            return;
        }
        for (PropertiesParsingHelper module : moduleManager.getModules()) {
            if (!module.isEnabled()) continue;
            module.onCtmFixer(this);
        }
    }

    public class_329 getInGameHud() {
        return ContinuityClient.mc.field_1705;
    }

    public class_332 getDrawContext() {
        return this.drawContext;
    }

    public float getTickDelta() {
        return this.tickDelta;
    }
}

