package net.io.fabric.util;

import net.io.fabric.loader.event.CancellableEvent;
import net.io.fabric.loader.event.Event;
import net.io.fabric.loader.event.EventManager;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public enum MixinUtils {
    ;

    public static void fireEvent(Event<?> event) {
        EventManager.fire(event);
    }

    public static void fireCancellable(CancellableEvent<?> event, CallbackInfo ci) {
        EventManager.fire(event);
        if (event.isCancelled() && ci.isCancellable())
            ci.cancel();
    }
}
