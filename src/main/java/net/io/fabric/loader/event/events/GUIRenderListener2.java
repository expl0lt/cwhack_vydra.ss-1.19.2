package net.io.fabric.loader.event.events;

import net.io.fabric.loader.event.Event;
import net.io.fabric.loader.event.Listener;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;

public interface GUIRenderListener2 extends Listener
{
    void onRenderGUI(GUIRenderEvent event);

    class GUIRenderEvent extends Event<GUIRenderListener2>
    {

        private final MatrixStack matrixStack;
        private final float partialTicks;

        public GUIRenderEvent(MatrixStack matrixStack, float partialTicks)
        {
            this.matrixStack = matrixStack;
            this.partialTicks = partialTicks;
        }

        public MatrixStack getMatrixStack()
        {
            return matrixStack;
        }

        public float getPartialTicks()
        {
            return partialTicks;
        }

        @Override
        public void fire(ArrayList<GUIRenderListener2> listeners)
        {
            for (GUIRenderListener2 listener : listeners)
            {
                listener.onRenderGUI(this);
            }
        }

        @Override
        public Class<GUIRenderListener2> getListenerType()
        {
            return GUIRenderListener2.class;
        }
    }
}
