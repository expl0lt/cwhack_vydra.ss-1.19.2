package net.io.fabric.loader.event.events;

import net.io.fabric.loader.event.CancellableEvent;
import net.io.fabric.loader.event.Listener;

import java.util.ArrayList;

public interface PlayerTickMovementListener extends Listener
{
	void onPlayerTickMovement(PlayerTickMovementEvent event);

	class PlayerTickMovementEvent extends CancellableEvent<PlayerTickMovementListener>
	{
		@Override
		public void fire(ArrayList<PlayerTickMovementListener> listeners)
		{
			for (PlayerTickMovementListener listener : listeners)
			{
				listener.onPlayerTickMovement(this);
				if (isCancelled())
					return;
			}
		}

		@Override
		public Class<PlayerTickMovementListener> getListenerType()
		{
			return PlayerTickMovementListener.class;
		}
	}
}
