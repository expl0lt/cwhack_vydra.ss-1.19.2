package net.io.fabric.loader.event.events;

import net.io.fabric.loader.event.Event;
import net.io.fabric.loader.event.Listener;

import java.util.ArrayList;

public interface UpdateListener extends Listener {
    void onUpdate();

    class UpdateEvent extends Event<UpdateListener> {

        @Override
        public void fire(ArrayList<UpdateListener> listeners) {
            for (UpdateListener listener : listeners) {
                listener.onUpdate();
            }
        }

        @Override
        public Class<UpdateListener> getListenerType() {
            return UpdateListener.class;
        }
    }
}
