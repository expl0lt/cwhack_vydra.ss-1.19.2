package net.io.fabric.loader.module.setting;

import net.minecraft.network.Packet;
import net.io.fabric.loader.event.Event;

import java.util.ArrayList;

public class PacketEvent extends Event {
    private Packet<?> packet;

    public PacketEvent(Packet<?> packet) {
        this.packet = packet;
    }

    public Packet<?> getPacket() {
        return packet;
    }

    public void setPacket(Packet<?> packet) {
        this.packet = packet;
    }

    @Override
    public void fire(ArrayList listeners) {

    }

    @Override
    public Class getListenerType() {
        return null;
    }

    public static class Read extends PacketEvent {

        public Read(Packet<?> packet) {
            super(packet);
        }

    }

    public static class Send extends PacketEvent {

        public Send(Packet<?> packet) {
            super(packet);
        }

    }
}
