package net.io.fabric.loader.gui.component;

import com.mojang.blaze3d.systems.RenderSystem;
import net.io.fabric.antarctica;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.io.fabric.loader.gui.ClickGui;
import net.io.fabric.loader.gui.window.ModuleSettingWindow;
import net.io.fabric.loader.gui.window.Window;
import net.io.fabric.loader.module.Module;
import net.io.fabric.loader.module.modules.client.CGS;
import net.io.fabric.util.RenderUtils;
import org.lwjgl.glfw.GLFW;

public class ModuleButtonComponent extends Component {

    private final Module module;
    private boolean settingWindowOpened = false;
    private ModuleSettingWindow moduleSettingWindow;

    public ModuleButtonComponent(Window parent, Module module, double x, double y) {
        super(parent, x, y, 14, module.getName());
        this.module = module;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        TextRenderer textRenderer = antarctica.MC.textRenderer;
        double r = CGS.class.cast(antarctica.INSTANCE.getModuleManager().getModule(CGS.class)).getHudColorRed();
        double g = CGS.class.cast(antarctica.INSTANCE.getModuleManager().getModule(CGS.class)).getHudColorGreen();
        double b = CGS.class.cast(antarctica.INSTANCE.getModuleManager().getModule(CGS.class)).getHudColorBlue();
        double f = CGS.class.cast(antarctica.INSTANCE.getModuleManager().getModule(CGS.class)).getModuleAlpha();
        double parentX = parent.getX();
        double parentY = parent.getY();
        double parentWidth = parent.getWidth();
        double parentLength = parent.getLength();
        double parentX2 = parent.getX() + parentWidth;
        double parentY2 = parent.getY() + parentLength;
        double x = getX() + parentX;
        double y = Math.max(getY() + parentY, parentY);
        double x2 = parentX2 - getX();
        double y2 = Math.min(getY() + parentY + 14, parentY2);
        if (getY() + 14 <= 0)
            return;
        if (parentY2 - (getY() + parentY) <= 0)
            return;
        RenderSystem.setShader(GameRenderer::getPositionShader);
        if (parent == parent.parent.getTopWindow() && RenderUtils.isHoveringOver(mouseX, mouseY, x, y, x2, y2)) {
            if (module.isEnabled())
                RenderSystem.setShaderColor((float) r + 0.2f, (float) g + 0.2f, (float) b + 0.2f, 0.8f);
            else
                RenderSystem.setShaderColor(0.7f, 0.7f, 0.7f, 0.3f);
        } else {
            if (module.isEnabled())
                RenderSystem.setShaderColor((float) r, (float) g, (float) b, 0.9f);
            else
                RenderSystem.setShaderColor( 0.04f,  0.04f,  0.04f, 0.4f);
        }
        RenderUtils.drawQuad(x, y, x2, y2, matrices);
        if (module.isEnabled()) {
            RenderSystem.setShader(GameRenderer::getPositionShader);
            RenderSystem.setShaderColor((float) r + 0.06f, (float) g + 0.06f, (float) b + 0.06f, 0.7f);
            RenderUtils.drawQuad(x2 - 3.0, y, x2 - 0.0, y2, matrices);
        }
        if (!module.isEnabled()) {
            RenderSystem.setShader(GameRenderer::getPositionShader);
            RenderSystem.setShaderColor(0.0f,0.0f, 0.0f, 0.4f);
            RenderUtils.drawQuad(x2 - 3.0, y, x2 - 0.0, y2, matrices);
        }
        double textX = x + 4;
        double textY = y + 2;
            textRenderer.drawWithShadow(matrices, module.getName(), (float) textX, (float) textY, 0xFFFFFFFF);
    }

    @Override
    public void onMouseClicked(double mouseX, double mouseY, int button) {
        double parentX = parent.getX();
        double parentY = parent.getY();
        double parentWidth = parent.getWidth();
        double parentLength = parent.getLength();
        double parentX2 = parent.getX() + parentWidth;
        double parentY2 = parent.getY() + parentLength;
        double x = getX() + parentX;
        double y = getY() + parentY;
        double x2 = parentX2 - getX();
        double y2 = Math.min(y + 14, parentY2);
        if (getY() + 14 <= 0)
            return;
        if (parentY2 - (getY() + parentY) <= 0)
            return;
        if (RenderUtils.isHoveringOver(mouseX, mouseY, x, y, x2, y2)) {
            if (button == GLFW.GLFW_MOUSE_BUTTON_1) {
                module.toggle();
            } else {
                if (!settingWindowOpened && module.getSettings().size()!=0) {
                    ClickGui gui = parent.parent;
                    moduleSettingWindow = new ModuleSettingWindow(gui, mouseX, mouseY, module, this);
                    gui.add(moduleSettingWindow);
                    settingWindowOpened = true;
                } else if(module.getSettings().size()!=0) {
                    parent.parent.moveToTop(moduleSettingWindow);
                }
            }
        }
    }

    public void settingWindowClosed() {
        settingWindowOpened = false;
        moduleSettingWindow = null;
    }
}
