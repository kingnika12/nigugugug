/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1109
 *  net.minecraft.class_1113
 *  net.minecraft.class_332
 *  net.minecraft.class_3417
 *  net.minecraft.class_3532
 *  net.minecraft.class_4587
 *  net.minecraft.class_4597
 *  net.minecraft.class_4597$class_4598
 *  net.minecraft.class_6880
 */
package me.pepperbell.continuity.client.model;

import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;
import me.pepperbell.continuity.client.ContinuityClient;
import me.pepperbell.continuity.client.model.CtmComponent;
import me.pepperbell.continuity.client.model.CtmModuleButton;
import me.pepperbell.continuity.client.properties.OrientedConnectingCtmProperties;
import me.pepperbell.continuity.client.util.CtmGuiText;
import net.minecraft.class_1109;
import net.minecraft.class_1113;
import net.minecraft.class_332;
import net.minecraft.class_3417;
import net.minecraft.class_3532;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_6880;

public class CtmSlider
extends CtmComponent {
    private boolean sliding = false;

    public CtmSlider(Object setting, CtmModuleButton parent, int offset) {
        super(setting, parent, offset);
    }

    @Override
    public void render(class_4587 matrices, int mouseX, int mouseY, float delta) {
        if (!(this.setting instanceof OrientedConnectingCtmProperties)) {
            return;
        }
        OrientedConnectingCtmProperties numSetting = (OrientedConnectingCtmProperties)this.setting;
        class_4597.class_4598 immediate = ContinuityClient.mc.method_22940().method_23000();
        class_332 drawContext = new class_332(ContinuityClient.mc, immediate);
        int y0 = this.parent.parent.y + this.parent.offset + this.offset;
        int y1 = y0 + this.parent.parent.height;
        drawContext.method_25294(this.parent.parent.x, y0, this.parent.parent.x + this.parent.parent.width, y1, new Color(24, 30, 38, 88).getRGB());
        drawContext.method_25294(this.parent.parent.x, y1 - 1, this.parent.parent.x + this.parent.parent.width, y1, new Color(14, 18, 24, 160).getRGB());
        double diff = Math.min(this.parent.parent.width, Math.max(0, mouseX - this.parent.parent.x));
        if (this.sliding) {
            if (diff == 0.0) {
                numSetting.setValue(numSetting.getMin());
            } else {
                double percentFilled = diff / (double)this.parent.parent.width;
                double interpolatedValue = numSetting.getMin() + percentFilled * (numSetting.getMax() - numSetting.getMin());
                interpolatedValue = this.roundToPlace(interpolatedValue, 2);
                numSetting.setValue(interpolatedValue);
            }
        }
        int renderWidth = (int)((double)this.parent.parent.width * (numSetting.getValue() - numSetting.getMin()) / (numSetting.getMax() - numSetting.getMin()));
        int actualRenderWidth = class_3532.method_15340((int)renderWidth, (int)0, (int)this.parent.parent.width);
        drawContext.method_25294(this.parent.parent.x, this.parent.parent.y + this.parent.offset + this.offset, this.parent.parent.x + actualRenderWidth, this.parent.parent.y + this.parent.offset + this.offset + this.parent.parent.height, new Color(100, 234, 255, 160).getRGB());
        CtmGuiText.drawWithColonValue(ContinuityClient.mc.field_1772, this.getSettingNameSequence(), numSetting.getValue(), (float)this.parent.parent.x + 2.0f, (float)this.parent.parent.y + (float)this.parent.offset + (float)this.offset + 2.0f, 0xE0E0E0, false, matrices.method_23760().method_23761(), (class_4597)ContinuityClient.mc.method_22940().method_23000());
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {
        this.sliding = false;
        super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (this.isHovered(mouseX, mouseY) && button == 0) {
            this.sliding = true;
            ContinuityClient.mc.method_1483().method_4873((class_1113)class_1109.method_47978((class_6880)class_3417.field_15015, (float)1.0f));
        }
        super.mouseClicked(mouseX, mouseY, button);
    }

    private double roundToPlace(double value, int place) {
        if (place < 0) {
            return value;
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(place, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}

