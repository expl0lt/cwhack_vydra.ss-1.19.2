package net.io.fabric.mixin;

import net.io.fabric.antarctica;
import net.minecraft.client.gui.screen.DeathScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.io.fabric.loader.module.modules.render.AGH;



@Mixin(DeathScreen.class)
public class DeathScreenMixin {
    @Inject(at = @At("HEAD"), method = "init()V")
    private void init(CallbackInfo info) {
        if (antarctica.INSTANCE.getModuleManager().getModule(AGH.class).isEnabled()) {
            AGH.putTotemInHand();
        }
    }
}
