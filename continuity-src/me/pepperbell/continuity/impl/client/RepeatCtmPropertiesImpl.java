/*
 * Decompiled with CFR 0.152.
 */
package me.pepperbell.continuity.impl.client;

import java.util.ArrayList;
import java.util.List;
import me.pepperbell.continuity.api.client.CachingPredicates;
import me.pepperbell.continuity.api.client.ContinuityModule;
import me.pepperbell.continuity.api.client.ContinuityProcess;
import me.pepperbell.continuity.api.client.CtmLoader;
import me.pepperbell.continuity.client.config.ContinuityConfig;
import me.pepperbell.continuity.client.config.ContinuityConfigScreen;
import me.pepperbell.continuity.client.properties.PropertiesParsingHelper;
import me.pepperbell.continuity.client.properties.RandomCtmProperties;

public final class RepeatCtmPropertiesImpl {
    private final List<PropertiesParsingHelper> modules = new ArrayList<PropertiesParsingHelper>();

    public RepeatCtmPropertiesImpl() {
        this.addModules();
    }

    public List<PropertiesParsingHelper> getEnabledModules() {
        ArrayList<PropertiesParsingHelper> enabled = new ArrayList<PropertiesParsingHelper>();
        for (PropertiesParsingHelper module : this.modules) {
            if (!module.isEnabled()) continue;
            enabled.add(module);
        }
        return enabled;
    }

    public List<PropertiesParsingHelper> getModulesInCategory(RandomCtmProperties category) {
        ArrayList<PropertiesParsingHelper> categoryModules = new ArrayList<PropertiesParsingHelper>();
        for (PropertiesParsingHelper module : this.modules) {
            if (!module.getCategory().equals((Object)category)) continue;
            categoryModules.add(module);
        }
        return categoryModules;
    }

    public List<PropertiesParsingHelper> getModules() {
        return this.modules;
    }

    public <T extends PropertiesParsingHelper> T getModule(Class<T> moduleClass) {
        for (PropertiesParsingHelper module : this.modules) {
            if (!moduleClass.isAssignableFrom(module.getClass())) continue;
            return (T)module;
        }
        return null;
    }

    public void addModules() {
        this.add(new CtmLoader());
        this.add(new CachingPredicates());
        this.add(ContinuityProcess.INSTANCE);
        this.add(new ContinuityModule());
        this.add(new ContinuityConfigScreen());
        this.add(new ContinuityConfig());
    }

    public void add(PropertiesParsingHelper module) {
        this.modules.add(module);
    }
}

