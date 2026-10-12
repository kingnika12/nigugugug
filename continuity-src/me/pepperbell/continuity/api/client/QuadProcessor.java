/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.api.renderer.v1.mesh.Mesh
 *  net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView
 *  net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter
 *  net.minecraft.class_1058
 *  net.minecraft.class_1920
 *  net.minecraft.class_2338
 *  net.minecraft.class_2680
 *  net.minecraft.class_4730
 *  net.minecraft.class_5819
 */
package me.pepperbell.continuity.api.client;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.fabricmc.fabric.api.renderer.v1.mesh.Mesh;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.minecraft.class_1058;
import net.minecraft.class_1920;
import net.minecraft.class_2338;
import net.minecraft.class_2680;
import net.minecraft.class_4730;
import net.minecraft.class_5819;

public interface QuadProcessor {
    public ProcessingResult processQuad(MutableQuadView var1, class_1058 var2, class_1920 var3, class_2680 var4, class_2680 var5, class_2338 var6, Supplier<class_5819> var7, int var8, ProcessingContext var9);

    public static interface Factory {
        public QuadProcessor createProcessor(Function<class_4730, class_1058> var1);
    }

    public static enum ProcessingResult {
        NEXT_PROCESSOR(String.join((CharSequence)"", "NE", "XT", "_", "PR", "OC", "ES", "SO", "R")),
        NEXT_PASS(String.join((CharSequence)"", "NE", "XT", "_", "PA", "SS")),
        STOP(String.join((CharSequence)"", "ST", "OP")),
        DISCARD(String.join((CharSequence)"", "DI", "SC", "AR", "D"));

        private final String displayName;

        private ProcessingResult(String displayName) {
            this.displayName = displayName;
        }

        public String toString() {
            return this.displayName;
        }
    }

    public static interface ProcessingContext {
        public void addEmitterConsumer(Consumer<QuadEmitter> var1);

        public void addMesh(Mesh var1);

        public QuadEmitter getExtraQuadEmitter();

        public void markHasExtraQuads();
    }
}

