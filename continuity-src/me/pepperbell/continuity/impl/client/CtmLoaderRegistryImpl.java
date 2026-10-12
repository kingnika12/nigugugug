/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
 *  org.jetbrains.annotations.Nullable
 */
package me.pepperbell.continuity.impl.client;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.Map;
import org.jetbrains.annotations.Nullable;

public final class CtmLoaderRegistryImpl {
    public static final CtmLoaderRegistryImpl INSTANCE = new CtmLoaderRegistryImpl();
    private final Map<String, Object> loaderMap = new Object2ObjectOpenHashMap();

    public void registerLoader(String method, Object loader) {
        this.loaderMap.put(method, loader);
    }

    @Nullable
    public Object getLoader(String method) {
        return this.loaderMap.get(method);
    }
}

