package net.io.fabric.loader.event.events;


import net.io.fabric.loader.event.CancellableEvent;
import net.io.fabric.loader.event.Listener;

import java.util.ArrayList;

public interface SendChatMessageListener extends Listener {
    void sendChatMessage(SendChatMessageEvent event);

    class SendChatMessageEvent extends CancellableEvent<SendChatMessageListener> {

        private String message;
        private boolean modified;

        public SendChatMessageEvent(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
            modified = true;
        }

        public boolean isModified() {
            return modified;
        }

        @Override
        public void fire(ArrayList<SendChatMessageListener> listeners) {
            listeners.forEach(listener -> listener.sendChatMessage(this));
        }

        @Override
        public Class<SendChatMessageListener> getListenerType() {
            return SendChatMessageListener.class;
        }
    }
}
