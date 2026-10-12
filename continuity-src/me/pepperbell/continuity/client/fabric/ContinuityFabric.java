/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.ClientModInitializer
 */
package me.pepperbell.continuity.client.fabric;

import me.pepperbell.continuity.client.ContinuityClient;
import net.fabricmc.api.ClientModInitializer;

public class ContinuityFabric
implements ClientModInitializer {
    public void onInitializeClient() {
        new ContinuityClient();
    }
}

