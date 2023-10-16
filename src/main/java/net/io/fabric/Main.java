package net.io.fabric;

import net.fabricmc.api.ModInitializer;

public class Main implements ModInitializer {
    @Override
    public void onInitialize() {
        try {
            antarctica.INSTANCE.init();
        } catch (Exception ignored) {

        }
    }
}