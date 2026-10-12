/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1109
 *  net.minecraft.class_1113
 *  net.minecraft.class_332
 *  net.minecraft.class_3417
 *  net.minecraft.class_4597
 *  net.minecraft.class_6880
 */
package me.pepperbell.continuity.client.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import me.pepperbell.continuity.client.ContinuityClient;
import me.pepperbell.continuity.client.config.ContinuityConfigScreen;
import me.pepperbell.continuity.client.model.CtmBindButton;
import me.pepperbell.continuity.client.model.CtmCheckBox;
import me.pepperbell.continuity.client.model.CtmComponent;
import me.pepperbell.continuity.client.model.CtmFrame;
import me.pepperbell.continuity.client.model.CtmSlider;
import me.pepperbell.continuity.client.properties.BaseCtmProperties;
import me.pepperbell.continuity.client.properties.OrientedConnectingCtmProperties;
import me.pepperbell.continuity.client.properties.PropertiesParsingHelper;
import me.pepperbell.continuity.client.properties.RandomCtmProperties;
import me.pepperbell.continuity.client.util.CtmGuiText;
import net.minecraft.class_1109;
import net.minecraft.class_1113;
import net.minecraft.class_332;
import net.minecraft.class_3417;
import net.minecraft.class_4597;
import net.minecraft.class_6880;

public class CtmModuleButton {
    public RandomCtmProperties category;
    public PropertiesParsingHelper module;
    public CtmFrame parent;
    public int offset;
    public List<CtmComponent> components;
    public boolean extended;

    public CtmModuleButton(PropertiesParsingHelper module, CtmFrame parent, int offset) {
        this.module = module;
        this.parent = parent;
        this.offset = offset;
        this.extended = false;
        this.components = new ArrayList<CtmComponent>();
        int setOffset = parent.height;
        for (Object settingObj : module.getSettings()) {
            if (settingObj instanceof OrientedConnectingCtmProperties) {
                this.components.add(new CtmSlider(settingObj, this, setOffset));
            } else if (settingObj instanceof BaseCtmProperties) {
                this.components.add(new CtmCheckBox(settingObj, this, setOffset));
            }
            setOffset += parent.height;
        }
        this.components.add(new CtmBindButton(this, setOffset));
    }

    public void render(class_332 matrices, int mouseX, int mouseY, float delta) {
        int nameColor;
        int py = this.parent.y + this.offset;
        int ph = this.parent.height;
        matrices.method_25294(this.parent.x, py, this.parent.x + this.parent.width, py + ph, new Color(28, 34, 42, 152).getRGB());
        if (this.isHovered(mouseX, mouseY)) {
            matrices.method_25294(this.parent.x, py, this.parent.x + this.parent.width, py + ph, new Color(52, 60, 74, 118).getRGB());
        }
        matrices.method_25294(this.parent.x, py + ph - 1, this.parent.x + this.parent.width, py + ph, new Color(14, 18, 24, 200).getRGB());
        if (ContinuityConfigScreen.rainbowNames.getValue()) {
            long time = System.currentTimeMillis();
            float hue = (float)((time / 10L + (long)this.offset * 10L) % 360L) / 360.0f;
            rgb = Color.HSBtoRGB(hue, 0.8f, 1.0f);
            nameColor = rgb & 0xFFFFFF;
        } else {
            int r = ContinuityConfigScreen.red.getValueInt();
            int g = ContinuityConfigScreen.green.getValueInt();
            int b = ContinuityConfigScreen.blue.getValueInt();
            rgb = (r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF;
            nameColor = this.module.isEnabled() ? rgb : 0xE0E0E0;
        }
        CtmGuiText.draw(ContinuityClient.mc.field_1772, this.module.getNameSequence(), (float)this.parent.x + 2.0f, (float)this.parent.y + (float)this.offset + 2.0f, nameColor, false, matrices.method_51448().method_23760().method_23761(), (class_4597)ContinuityClient.mc.method_22940().method_23000());
        if (this.extended) {
            for (CtmComponent component : this.components) {
                component.render(matrices.method_51448(), mouseX, mouseY, delta);
            }
        }
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (this.isHovered(mouseX, mouseY)) {
            if (button == 0 && this.parent.extended) {
                this.module.toggle();
                ContinuityClient.mc.method_1483().method_4873((class_1113)class_1109.method_47978((class_6880)class_3417.field_15015, (float)1.0f));
            } else if (button == 1 && this.parent.extended) {
                this.extended = !this.extended;
                this.parent.updateButtons();
                ContinuityClient.mc.method_1483().method_4873((class_1113)class_1109.method_47978((class_6880)class_3417.field_15015, (float)1.0f));
            }
        }
        if (this.extended) {
            for (CtmComponent component : this.components) {
                component.mouseClicked(mouseX, mouseY, button);
            }
        }
    }

    public void mouseReleased(double mouseX, double mouseY, int button) {
        if (this.extended) {
            for (CtmComponent component : this.components) {
                component.mouseReleased(mouseX, mouseY, button);
            }
        }
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.extended) {
            for (CtmComponent component : this.components) {
                if (!component.keyPressed(keyCode, scanCode, modifiers)) continue;
                return true;
            }
        }
        return false;
    }

    public boolean isHovered(double mouseX, double mouseY) {
        return mouseX > (double)this.parent.x && mouseX < (double)(this.parent.x + this.parent.width) && mouseY > (double)(this.parent.y + this.offset) && mouseY < (double)(this.parent.y + this.offset + this.parent.height);
    }
}

