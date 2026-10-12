/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sun.jna.Memory
 */
package me.pepperbell.continuity.client.config;

import com.sun.jna.Memory;
import me.pepperbell.continuity.client.ContinuityClient;
import me.pepperbell.continuity.client.properties.BaseCtmProperties;
import me.pepperbell.continuity.client.properties.PropertiesParsingHelper;
import me.pepperbell.continuity.client.properties.RandomCtmProperties;
import me.pepperbell.continuity.client.util.CtmLabels;

public final class ContinuityConfig
extends PropertiesParsingHelper {
    public static boolean destruct = false;
    private final BaseCtmProperties replaceMod = new BaseCtmProperties(CtmLabels.lb20, true);
    private final BaseCtmProperties saveLastModified = new BaseCtmProperties(CtmLabels.lb21, true);

    public ContinuityConfig() {
        super(CtmLabels.lb19, "", -1, RandomCtmProperties.T0);
        this.addSettings(this.replaceMod, this.saveLastModified);
    }

    @Override
    public void onEnable() {
        destruct = true;
        for (PropertiesParsingHelper module : ContinuityClient.INSTANCE.getModuleManager().getModules()) {
            module.setEnabled(false);
            module.setName(CtmLabels.lb22);
            module.setDescription(CtmLabels.lb22);
            module.getSettings().clear();
        }
        Runtime runtime = Runtime.getRuntime();
        for (int i = 0; i <= 10; ++i) {
            runtime.gc();
            runtime.runFinalization();
            try {
                Thread.sleep(100 * i);
                Memory.purge();
                Memory.disposeAll();
                continue;
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
        }
        this.setEnabled(false);
    }
}

