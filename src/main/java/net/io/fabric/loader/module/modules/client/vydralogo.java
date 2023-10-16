package net.io.fabric.loader.module.modules.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.io.fabric.loader.event.events.ItemUseListener;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.io.fabric.loader.event.events.RenderHudListener;
import net.io.fabric.loader.module.Category;
import net.io.fabric.loader.module.Module;
import org.lwjgl.opengl.GL11;

public class vydralogo extends Module implements RenderHudListener
{
	private static final Identifier logoId = new Identifier("when", "logo.png");

	public vydralogo()
	{
		super("VydraLogo", "SKLIGGER", false, Category.Client);
	}

	@Override
	public void onEnable()
	{
		super.onEnable();
		eventManager.add(RenderHudListener.class, this);
	}

	@Override
	public void onDisable()
	{
		super.onDisable();
		eventManager.remove(RenderHudListener.class, this);
	}

	@Override
	public void ItemUseListener(ItemUseListener.ItemUseEvent event) {

	}

	@Override
	public void onRenderHud(MatrixStack matrices, double partialTicks)
	{
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		matrices.push();
		matrices.translate(28, 10, 0);
		matrices.scale(0.5f, 0.5f, 1);
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, logoId);
		DrawableHelper.drawTexture(matrices, 0, 3, 0, 0, 75, 81, 75 , 81);
		matrices.pop();
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_BLEND);
	}
}
