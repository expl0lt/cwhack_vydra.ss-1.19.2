package net.io.fabric.loader.event.events;

import net.minecraft.entity.Entity;
import net.io.fabric.loader.event.Event;
import net.io.fabric.loader.event.Listener;

import java.util.ArrayList;

public interface EntitySpawnListener extends Listener {
    void onEntitySpawn(Entity entity);

    class EntitySpawnEvent extends Event<EntitySpawnListener> {

        private Entity entity;

        public EntitySpawnEvent(Entity entity) {
            this.entity = entity;
        }

        @Override
        public void fire(ArrayList<EntitySpawnListener> listeners) {
            listeners.forEach(e -> e.onEntitySpawn(entity));
        }

        @Override
        public Class<EntitySpawnListener> getListenerType() {
            return EntitySpawnListener.class;
        }
    }
}
