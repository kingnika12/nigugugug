/*
 * Decompiled with CFR 0.152.
 */
package me.pepperbell.continuity.api.client;

import java.util.Arrays;
import java.util.List;

public class CtmProperties {
    private String name;
    public int index;
    private final String mode;
    private final List<String> modes;

    public CtmProperties(String name, String defaultMode, String ... modes) {
        this.name = name;
        this.mode = defaultMode;
        this.modes = Arrays.asList(modes);
        this.index = this.modes.indexOf(defaultMode);
    }

    public String getName() {
        return this.name;
    }

    public String getMode() {
        return this.modes.get(this.index);
    }

    public void setMode(String mode) {
        this.index = this.modes.indexOf(mode);
    }

    public void cycle() {
        this.index = this.index < this.modes.size() - 1 ? ++this.index : 0;
    }

    public boolean isMode(String mode) {
        return this.index == this.modes.indexOf(mode);
    }

    public List<String> getModes() {
        return this.modes;
    }

    public boolean isVisible() {
        return true;
    }
}

