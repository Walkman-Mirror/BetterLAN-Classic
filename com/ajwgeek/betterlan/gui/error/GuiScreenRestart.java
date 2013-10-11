package com.ajwgeek.betterlan.gui.error;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import com.ajwgeek.betterlan.constant.GlobalVariables;

public class GuiScreenRestart extends GuiScreen
{
	public void initGui()
	{
		this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 2 + 25, GlobalVariables.close));
	}

	protected void actionPerformed(GuiButton guiButton)
	{
		if (!guiButton.enabled)
		{
			return;
		}
		else if (guiButton.id == 0)
		{
			Minecraft.getMinecraft().shutdown();
		}
	}

	@Override
	protected void keyTyped(char par1, int par2)
	{
	}

	public void drawScreen(int par1, int par2, float par3)
	{
		drawDefaultBackground();
		drawCenteredString(fontRenderer, GlobalVariables.restartNow, width / 2, height / 6 + 75, 0xffffff);
		super.drawScreen(par1, par2, par3);
	}
}