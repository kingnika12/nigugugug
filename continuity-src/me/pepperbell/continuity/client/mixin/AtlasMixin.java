/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2561
 *  net.minecraft.class_437
 */
package me.pepperbell.continuity.client.mixin;

import java.lang.reflect.Field;
import java.util.List;
import me.pepperbell.continuity.client.ContinuityClient;
import me.pepperbell.continuity.client.model.CtmScreen;
import me.pepperbell.continuity.client.properties.BaseCtmProperties;
import me.pepperbell.continuity.client.properties.OrientedConnectingCtmProperties;
import me.pepperbell.continuity.client.properties.PropertiesParsingHelper;
import me.pepperbell.continuity.client.properties.RandomCtmProperties;
import me.pepperbell.continuity.impl.client.RepeatCtmPropertiesImpl;
import net.minecraft.class_2561;
import net.minecraft.class_437;

public final class AtlasMixin {
    private static final String WIPED = "";

    public static void runCleanup() {
        AtlasMixin.uninjectFromUI();
        AtlasMixin.wipeModuleAndSettingStrings();
        AtlasMixin.wipeCategoryNames();
        AtlasMixin.wipeScreenTitle();
    }

    public static void uninjectFromUI() {
        if (ContinuityClient.mc == null) {
            return;
        }
        if (ContinuityClient.mc.field_1755 instanceof CtmScreen) {
            ContinuityClient.mc.method_1507(null);
        }
    }

    public static void wipeModuleAndSettingStrings() {
        if (ContinuityClient.INSTANCE == null) {
            return;
        }
        RepeatCtmPropertiesImpl manager = ContinuityClient.INSTANCE.getModuleManager();
        if (manager == null) {
            return;
        }
        List<PropertiesParsingHelper> modules = manager.getModules();
        if (modules == null) {
            return;
        }
        for (PropertiesParsingHelper module : modules) {
            module.setName(WIPED);
            module.setDescription(WIPED);
            List<Object> settings = module.getSettings();
            if (settings == null) continue;
            for (Object setting : settings) {
                if (setting instanceof BaseCtmProperties) {
                    ((BaseCtmProperties)setting).setName(WIPED);
                    continue;
                }
                if (!(setting instanceof OrientedConnectingCtmProperties)) continue;
                ((OrientedConnectingCtmProperties)setting).setName(WIPED);
            }
            settings.clear();
        }
    }

    public static void wipeCategoryNames() {
        try {
            Field field = RandomCtmProperties.class.getDeclaredField("displayName");
            field.setAccessible(true);
            for (RandomCtmProperties category : RandomCtmProperties.values()) {
                field.set((Object)category, WIPED);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void wipeScreenTitle() {
        try {
            Field titleField = class_437.class.getDeclaredField("title");
            titleField.setAccessible(true);
            titleField.set((Object)CtmScreen.INSTANCE, class_2561.method_43470((String)WIPED));
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

