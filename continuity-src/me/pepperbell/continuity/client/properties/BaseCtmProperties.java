/*
 * Decompiled with CFR 0.152.
 */
package me.pepperbell.continuity.client.properties;

public class BaseCtmProperties {
    protected CharSequence name;
    private boolean value;

    public BaseCtmProperties(CharSequence name, boolean value) {
        this.name = name;
        this.value = value;
    }

    public CharSequence getNameSequence() {
        return this.name != null ? this.name : "";
    }

    public String getName() {
        CharSequence s = this.getNameSequence();
        return s.length() == 0 ? "" : s.toString();
    }

    public void setName(CharSequence name) {
        this.name = name;
    }

    public void toggle() {
        this.setValue(!this.value);
    }

    public boolean getValue() {
        return this.value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public boolean isVisible() {
        return true;
    }
}

