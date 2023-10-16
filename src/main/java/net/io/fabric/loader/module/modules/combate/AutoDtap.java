package net.io.fabric.loader.module.modules.combate;

import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.io.fabric.antarctica;
import net.io.fabric.loader.event.EventManager;
import net.io.fabric.loader.event.events.ItemUseListener;
import net.io.fabric.loader.event.events.PlayerTickListener;
import net.io.fabric.loader.module.Category;
import net.io.fabric.loader.module.Module;
import net.io.fabric.loader.module.setting.BooleanSetting;
import net.io.fabric.loader.module.setting.IntegerSetting;
import net.io.fabric.util.BlockUtils;
import net.io.fabric.util.CrystalUtils;
import org.lwjgl.glfw.GLFW;


public class AutoDtap extends Module implements PlayerTickListener, ItemUseListener
{

    private final IntegerSetting placeInterval = IntegerSetting.Builder.newInstance()
            .setName("placeInterval")
            .setDescription("the interval between placing crystals (in tick)")
            .setModule(this)
            .setValue(2)
            .setMin(0)
            .setMax(20)
            .setAvailability(() -> true)
            .build();
    private final IntegerSetting MaxCrystals = IntegerSetting.Builder.newInstance()
            .setName("MaxCrystals")
            .setDescription("the interval between breaking crystals (in tick)")
            .setModule(this)
            .setValue(2)
            .setMin(1)
            .setMax(2)
            .setAvailability(() -> false)
            .build();

    private final IntegerSetting breakInterval = IntegerSetting.Builder.newInstance()
            .setName("breakInterval")
            .setDescription("the interval between breaking crystals (in tick)")
            .setModule(this)
            .setValue(2)
            .setMin(0)
            .setMax(20)
            .setAvailability(() -> true)
            .build();

    private final BooleanSetting activateOnRightClick = BooleanSetting.Builder.newInstance()
            .setName("activateOnRightClick")
            .setDescription("will only activate on right click when enabled")
            .setModule(this)
            .setValue(false)
            .setAvailability(() -> true)
            .build();

    private final BooleanSetting stopOnKill = BooleanSetting.Builder.newInstance()
            .setName("stopOnKill")
            .setDescription("automatically stops crystalling when someone close to you dies")
            .setModule(this)
            .setValue(false)
            .setAvailability(() -> true)
            .build();

    private int crystalPlaceClock = 0;
    private int crystalBreakClock = 0;

    public AutoDtap()
    {
        super("ClosetCrystal", "Double pop like commanderducko", false, Category.Combat);
    }

    @Override
    public void onEnable()
    {
        super.onEnable();
        eventManager.add(PlayerTickListener.class, this);
        eventManager.add(ItemUseListener.class, this);
        crystalPlaceClock = 0;
        crystalBreakClock = 0;
    }

    @Override
    public void onDisable()
    {
        super.onDisable();
        EventManager eventManager = antarctica.INSTANCE.getEventManager();
        eventManager.remove(PlayerTickListener.class, this);
        eventManager.remove(ItemUseListener.class, this);
    }

    @Override
    public void ItemUseListener(ItemUseEvent event) {

    }

    private boolean isDeadBodyNearby()
    {
        return mc.world.getPlayers().parallelStream()
                .filter(e -> mc.player != e)
                .filter(e -> e.squaredDistanceTo(mc.player) < 36)
                .anyMatch(LivingEntity::isDead);
    }

    @Override
    public void onPlayerTick()
    {
        boolean dontPlaceCrystal = crystalPlaceClock != 0;
        boolean dontBreakCrystal = crystalBreakClock != 0;
        if (dontPlaceCrystal)
            crystalPlaceClock--;
        if (dontBreakCrystal)
            crystalBreakClock--;
        if (activateOnRightClick.get() && GLFW.glfwGetMouseButton(mc.getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_2) != GLFW.GLFW_PRESS)
            return;
        ItemStack mainHandStack = mc.player.getMainHandStack();
        if (!mainHandStack.isOf(Items.END_CRYSTAL))
            return;
        if (stopOnKill.get() && isDeadBodyNearby())
            return;

        if (mc.crosshairTarget instanceof EntityHitResult hit)
        {
            if (!dontBreakCrystal && hit.getEntity() instanceof EndCrystalEntity crystal)
            {
                crystalBreakClock = breakInterval.get();
                mc.interactionManager.attackEntity(mc.player, crystal);
                mc.player.swingHand(Hand.MAIN_HAND);
                antarctica.INSTANCE.getCrystalDataTracker().recordAttack(crystal);
            }
        }
        if (mc.crosshairTarget instanceof BlockHitResult hit)
        {
            BlockPos block = hit.getBlockPos();
            if (!dontPlaceCrystal && CrystalUtils.canPlaceCrystalServer(block))
            {
                crystalPlaceClock = placeInterval.get();
                ActionResult result = mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, hit);
                if (result.isAccepted() && result.shouldSwingHand())
                    mc.player.swingHand(Hand.MAIN_HAND);
            }
        }
    }

    @Override
    public void onItemUse(ItemUseEvent event)
    {
        ItemStack mainHandStack = mc.player.getMainHandStack();
        if (mc.crosshairTarget.getType() == HitResult.Type.BLOCK)
        {
            BlockHitResult hit = (BlockHitResult) mc.crosshairTarget;
            if (mainHandStack.isOf(Items.END_CRYSTAL) && BlockUtils.isBlock(Blocks.OBSIDIAN, hit.getBlockPos()))
                event.cancel();
        }
    }
}