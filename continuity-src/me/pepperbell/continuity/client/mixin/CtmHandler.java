/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.terraformersmc.modmenu.util.mod.fabric.FabricIconHandler
 *  net.fabricmc.loader.api.ModContainer
 *  net.minecraft.class_1011
 *  net.minecraft.class_1043
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package me.pepperbell.continuity.client.mixin;

import com.terraformersmc.modmenu.util.mod.fabric.FabricIconHandler;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.class_1011;
import net.minecraft.class_1043;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={FabricIconHandler.class}, remap=false)
public class CtmHandler {
    @Unique
    private static final Map<String, byte[]> continuity$iconBytesCache = new HashMap<String, byte[]>();
    @Unique
    private static final Map<String, byte[]> continuity$iconBytesByModId = new HashMap<String, byte[]>();
    @Unique
    private static final Map<String, class_1043> continuity$iconTextureCache = new HashMap<String, class_1043>();
    @Shadow
    private Map<Path, class_1043> modIconCache;

    @Inject(method={"createIcon"}, at={@At(value="RETURN")}, cancellable=true)
    private void continuity$cacheAndRestoreIcon(ModContainer iconSource, String iconPath, CallbackInfoReturnable<class_1043> cir) {
        String modId = iconSource.getMetadata().getId();
        String cacheKey = modId + "|" + iconPath;
        class_1043 result = (class_1043)cir.getReturnValue();
        if (result != null) {
            if (!continuity$iconBytesCache.containsKey(cacheKey)) {
                try {
                    Path path = iconSource.getPath(iconPath);
                    if (Files.exists(path, new LinkOption[0])) {
                        byte[] bytes = Files.readAllBytes(path);
                        continuity$iconBytesCache.put(cacheKey, bytes);
                        continuity$iconBytesByModId.put(modId, bytes);
                    }
                }
                catch (IOException path) {
                    // empty catch block
                }
            }
            continuity$iconTextureCache.put(cacheKey, result);
            return;
        }
        byte[] cachedBytes = CtmHandler.continuity$resolveIconBytes(modId, cacheKey);
        if (cachedBytes != null) {
            class_1043 existing = continuity$iconTextureCache.get(cacheKey);
            if (existing != null) {
                cir.setReturnValue((Object)existing);
                return;
            }
            try {
                class_1011 image = class_1011.method_4309((InputStream)new ByteArrayInputStream(cachedBytes));
                class_1043 tex = new class_1043(image);
                continuity$iconTextureCache.put(cacheKey, tex);
                cir.setReturnValue((Object)tex);
            }
            catch (IOException iOException) {
                // empty catch block
            }
            return;
        }
        class_1043 cachedTex = continuity$iconTextureCache.get(cacheKey);
        if (cachedTex != null) {
            cir.setReturnValue((Object)cachedTex);
        }
    }

    @Unique
    private static byte[] continuity$resolveIconBytes(String modId, String cacheKey) {
        byte[] b = continuity$iconBytesCache.get(cacheKey);
        if (b != null) {
            return b;
        }
        b = continuity$iconBytesByModId.get(modId);
        if (b != null) {
            return b;
        }
        String prefix = modId + "|";
        for (Map.Entry<String, byte[]> e : continuity$iconBytesCache.entrySet()) {
            if (!e.getKey().startsWith(prefix)) continue;
            return e.getValue();
        }
        return null;
    }

    @Inject(method={"close"}, at={@At(value="HEAD")}, cancellable=true)
    private void continuity$avoidClosingCachedIcons(CallbackInfo ci) {
        for (Map.Entry<Path, class_1043> entry : new HashMap<Path, class_1043>(this.modIconCache).entrySet()) {
            class_1043 tex = entry.getValue();
            boolean isCached = continuity$iconTextureCache.containsValue(tex);
            if (isCached || tex == null) continue;
            tex.close();
        }
        this.modIconCache.clear();
        ci.cancel();
    }
}

