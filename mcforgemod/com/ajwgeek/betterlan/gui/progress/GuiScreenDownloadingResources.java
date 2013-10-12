package com.ajwgeek.betterlan.gui.progress;

import com.ajwgeek.betterlan.constant.GlobalVariables;
import com.ajwgeek.betterlan.src.BetterLAN;
import com.ajwgeek.betterlan.threads.DownloadResourcesThread;

public class GuiScreenDownloadingResources extends GuiScreenProgress
{
	public GuiScreenDownloadingResources(boolean flag)
	{
		super();
		BetterLAN.instance.serverFolder().mkdirs();
		BetterLAN.instance.serverPluginsFolder().mkdirs();
		new DownloadResourcesThread().dl(flag);
	}
	
	public void drawScreen(int par1, int par2, float par3)
	{
		this.drawDefaultBackground();
		if (downloading)
			drawCenteredString(this.fontRenderer, GlobalVariables.downloadingResources, width / 2, height / 6 + 75, 16777215);
		else
			drawCenteredString(this.fontRenderer, GlobalVariables.preparingDownload, width / 2, height / 6 + 75, 16777215);
		incrementAndDraw();
		super.drawScreen(par1, par2, par3);
	}
}