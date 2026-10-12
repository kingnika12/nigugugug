/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_310
 *  net.minecraft.class_437
 */
package me.pepperbell.continuity.client.config;

import me.pepperbell.continuity.client.config.ContinuityConfig;
import me.pepperbell.continuity.client.model.CtmScreen;
import me.pepperbell.continuity.client.properties.BaseCtmProperties;
import me.pepperbell.continuity.client.properties.OrientedConnectingCtmProperties;
import me.pepperbell.continuity.client.properties.PropertiesParsingHelper;
import me.pepperbell.continuity.client.properties.RandomCtmProperties;
import me.pepperbell.continuity.client.util.CtmLabels;
import net.minecraft.class_310;
import net.minecraft.class_437;

public class ContinuityConfigScreen
extends PropertiesParsingHelper {
    public static final OrientedConnectingCtmProperties red = new OrientedConnectingCtmProperties(CtmLabels.lb15, 0.0, 255.0, 153.0, 1.0);
    public static final OrientedConnectingCtmProperties green = new OrientedConnectingCtmProperties(CtmLabels.lb16, 0.0, 255.0, 0.0, 1.0);
    public static final OrientedConnectingCtmProperties blue = new OrientedConnectingCtmProperties(CtmLabels.lb17, 0.0, 255.0, 0.0, 1.0);
    public static final BaseCtmProperties rainbowNames = new BaseCtmProperties(CtmLabels.lb18, false);

    public ContinuityConfigScreen() {
        super(CtmLabels.lb14, "", 260, RandomCtmProperties.T0);
        this.addSettings(red, green, blue, rainbowNames);
    }

    @Override
    public void onEnable() {
        if (ContinuityConfig.destruct) {
            this.setEnabled(false);
            return;
        }
        class_310.method_1551().method_1507((class_437)CtmScreen.INSTANCE);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        if (class_310.method_1551().field_1755 instanceof CtmScreen) {
            class_310.method_1551().method_1507(null);
        }
        super.onDisable();
    }
}

