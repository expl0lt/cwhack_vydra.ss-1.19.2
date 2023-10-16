package net.io.fabric.loader.gui.window;


import com.mojang.blaze3d.systems.RenderSystem;
import net.io.fabric.antarctica;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.io.fabric.loader.gui.ClickGui;
import net.io.fabric.loader.gui.component.Component;
import net.io.fabric.loader.module.modules.client.CGS;
import net.io.fabric.util.RenderUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;


import java.util.ArrayList;

public class Window {
    public final ClickGui parent;
    private double x, y;
    private double width, length;
    private double scrollAmount = 0;
    protected boolean minimized = false;
    protected ArrayList<Component> components = new ArrayList<>();
    private String title = "";
    private boolean isDraggable = true;
    private boolean draggable = true;
    public boolean closable = false;
    public boolean minimizable = true;
    private boolean resizable = true;
    private boolean pinnable = true;


    public Window(ClickGui parent, double x, double y, double width, int length) {
        this.parent = parent;
        this.x = x;
        this.y = y;
        this.width = width;
        this.length = length;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addComponent(Component component) {
        components.add(component);
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        double r = CGS.class.cast(antarctica.INSTANCE.getModuleManager().getModule(CGS.class)).getHudColorRed();
        double g = CGS.class.cast(antarctica.INSTANCE.getModuleManager().getModule(CGS.class)).getHudColorGreen();
        double b = CGS.class.cast(antarctica.INSTANCE.getModuleManager().getModule(CGS.class)).getHudColorBlue();
        double f = CGS.class.cast(antarctica.INSTANCE.getModuleManager().getModule(CGS.class)).getWindowAlpha();
        TextRenderer textRenderer = antarctica.MC.textRenderer;
        if (length == 15) {
            return;
        }
        if (!minimized) {
            RenderSystem.setShader(GameRenderer::getPositionShader);
            RenderSystem.setShaderColor(0.0f, 0.0f, 0.0f, 0.2f);
            GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glEnable(GL11.GL_BLEND);
            if (parent.getTopWindow() == this)
                RenderSystem.setShaderColor(0.0f, 0.0f, 0.0f, 0.2f);
            RenderUtils.drawQuad(x, y, x + width, y + length, matrices);
            for (Component component : components) {
                GL11.glDisable(GL11.GL_CULL_FACE);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                RenderSystem.lineWidth(1);
                component.render(matrices, mouseX, mouseY, delta);
                GL11.glEnable(GL11.GL_CULL_FACE);
                GL11.glDisable(GL11.GL_BLEND);
            }
        }
        if (draggable) {
            GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glEnable(GL11.GL_BLEND);
            RenderSystem.setShader(GameRenderer::getPositionShader);
            RenderSystem.setShaderColor((float) r, (float) g, (float) b, (float) f);
            if (parent.getTopWindow() == this)
                RenderSystem.setShaderColor((float) r, (float) g, (float) b, (float) f);
            RenderUtils.drawQuad(x, y, x + width, y + 14, matrices);
            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glDisable(GL11.GL_BLEND);
        }
        if (closable) {
            GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glEnable(GL11.GL_BLEND);
            RenderSystem.setShader(GameRenderer::getPositionShader);
            RenderSystem.setShaderColor((float) r, (float) g, (float) b, 0.0f);
            double x = getX() + width - 10;
            double y = getY();
            RenderUtils.drawQuad(x, y, x + 10, y + 10, matrices);
            textRenderer.drawWithShadow(matrices, "x", (float) (x + 1), (float) y + 2, 0xFFFFFF);
            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glDisable(GL11.GL_BLEND);
        }
        if (minimizable) {
            // width of the modules (where they are not the top window)
            double x = getX() + width - 10;
            double y = getY();
        }
        RenderSystem.setShader(GameRenderer::getPositionShader);
        RenderSystem.setShaderColor((float)r, (float)g, (float)b, 1.0f);
        RenderUtils.drawQuad(this.x, this.y, this.x + 1.0, this.y + this.length, matrices);
        RenderUtils.drawQuad(this.x, this.y + this.length, this.x + this.width, this.y + this.length + 1.0, matrices);
        RenderUtils.drawQuad(this.x + this.width - 1.0, this.y, this.x + this.width, this.y + this.length, matrices);
        textRenderer.drawWithShadow(matrices, title, (float) (x + 2), (float) (y + 3), 0xFFFFFFFF);
    }

    public void onMouseMoved(double mouseX, double mouseY) {
        for (Component component : components) {
            component.onMouseMoved(mouseX, mouseY);
        }
    }

    public void onMouseClicked(double mouseX, double mouseY, int button) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_1) {
            if (closable) {
                double x = getX() + width - 10;
                double y = getY();
                if (RenderUtils.isHoveringOver(mouseX, mouseY, x, y, x + 10, y + 10))
                    parent.close(this);
            }
            if (minimizable) {
                double x = getX() + width - 10;
                double y = getY();
            }
        }
        if (!minimized && !canDrag(mouseX, mouseY)) {
            if (RenderUtils.isHoveringOver(mouseX, mouseY, x, draggable ? y + 10 : y, x + width, y + length)) {
                for (Component component : components) {
                    component.onMouseClicked(mouseX, mouseY, button);
                }
            }
        }
    }

    public void onMouseScrolled(double mouseX, double mouseY, double amount) {
        if (minimized)
            return;
        if (!RenderUtils.isHoveringOver(mouseX, mouseY, x, y, x + width, y + length))
            return;
        for (Component component : components) {
            if (component.onMouseScrolled(mouseX, mouseY, amount))
                return;
        }

        scrollAmount += amount * 2;
        if (scrollAmount > 0)
            scrollAmount = 0;
        else {
            for (Component component : components) {
                component.setY(component.getY() + amount * 0);
            }
        }
    }

    public boolean onMouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        for (Component component : components) {
            if (component.onMouseDragged(mouseX, mouseY, button, deltaX, deltaY))
                return true;
        }
        return false;
    }

    public void onClose() {

    }

    public boolean canDrag(double mouseX, double mouseY) {
        if (!draggable)
            return false;
        return RenderUtils.isHoveringOver(mouseX, mouseY, x, y, x + width, y + 10);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }


    public double getLength() {
        return length;
    }

    public boolean isHoveringOver(double mouseX, double mouseY) {
        return minimized ? canDrag(mouseX, mouseY) : RenderUtils.isHoveringOver(mouseX, mouseY, x, y, x + width, y + length);
    }
}