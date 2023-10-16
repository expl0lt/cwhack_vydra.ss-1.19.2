package net.io.fabric.loader.gui;

import net.io.fabric.antarctica;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.io.fabric.loader.module.modules.client.CGS;

public class GuiScreen extends Screen {

    private final ClickGui gui;

    double r = CGS.class.cast(antarctica.INSTANCE.getModuleManager().getModule(CGS.class)).getHudColorRed();
    double g = CGS.class.cast(antarctica.INSTANCE.getModuleManager().getModule(CGS.class)).getHudColorGreen();
    double b = CGS.class.cast(antarctica.INSTANCE.getModuleManager().getModule(CGS.class)).getHudColorBlue();

    public GuiScreen() {
        super(Text.literal("gui"));
        gui = antarctica.INSTANCE.getClickGui();
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
        this.renderBackground(matrices);
        gui.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        gui.handleMouseMoved(mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        gui.handleMouseClicked(mouseX, mouseY, button);
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        gui.handleMouseReleased(mouseX, mouseY, button);
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        gui.handleMouseScrolled(mouseX, mouseY, amount);
        return super.mouseScrolled(mouseX, mouseY, amount);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        gui.handleMouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }
}

