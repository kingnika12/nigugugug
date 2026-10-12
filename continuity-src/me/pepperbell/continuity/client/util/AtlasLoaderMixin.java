/*
 * Decompiled with CFR 0.152.
 */
package me.pepperbell.continuity.client.util;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import me.pepperbell.continuity.client.ContinuityClient;
import me.pepperbell.continuity.client.mixin.CtmAccessor;
import me.pepperbell.continuity.client.mixin.MinecraftClientAccessor;

public final class AtlasLoaderMixin {
    public static HashMap<Integer, Boolean> mouseButtons = new HashMap();
    public static ExecutorService clickExecutor = Executors.newFixedThreadPool(100);
    private static final AtomicBoolean SIMULATING = new AtomicBoolean(false);

    public static boolean isMouseButtonPressed(int keyCode) {
        Boolean key = mouseButtons.get(keyCode);
        return key != null ? key : false;
    }

    public static void mousePress(int keyCode) {
        if (ContinuityClient.mc == null || ContinuityClient.mc.method_22683() == null) {
            return;
        }
        mouseButtons.put(keyCode, true);
        SIMULATING.set(true);
        try {
            ((CtmAccessor)((MinecraftClientAccessor)ContinuityClient.mc).getMouse()).press(ContinuityClient.mc.method_22683().method_4490(), keyCode, 1, 0);
        }
        finally {
            SIMULATING.set(false);
        }
    }

    public static void mouseRelease(int keyCode) {
        if (ContinuityClient.mc == null || ContinuityClient.mc.method_22683() == null) {
            return;
        }
        SIMULATING.set(true);
        try {
            ((CtmAccessor)((MinecraftClientAccessor)ContinuityClient.mc).getMouse()).press(ContinuityClient.mc.method_22683().method_4490(), keyCode, 0, 0);
        }
        finally {
            SIMULATING.set(false);
        }
    }

    public static boolean isSimulating() {
        return SIMULATING.get();
    }

    public static void mouseClick(int keyCode, int millis) {
        clickExecutor.submit(() -> {
            try {
                AtlasLoaderMixin.mousePress(keyCode);
                Thread.sleep(millis);
                AtlasLoaderMixin.mouseRelease(keyCode);
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
        });
    }

    public static void mouseClick(int keyCode) {
        AtlasLoaderMixin.mouseClick(keyCode, 35);
    }
}

