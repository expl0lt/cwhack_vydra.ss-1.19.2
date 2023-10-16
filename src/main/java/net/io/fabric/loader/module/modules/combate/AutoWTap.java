package net.io.fabric.loader.module.modules.combate;

import net.io.fabric.loader.event.events.AttackEntityListener;
import net.io.fabric.loader.event.events.ItemUseListener;
import net.io.fabric.loader.event.events.PlayerTickListener;
import net.io.fabric.loader.module.Category;
import net.io.fabric.loader.module.Module;
import net.io.fabric.loader.module.setting.IntegerSetting;

import static net.io.fabric.antarctica.MC;

public class AutoWTap extends Module implements AttackEntityListener, PlayerTickListener {

    private IntegerSetting delay = IntegerSetting.Builder.newInstance()
            .setName("Delay")
            .setDescription("delay in ticks")
            .setModule(this)
            .setValue(0)
            .setMin(0)
            .setMax(10)
            .setAvailability(() -> true)
            .build();

    private int delayClock = 0;
    private boolean reset = false;

    public AutoWTap() {
        super("AutoWTap", "automaticly reset sprint", false, Category.Combat);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        eventManager.add(AttackEntityListener.class, this);
        eventManager.add(PlayerTickListener.class, this);

        reset = false;
        delayClock = 0;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        eventManager.remove(AttackEntityListener.class, this);
        eventManager.remove(PlayerTickListener.class, this);;
    }

    @Override
    public void ItemUseListener(ItemUseListener.ItemUseEvent event) {

    }

    @Override
    public void onPlayerTick() {
        if (reset && delayClock != delay.get())
            delayClock++;
        else if (reset) {
            MC.options.sprintKey.setPressed(true);
            reset = false;
            delayClock = 0;
        }
    }

    @Override
    public void onAttackEntity(AttackEntityEvent event) {
        if (MC.player.isSprinting()) {
            MC.options.sprintKey.setPressed(false);
            reset = true;
        }
    }
}
