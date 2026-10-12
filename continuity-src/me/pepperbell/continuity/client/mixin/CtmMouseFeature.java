/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_310
 *  net.minecraft.class_312
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Redirect
 */
package me.pepperbell.continuity.client.mixin;

import me.pepperbell.continuity.client.mixin.MinecraftClientAccessor;
import net.minecraft.class_310;
import net.minecraft.class_312;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value={class_312.class})
public class CtmMouseFeature {
    @Redirect(method={"onMouseButton"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/MinecraftClient;doAttack()Z"), require=0)
    private boolean continuity$allowSimulatedClicksToAttack(class_310 instance) {
        return ((MinecraftClientAccessor)instance).invokeDoAttack();
    }
}

