    package net.io.fabric.loader.module.modules.misc;

    import net.io.fabric.antarctica;
    import net.minecraft.entity.player.PlayerEntity;
    import net.minecraft.item.ItemStack;
    import net.minecraft.item.Items;
    import net.minecraft.util.Hand;
    import net.io.fabric.loader.event.events.ItemUseListener;
    import net.io.fabric.loader.event.events.PlayerTickListener;
    import net.io.fabric.loader.module.Category;
    import net.io.fabric.loader.module.Module;
    import net.io.fabric.loader.module.setting.BooleanSetting;
    import net.io.fabric.loader.module.setting.DecimalSetting;
    import org.lwjgl.glfw.GLFW;

    import static net.io.fabric.antarctica.MC;

    public class AXP extends Module
            implements PlayerTickListener {
        private int DropClock = 0;
        private final BooleanSetting ActivateOnRightClick = BooleanSetting.Builder.newInstance().setName("Activate On Right Click").setDescription("When deactivated, XP will also splash in Inventory Screen").setModule(this).setValue(true).setAvailability(() -> true).build();
        private final BooleanSetting OnlyMainScreen = BooleanSetting.Builder.newInstance().setName("MainList Screen Only").setDescription("When deactivated, XP will also splash in Inventory Screen").setModule(this).setValue(true).setAvailability(() -> true).build();
        private final DecimalSetting speed = DecimalSetting.Builder.newInstance().setName("Speed").setDescription("Dropping Speed").setModule(this).setValue(1.0).setMin(1.0).setMax(10.0).setStep(1.0).setAvailability(() -> true).build();

        public AXP() {
            super("AutoXP", "automatically splashes XP When you hold them", false, Category.Misc);
        }

        @Override
        public void onEnable() {
            this.DropClock = 0;
            super.onEnable();
            eventManager.add(PlayerTickListener.class, this);
        }

        @Override
        public void onDisable() {
            super.onDisable();
            eventManager.remove(PlayerTickListener.class, this);
        }

        @Override
        public void ItemUseListener(ItemUseListener.ItemUseEvent event) {

        }

        @Override
        public void onPlayerTick() {
            if (antarctica.MC.currentScreen != null && this.OnlyMainScreen.get()) {
                return;
            }
            if (this.ActivateOnRightClick.get() && GLFW.glfwGetMouseButton(MC.getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_2) != GLFW.GLFW_PRESS) {
                return;
            }
            ++this.DropClock;
            if ((double)this.DropClock < (1 / this.speed.get())) {
                return;
            }
            this.DropClock = 0;
            ItemStack mainHandStack = antarctica.MC.player.getMainHandStack();
            if (!mainHandStack.isOf(Items.EXPERIENCE_BOTTLE)) {
                return;
            }
            antarctica.MC.interactionManager.interactItem((PlayerEntity) antarctica.MC.player, Hand.MAIN_HAND);
            antarctica.MC.player.swingHand(Hand.MAIN_HAND);
        }
    }