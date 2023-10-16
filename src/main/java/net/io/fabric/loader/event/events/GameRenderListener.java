package net.io.fabric.loader.event.events;

import net.minecraft.client.util.math.MatrixStack;
import net.io.fabric.loader.event.Event;
import net.io.fabric.loader.event.Listener;

import java.util.ArrayList;

public interface GameRenderListener extends Listener {
    void onGameRender(MatrixStack matrixStack, float tickDelta);

    class GameRenderEvent extends Event<GameRenderListener> {

        private MatrixStack matrixStack;
        private float tickDelta;

        public GameRenderEvent(MatrixStack matrixStack, float tickDelta) {
            this.matrixStack = matrixStack;
            this.tickDelta = tickDelta;
        }

        @Override
        public void fire(ArrayList<GameRenderListener> listeners) {
            listeners.forEach(e -> e.onGameRender(matrixStack, tickDelta));
        }

        @Override
        public Class<GameRenderListener> getListenerType() {
            return GameRenderListener.class;
        }

        public float getTickDelta() {
            return tickDelta;
        }

        public MatrixStack getMatrixStack() {
            return matrixStack;
        }
    }
}
