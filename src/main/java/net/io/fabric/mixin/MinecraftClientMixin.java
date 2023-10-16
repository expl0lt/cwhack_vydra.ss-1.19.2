package net.io.fabric.mixin;

import net.io.fabric.antarctica;
import net.minecraft.client.MinecraftClient;
import net.io.fabric.loader.event.EventManager;
import net.io.fabric.loader.event.events.ItemUseListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.io.fabric.loader.module.modules.render.NTGS;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Inject(at = @At("HEAD"), method = "doItemUse", cancellable = true)
    private void onDoItemUse(CallbackInfo ci) {
        ItemUseListener.ItemUseEvent event = new ItemUseListener.ItemUseEvent();
        EventManager.fire(event);
        if (event.isCancelled())
            ci.cancel();
    }
    @Inject(at = @At("HEAD"), method = "hasOutline", cancellable = true)
    public void hasOutline(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if(antarctica.INSTANCE.getModuleManager().getModule(NTGS.class).isEnabled()){
            if((entity instanceof ClientPlayerEntity || entity instanceof OtherClientPlayerEntity) && entity != MinecraftClient.getInstance().player) {
                cir.setReturnValue(true);
            }
        }
    }
}
