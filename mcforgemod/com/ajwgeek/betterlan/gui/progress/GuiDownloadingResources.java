package com.ajwgeek.betterlan.gui.progress;

import com.ajwgeek.betterlan.constant.Constants;
import com.ajwgeek.betterlan.src.BetterLAN;
import com.ajwgeek.betterlan.threads.ThreadDownloadResources;

public class GuiDownloadingResources extends GuiProgress
{
	public GuiDownloadingResources(boolean flag)
	{
		super();
		BetterLAN.instance.serverFolder().mkdirs();
		BetterLAN.instance.serverPluginsFolder().mkdirs();
		new ThreadDownloadResources().dl(flag);
	}
	
	public void drawScreen(int par1, int par2, float par3)
	{
		this.drawDefaultBackground();
		if (downloading)
			drawCenteredString(this.fontRenderer, Constants.downloadingResources, width / 2, height / 6 + 75, 16777215);
		else
			drawCenteredString(this.fontRenderer, Constants.preparingDownload, width / 2, height / 6 + 75, 16777215);
		incrementAndDraw();
		super.drawScreen(par1, par2, par3);
	}
}