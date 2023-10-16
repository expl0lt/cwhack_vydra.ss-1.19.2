package net.io.fabric.loader.gui.component;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.io.fabric.loader.gui.window.Window;
import net.io.fabric.mixinterface.ITextRenderer;


import static net.io.fabric.antarctica.MC;

public abstract class Component {

    public final Window parent;
    private double x, y;
    private final double length;
    private final String name;

    public Component(Window parent, double x, double y, double length, String name) {
        this.parent = parent;
        this.x = x;
        this.y = y;
        this.length = length;
        this.name = name;
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        double parentX = parent.getX();
        double parentY = parent.getY();
        double parentWidth = parent.getWidth();
        double parentLength = parent.getLength();
        double parentX2 = parent.getX() + parentWidth;
        double parentY2 = parent.getY() + parentLength;
        double x = getX() + parentX;
        double y = getY() + parentY - 10.0;
        if (getY() + parentY - parentY - 10.0 <= 0)
            return;
        if (parentY2 - (getY() + parentY) <= 0)
            return;
        ((ITextRenderer) MC.textRenderer).drawTrimmed(Text.literal(name), (float) x, (float) y, (int) (parentX2 - x), 0xFFFFFF, matrices.peek().getPositionMatrix());
    }

    public void onMouseMoved(double mouseX, double mouseY) {

    }

    public void onMouseClicked(double mouseX, double mouseY, int button) {

    }

    public boolean onMouseScrolled(double mouseX, double mouseY, double amount) {
        return false;
    }

    public boolean onMouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        return false;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getLength() {
        return length;
    }

    public String getName() {
        return name;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}
