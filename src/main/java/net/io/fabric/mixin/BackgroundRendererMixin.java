package net.io.fabric.mixin;

import net.io.fabric.antarctica;
import net.io.fabric.loader.module.modules.render.NF;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.FogShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BackgroundRenderer.class)
public class BackgroundRendererMixin {
    @Inject(method = "applyFog", at = @At("RETURN"))
    private static void applyFogModifyDistance(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance,
                                               boolean thickFog, float tickDelta, CallbackInfo ci) {
        if(antarctica.INSTANCE.getModuleManager().getModule(NF.class).isEnabled())
            return;
        RenderSystem.setShaderFogStart(-8);
        RenderSystem.setShaderFogEnd(1000000);
        RenderSystem.setShaderFogShape(FogShape.CYLINDER);
    }
}
