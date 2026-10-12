/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_310
 *  net.minecraft.class_3675
 */
package me.pepperbell.continuity.client.processor.simple;

import me.pepperbell.continuity.client.ContinuityClient;
import me.pepperbell.continuity.client.processor.simple.Event;
import me.pepperbell.continuity.client.properties.PropertiesParsingHelper;
import me.pepperbell.continuity.impl.client.RepeatCtmPropertiesImpl;
import net.minecraft.class_310;
import net.minecraft.class_3675;

public class PreCtm
extends Event {
    public static final int KEY_NONE = -1;
    private final class_310 instance;

    public PreCtm(class_310 instance) {
        this.instance = instance;
    }

    @Override
    public void dispatch() {
        if (this.instance == null || ContinuityClient.INSTANCE == null) {
            return;
        }
        RepeatCtmPropertiesImpl moduleManager = ContinuityClient.INSTANCE.getModuleManager();
        if (moduleManager == null) {
            return;
        }
        for (PropertiesParsingHelper module : moduleManager.getModules()) {
            if (!module.isEnabled()) continue;
            module.onPreCtm(this);
        }
        if (this.instance.field_1755 != null) {
            return;
        }
        long window = this.instance.method_22683().method_4490();
        for (PropertiesParsingHelper module : moduleManager.getModules()) {
            if (module.getKey() == -1) continue;
            boolean isPressed = class_3675.method_15987((long)window, (int)module.getKey());
            if (module.canToggle() && isPressed) {
                module.toggle();
                module.setCanToggle(false);
                continue;
            }
            if (module.canToggle() || isPressed) continue;
            module.setCanToggle(true);
        }
    }

    public class_310 getInstance() {
        return this.instance;
    }
}

