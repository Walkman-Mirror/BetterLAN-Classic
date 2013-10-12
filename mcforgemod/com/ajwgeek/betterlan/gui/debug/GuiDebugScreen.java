package com.ajwgeek.betterlan.gui.debug;

import net.minecraft.client.gui.GuiButton;

import com.ajwgeek.betterlan.constant.Constants;
import com.ajwgeek.betterlan.gui.progress.GuiDownloadingResources;
import com.ajwgeek.betterlan.gui.progress.GuiProgress;

public class GuiDebugScreen extends GuiProgress
{
	@SuppressWarnings("unchecked")
	public void initGui()
	{
		this.buttonList.add(new GuiButton(0, this.width / 2 - 100, 50, Constants.forceupdate));
		this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 2 + 75, Constants.done));
	}

	protected void actionPerformed(GuiButton guiButton)
	{
		if (!guiButton.enabled)
		{
			return;
		}
		else if (guiButton.id == 0)
		{
			mc.displayGuiScreen(new GuiDownloadingResources(true));
		}
		else if (guiButton.id == 2)
		{
			mc.displayGuiScreen(null);
		}
	}

	public void drawScreen(int par1, int par2, float par3)
	{
		drawDefaultBackground();
		drawCenteredString(fontRenderer, Constants.debugTitle, width / 2, 25, 0xffffff);
		super.drawScreen(par1, par2, par3);
	}
}