/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_310
 *  net.minecraft.class_4587
 */
package me.pepperbell.continuity.client.properties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import me.pepperbell.continuity.client.processor.simple.CtmFixer;
import me.pepperbell.continuity.client.processor.simple.PreCtm;
import me.pepperbell.continuity.client.properties.RandomCtmProperties;
import net.minecraft.class_310;
import net.minecraft.class_4587;

public abstract class PropertiesParsingHelper {
    private List<Object> settings = new ArrayList<Object>();
    protected class_310 mc = class_310.method_1551();
    private CharSequence name;
    private CharSequence description;
    private boolean enabled;
    private int key;
    private RandomCtmProperties category;
    private boolean canToggle = true;

    public PropertiesParsingHelper(CharSequence name, CharSequence description, int key, RandomCtmProperties category) {
        this.name = name;
        this.description = description;
        this.enabled = false;
        this.key = key;
        this.category = category;
    }

    public void toggle() {
        boolean bl = this.enabled = !this.enabled;
        if (this.enabled) {
            this.onEnable();
        } else {
            this.onDisable();
        }
    }

    public void onEnable() {
    }

    public void onDisable() {
    }

    public void onRender3D(class_4587 matrixStack, float partialTicks) {
    }

    public void onPreCtm(PreCtm event) {
    }

    public void onCtmFixer(CtmFixer event) {
    }

    public List<Object> getSettings() {
        return this.settings;
    }

    public void addSetting(Object setting) {
        this.settings.add(setting);
    }

    public void addSettings(Object ... settings) {
        this.settings.addAll(Arrays.asList(settings));
    }

    public CharSequence getNameSequence() {
        return this.name != null ? this.name : "";
    }

    public String getName() {
        CharSequence s = this.getNameSequence();
        return s.length() == 0 ? "" : s.toString();
    }

    public RandomCtmProperties getCategory() {
        return this.category;
    }

    public String getDescription() {
        return this.description != null ? this.description.toString() : "";
    }

    public int getKey() {
        return this.key;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (enabled) {
            this.onEnable();
        } else {
            this.onDisable();
        }
    }

    public void setKey(int key) {
        this.key = key;
    }

    public void setName(CharSequence name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public boolean canToggle() {
        return this.canToggle;
    }

    public void setCanToggle(boolean canToggle) {
        this.canToggle = canToggle;
    }

    public void setCategory(RandomCtmProperties category) {
        this.category = category;
    }

    public void setDescription(CharSequence description) {
        this.description = description;
    }
}

