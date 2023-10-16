package net.io.fabric.loader.event.events;

import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.Packet;
import net.io.fabric.loader.event.CancellableEvent;
import net.io.fabric.loader.event.Listener;

import java.util.ArrayList;

public interface PacketInputListener extends Listener
{
	void onReceivePacket(PacketInputEvent event);

	class PacketInputEvent extends CancellableEvent<PacketInputListener>
	{
		private ChannelHandlerContext context;
		private Packet<?> packet;

		public PacketInputEvent(ChannelHandlerContext context, Packet<?> packet)
		{
			this.context = context;
			this.packet = packet;
		}

		public ChannelHandlerContext getContext()
		{
			return context;
		}

		public Packet<?> getPacket()
		{
			return packet;
		}

		@Override
		public void fire(ArrayList<PacketInputListener> listeners)
		{
			for (PacketInputListener listener : listeners)
			{
				listener.onReceivePacket(this);
				if (isCancelled())
					return;
			}
		}

		@Override
		public Class<PacketInputListener> getListenerType()
		{
			return PacketInputListener.class;
		}

	}

}
