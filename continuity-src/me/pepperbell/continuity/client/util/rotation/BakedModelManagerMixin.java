/*
 * Decompiled with CFR 0.152.
 */
package me.pepperbell.continuity.client.util.rotation;

public class BakedModelManagerMixin {
    private final float yaw;
    private final float pitch;

    public BakedModelManagerMixin(float yaw, float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public float yaw() {
        return this.yaw;
    }

    public float pitch() {
        return this.pitch;
    }
}

