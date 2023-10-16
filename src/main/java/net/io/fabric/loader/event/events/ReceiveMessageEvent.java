package net.io.fabric.loader.event.events;


import net.minecraft.text.Text;

public class ReceiveMessageEvent {
    private static final ReceiveMessageEvent INSTANCE = new ReceiveMessageEvent();

    private Text message;
    private boolean modified;
    public int id;

    public static ReceiveMessageEvent get(Text message, int id) {
        INSTANCE.message = message;
        INSTANCE.modified = false;
        INSTANCE.id = id;
        return INSTANCE;
    }

    public Text getMessage() {
        return message;
    }

    public void setMessage(Text message) {
        this.message = message;
        this.modified = true;
    }

    public boolean isModified() {
        return modified;
    }
}