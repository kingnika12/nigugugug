/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2561
 *  net.minecraft.class_332
 *  net.minecraft.class_437
 */
package me.pepperbell.continuity.client.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import me.pepperbell.continuity.client.ContinuityClient;
import me.pepperbell.continuity.client.config.ContinuityConfigScreen;
import me.pepperbell.continuity.client.model.CtmFrame;
import me.pepperbell.continuity.client.properties.RandomCtmProperties;
import net.minecraft.class_2561;
import net.minecraft.class_332;
import net.minecraft.class_437;

public class CtmScreen
extends class_437 {
    public static final CtmScreen INSTANCE = new CtmScreen();
    private List<CtmFrame> frames = new ArrayList<CtmFrame>();
    private int ticksOpen = 0;
    private boolean justOpened = true;
    private boolean framesBuilt = false;

    public List<CtmFrame> getFrames() {
        return this.frames;
    }

    private CtmScreen() {
        super((class_2561)class_2561.method_43470((String)String.join((CharSequence)"", "Co", "nt", "in", "ui", "ty", " ", "Se", "tt", "in", "gs")));
    }

    private void ensureFramesBuilt() {
        if (this.framesBuilt || ContinuityClient.INSTANCE == null) {
            return;
        }
        this.framesBuilt = true;
        this.frames.clear();
        int offset = 20;
        for (RandomCtmProperties category : RandomCtmProperties.values()) {
            this.frames.add(new CtmFrame(category, offset, 20, 100, 15));
            offset += 120;
        }
    }

    public void method_25394(class_332 matrices, int mouseX, int mouseY, float delta) {
        this.ensureFramesBuilt();
        Color translucentBlack = new Color(0, 0, 0, 52);
        matrices.method_25294(0, 0, this.field_22789, this.field_22790, translucentBlack.getRGB());
        for (CtmFrame frame : this.frames) {
            frame.render(matrices, mouseX, mouseY, delta);
            frame.updatePosition(mouseX, mouseY);
        }
        ++this.ticksOpen;
        if (this.justOpened && this.ticksOpen > 5) {
            this.justOpened = false;
        }
    }

    public boolean method_25402(double mouseX, double mouseY, int button) {
        this.ensureFramesBuilt();
        for (CtmFrame frame : this.frames) {
            frame.mouseClicked(mouseX, mouseY, button);
        }
        return super.method_25402(mouseX, mouseY, button);
    }

    public boolean method_25406(double mouseX, double mouseY, int button) {
        this.ensureFramesBuilt();
        for (CtmFrame frame : this.frames) {
            frame.mouseReleased(mouseX, mouseY, button);
        }
        return super.method_25406(mouseX, mouseY, button);
    }

    public boolean method_25404(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 260 && this.ticksOpen > 5 && !this.justOpened) {
            ContinuityClient.INSTANCE.getModuleManager().getModule(ContinuityConfigScreen.class).setEnabled(false);
            return true;
        }
        this.ensureFramesBuilt();
        for (CtmFrame frame : this.frames) {
            frame.keyPressed(keyCode, scanCode, modifiers);
        }
        return super.method_25404(keyCode, scanCode, modifiers);
    }

    public boolean method_25421() {
        return false;
    }
}

