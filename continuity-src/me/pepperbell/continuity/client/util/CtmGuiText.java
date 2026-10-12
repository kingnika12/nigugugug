/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2583
 *  net.minecraft.class_327
 *  net.minecraft.class_327$class_6415
 *  net.minecraft.class_4597
 *  net.minecraft.class_5481
 *  org.joml.Matrix4f
 */
package me.pepperbell.continuity.client.util;

import java.util.Objects;
import me.pepperbell.continuity.client.util.CtmLabels;
import net.minecraft.class_2583;
import net.minecraft.class_327;
import net.minecraft.class_4597;
import net.minecraft.class_5481;
import org.joml.Matrix4f;

public final class CtmGuiText {
    private CtmGuiText() {
    }

    public static class_5481 ordered(CharSequence cs) {
        return visitor -> {
            int charCount;
            int len = cs.length();
            for (int i = 0; i < len; i += charCount) {
                int cp = Character.codePointAt(cs, i);
                charCount = Character.charCount(cp);
                if (visitor.accept(i, class_2583.field_24360, cp)) continue;
                return false;
            }
            return true;
        };
    }

    public static int draw(class_327 tr, CharSequence text, float x, float y, int color, boolean shadow, Matrix4f matrix, class_4597 vcp) {
        return tr.method_22942(CtmGuiText.ordered(text), x, y, color, shadow, matrix, vcp, class_327.class_6415.field_33993, 0, 0xF000F0);
    }

    public static int drawWithColonValue(class_327 tr, CharSequence label, double value, float x, float y, int color, boolean shadow, Matrix4f matrix, class_4597 vcp) {
        class_5481 line = class_5481.method_34909((class_5481[])new class_5481[]{CtmGuiText.ordered(label), CtmGuiText.ordered(CtmLabels.lb01), class_5481.method_30747((String)Objects.toString(value), (class_2583)class_2583.field_24360)});
        return tr.method_22942(line, x, y, color, shadow, matrix, vcp, class_327.class_6415.field_33993, 0, 0xF000F0);
    }

    public static int drawConcat(class_327 tr, CharSequence first, CharSequence second, float x, float y, int color, boolean shadow, Matrix4f matrix, class_4597 vcp) {
        class_5481 line = class_5481.method_30742((class_5481)CtmGuiText.ordered(first), (class_5481)CtmGuiText.ordered(second));
        return tr.method_22942(line, x, y, color, shadow, matrix, vcp, class_327.class_6415.field_33993, 0, 0xF000F0);
    }
}

