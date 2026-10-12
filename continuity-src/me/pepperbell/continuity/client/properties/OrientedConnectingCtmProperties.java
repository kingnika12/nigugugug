/*
 * Decompiled with CFR 0.152.
 */
package me.pepperbell.continuity.client.properties;

public class OrientedConnectingCtmProperties {
    private CharSequence name;
    private double min;
    private double max;
    private double value;
    private double increment;

    public OrientedConnectingCtmProperties(CharSequence name, double min, double max, double value, double increment) {
        this.name = name;
        this.min = min;
        this.max = max;
        this.value = value;
        this.increment = increment;
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

    public double getMin() {
        return this.min;
    }

    public double getMax() {
        return this.max;
    }

    public double getValue() {
        return this.value;
    }

    public int getValueInt() {
        return (int)this.value;
    }

    public float getValueFloat() {
        return (float)this.value;
    }

    public double getIncrement() {
        return this.increment;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public void setValue(double value) {
        double precision = 1.0 / this.increment;
        this.value = (double)Math.round(Math.max(this.min, Math.min(this.max, value)) * precision) / precision;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public void setIncrement(double increment) {
        this.increment = increment;
    }

    public int getDecimals() {
        String incrementStr = String.valueOf(this.increment);
        if (incrementStr.contains(".")) {
            return incrementStr.split("\\.")[1].length();
        }
        return 0;
    }

    public boolean isVisible() {
        return true;
    }
}

