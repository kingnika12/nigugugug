/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.ModInitializer
 */
package me.pepperbell.continuity.api.client;

import me.pepperbell.continuity.client.ContinuityClient;
import net.fabricmc.api.ModInitializer;

public final class ContinuityFeatureStates
implements ModInitializer {
    public void onInitialize() {
        new ContinuityClient();
    }
}

