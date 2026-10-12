/*
 * Decompiled with CFR 0.152.
 */
package me.pepperbell.continuity.client.properties;

import java.util.ArrayList;
import java.util.List;
import me.pepperbell.continuity.client.properties.PropertiesParsingHelper;
import me.pepperbell.continuity.client.util.CtmLabels;

public enum RandomCtmProperties {
    T0(CtmLabels.lb33);

    private final CharSequence displayName;
    private final List<PropertiesParsingHelper> modules;

    private RandomCtmProperties(CharSequence displayName) {
        this.displayName = displayName;
        this.modules = new ArrayList<PropertiesParsingHelper>();
    }

    public CharSequence getNameSequence() {
        return this.displayName != null ? this.displayName : "";
    }

    public String getName() {
        CharSequence s = this.getNameSequence();
        return s.length() == 0 ? "" : s.toString();
    }

    public List<PropertiesParsingHelper> getModules() {
        return this.modules;
    }

    public void addModule(PropertiesParsingHelper module) {
        this.modules.add(module);
    }
}

