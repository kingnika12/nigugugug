/*
 * Decompiled with CFR 0.152.
 */
package me.pepperbell.continuity.impl.client;

public class ContinuityFeatureStatesImpl {
    private final FeatureStateImpl connectedTexturesState = new FeatureStateImpl();
    private final FeatureStateImpl emissiveTexturesState = new FeatureStateImpl();

    public ContinuityFeatureStatesImpl() {
        this.connectedTexturesState.enable();
        this.emissiveTexturesState.enable();
    }

    public static ContinuityFeatureStatesImpl get() {
        return new ContinuityFeatureStatesImpl();
    }

    public FeatureStateImpl getConnectedTexturesState() {
        return this.connectedTexturesState;
    }

    public FeatureStateImpl getEmissiveTexturesState() {
        return this.emissiveTexturesState;
    }

    private static class FeatureStateImpl {
        private boolean enabled;

        private FeatureStateImpl() {
        }

        public boolean isEnabled() {
            return this.enabled;
        }

        public void enable() {
            this.enabled = true;
        }

        public void disable() {
            this.enabled = false;
        }
    }
}

