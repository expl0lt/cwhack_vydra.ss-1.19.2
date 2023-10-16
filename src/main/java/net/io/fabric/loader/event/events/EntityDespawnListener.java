package net.io.fabric.loader.event.events;

import net.minecraft.entity.Entity;
import net.io.fabric.loader.event.Event;
import net.io.fabric.loader.event.Listener;

import java.util.ArrayList;

public interface EntityDespawnListener extends Listener {
    void onEntityDespawn(Entity entity);

    class EntityDespawnEvent extends Event<EntityDespawnListener> {

        private Entity entity;

        public EntityDespawnEvent(Entity entity) {
            this.entity = entity;
        }

        @Override
        public void fire(ArrayList<EntityDespawnListener> listeners) {
            listeners.forEach(e -> e.onEntityDespawn(entity));
        }

        @Override
        public Class<EntityDespawnListener> getListenerType() {
            return EntityDespawnListener.class;
        }
    }
}
