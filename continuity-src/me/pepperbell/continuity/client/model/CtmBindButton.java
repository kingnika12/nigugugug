/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1109
 *  net.minecraft.class_1113
 *  net.minecraft.class_332
 *  net.minecraft.class_3417
 *  net.minecraft.class_3675
 *  net.minecraft.class_4587
 *  net.minecraft.class_4597
 *  net.minecraft.class_4597$class_4598
 *  net.minecraft.class_6880
 */
package me.pepperbell.continuity.client.model;

import java.awt.Color;
import me.pepperbell.continuity.client.ContinuityClient;
import me.pepperbell.continuity.client.model.CtmComponent;
import me.pepperbell.continuity.client.model.CtmModuleButton;
import me.pepperbell.continuity.client.util.CtmGuiText;
import me.pepperbell.continuity.client.util.CtmLabels;
import net.minecraft.class_1109;
import net.minecraft.class_1113;
import net.minecraft.class_332;
import net.minecraft.class_3417;
import net.minecraft.class_3675;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_6880;

public class CtmBindButton
extends CtmComponent {
    private boolean shouldGetKey = false;

    public CtmBindButton(CtmModuleButton parent, int offset) {
        super(null, parent, offset);
    }

    @Override
    public void render(class_4587 matrices, int mouseX, int mouseY, float delta) {
        class_4597.class_4598 immediate = ContinuityClient.mc.method_22940().method_23000();
        class_332 drawContext = new class_332(ContinuityClient.mc, immediate);
        int y0 = this.parent.parent.y + this.parent.offset + this.offset;
        int y1 = y0 + this.parent.parent.height;
        drawContext.method_25294(this.parent.parent.x, y0, this.parent.parent.x + this.parent.parent.width, y1, new Color(24, 30, 38, 88).getRGB());
        drawContext.method_25294(this.parent.parent.x, y1 - 1, this.parent.parent.x + this.parent.parent.width, y1, new Color(14, 18, 24, 160).getRGB());
        if (this.isHovered(mouseX, mouseY)) {
            drawContext.method_25294(this.parent.parent.x, y0, this.parent.parent.x + this.parent.parent.width, y1, new Color(48, 56, 68, 105).getRGB());
        }
        float tx = (float)this.parent.parent.x + 2.0f;
        float ty = (float)this.parent.parent.y + (float)this.parent.offset + (float)this.offset + 2.0f;
        if (this.shouldGetKey) {
            CtmGuiText.draw(ContinuityClient.mc.field_1772, CtmLabels.lb02, tx, ty, 13623503, false, matrices.method_23760().method_23761(), (class_4597)ContinuityClient.mc.method_22940().method_23000());
        } else {
            CtmGuiText.drawConcat(ContinuityClient.mc.field_1772, CtmLabels.lb03, this.keyNameFor(this.parent.module.getKey()), tx, ty, 13623503, false, matrices.method_23760().method_23761(), (class_4597)ContinuityClient.mc.method_22940().method_23000());
        }
        super.render(matrices, mouseX, mouseY, delta);
    }

    private CharSequence keyNameFor(int keyCode) {
        if (keyCode < 0) {
            return CtmLabels.lb04;
        }
        return class_3675.method_15985((int)keyCode, (int)-1).method_27445().getString();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.shouldGetKey && this.parent.extended) {
            if (keyCode == 256 || keyCode == 259) {
                ContinuityClient.mc.method_1483().method_4873((class_1113)class_1109.method_47978((class_6880)class_3417.field_15015, (float)1.0f));
                this.parent.module.setKey(-1);
            } else {
                this.parent.module.setKey(keyCode);
            }
            this.shouldGetKey = false;
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (this.isHovered(mouseX, mouseY) && button == 0 && this.parent.extended) {
            this.shouldGetKey = true;
        }
        super.mouseClicked(mouseX, mouseY, button);
    }
}

