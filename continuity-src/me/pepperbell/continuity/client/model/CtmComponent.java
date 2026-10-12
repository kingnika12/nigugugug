/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_4587
 */
package me.pepperbell.continuity.client.model;

import me.pepperbell.continuity.client.model.CtmModuleButton;
import me.pepperbell.continuity.client.properties.BaseCtmProperties;
import me.pepperbell.continuity.client.properties.OrientedConnectingCtmProperties;
import me.pepperbell.continuity.client.util.CtmLabels;
import net.minecraft.class_4587;

public class CtmComponent {
    public Object setting;
    public CtmModuleButton parent;
    public int offset;

    public CtmComponent(Object setting, CtmModuleButton parent, int offset) {
        this.parent = parent;
        this.setting = setting;
        this.offset = offset;
    }

    public CharSequence getSettingNameSequence() {
        if (this.setting instanceof BaseCtmProperties) {
            return ((BaseCtmProperties)this.setting).getNameSequence();
        }
        if (this.setting instanceof OrientedConnectingCtmProperties) {
            return ((OrientedConnectingCtmProperties)this.setting).getNameSequence();
        }
        return CtmLabels.lb05;
    }

    public String getSettingName() {
        return this.getSettingNameSequence().toString();
    }

    public void render(class_4587 matrices, int mouseX, int mouseY, float delta) {
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
    }

    public void mouseReleased(double mouseX, double mouseY, int button) {
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return false;
    }

    public boolean isHovered(double mouseX, double mouseY) {
        return mouseX > (double)this.parent.parent.x && mouseX < (double)(this.parent.parent.x + this.parent.parent.width) && mouseY > (double)(this.parent.parent.y + this.parent.offset + this.offset) && mouseY < (double)(this.parent.parent.y + this.parent.offset + this.parent.parent.height + this.offset);
    }
}

