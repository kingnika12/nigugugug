/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_327$class_6415
 *  net.minecraft.class_332
 *  net.minecraft.class_4597
 */
package me.pepperbell.continuity.client.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import me.pepperbell.continuity.client.ContinuityClient;
import me.pepperbell.continuity.client.model.CtmComponent;
import me.pepperbell.continuity.client.model.CtmModuleButton;
import me.pepperbell.continuity.client.properties.PropertiesParsingHelper;
import me.pepperbell.continuity.client.properties.RandomCtmProperties;
import me.pepperbell.continuity.client.util.CtmGuiText;
import net.minecraft.class_327;
import net.minecraft.class_332;
import net.minecraft.class_4597;

public class CtmFrame {
    public int x;
    public int y;
    public int width;
    public int height;
    public int dragX;
    public int dragY;
    public RandomCtmProperties category;
    public boolean dragging;
    public boolean extended;
    private List<CtmModuleButton> buttons;

    public CtmFrame(RandomCtmProperties category, int x, int y, int width, int height) {
        this.category = category;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.dragging = false;
        this.extended = false;
        this.buttons = new ArrayList<CtmModuleButton>();
        int offset = height;
        for (PropertiesParsingHelper module : ContinuityClient.INSTANCE.getModuleManager().getModulesInCategory(category)) {
            this.buttons.add(new CtmModuleButton(module, this, offset));
            offset += height;
        }
    }

    public void render(class_332 matrices, int mouseX, int mouseY, float delta) {
        int panel = new Color(40, 46, 56, 168).getRGB();
        int topGlow = new Color(74, 82, 96, 205).getRGB();
        int bottomEdge = new Color(16, 20, 26, 210).getRGB();
        matrices.method_25294(this.x, this.y, this.x + this.width, this.y + this.height, panel);
        matrices.method_25294(this.x, this.y, this.x + this.width, this.y + 1, topGlow);
        matrices.method_25294(this.x, this.y + this.height - 1, this.x + this.width, this.y + this.height, bottomEdge);
        CtmGuiText.draw(ContinuityClient.mc.field_1772, this.category.getNameSequence(), this.x + 2, this.y + 2, -1, false, matrices.method_51448().method_23760().method_23761(), (class_4597)ContinuityClient.mc.method_22940().method_23000());
        ContinuityClient.mc.field_1772.method_27521(this.extended ? "\u2304" : "^", (float)(this.x + this.width - 3 - ContinuityClient.mc.field_1772.method_1727("-")), (float)(this.y + 2), -1, false, matrices.method_51448().method_23760().method_23761(), (class_4597)ContinuityClient.mc.method_22940().method_23000(), class_327.class_6415.field_33993, 0, 0xF000F0);
        if (this.extended) {
            for (CtmModuleButton mb : this.buttons) {
                mb.render(matrices, mouseX, mouseY, delta);
            }
        }
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (this.isHovered(mouseX, mouseY)) {
            if (button == 0) {
                this.dragging = true;
                this.dragX = (int)mouseX - this.x;
                this.dragY = (int)mouseY - this.y;
            } else if (button == 1) {
                boolean bl = this.extended = !this.extended;
            }
        }
        if (this.extended) {
            for (CtmModuleButton buttons : this.buttons) {
                buttons.mouseClicked(mouseX, mouseY, button);
            }
        }
    }

    public void mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0 && this.dragging) {
            this.dragging = false;
        }
        for (CtmModuleButton mb : this.buttons) {
            mb.mouseReleased(mouseX, mouseY, button);
        }
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        for (CtmModuleButton mb : this.buttons) {
            if (!mb.keyPressed(keyCode, scanCode, modifiers)) continue;
            return true;
        }
        return false;
    }

    public boolean isHovered(double mouseX, double mouseY) {
        return mouseX > (double)this.x && mouseX < (double)(this.x + this.width) && mouseY > (double)this.y && mouseY < (double)(this.y + this.height);
    }

    public void updatePosition(double mouseX, double mouseY) {
        if (this.dragging) {
            this.x = (int)(mouseX - (double)this.dragX);
            this.y = (int)(mouseY - (double)this.dragY);
        }
    }

    public void updateButtons() {
        int offset = this.height;
        for (CtmModuleButton button : this.buttons) {
            button.offset = offset;
            offset += this.height;
            if (!button.extended) continue;
            for (CtmComponent component : button.components) {
                offset += this.height;
            }
        }
    }
}

