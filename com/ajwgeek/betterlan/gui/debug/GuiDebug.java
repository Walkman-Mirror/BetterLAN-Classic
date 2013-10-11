package com.ajwgeek.betterlan.gui.debug;

import java.io.IOException;
import java.util.zip.ZipException;

import net.minecraft.client.gui.GuiButton;

import com.ajwgeek.betterlan.constant.GlobalVariables;
import com.ajwgeek.betterlan.gui.progress.GuiScreenDownloadingResources;
import com.ajwgeek.betterlan.gui.progress.GuiScreenProgress;
import com.ajwgeek.betterlan.util.FileDownloadTools;

public class GuiDebug extends GuiScreenProgress
{
	@SuppressWarnings("unchecked")
	public void initGui()
	{
		this.buttonList.add(new GuiButton(0, this.width / 2 - 100, 50, "Force Update Resources"));
		this.buttonList.add(new GuiButton(1, this.width / 2 - 100, 75, "Re-Download Configurations"));
		this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 2 + 75, "Done"));
	}

	protected void actionPerformed(GuiButton guiButton)
	{
		if (!guiButton.enabled)
		{
			return;
		}
		else if (guiButton.id == 0)
		{
			mc.displayGuiScreen(new GuiScreenDownloadingResources(true));
		}
		else if (guiButton.id == 1)
		{
			FileDownloadTools fdu = new FileDownloadTools();
			try
			{
				fdu.saveUrl(b.configZip().getCanonicalPath(), GlobalVariables.configDownloadURL);
				FileDownloadTools.extractFolder(b.configZip().getCanonicalPath());
				b.configZip().deleteOnExit();
				fdu.saveUrl(b.pluginFile().getCanonicalPath(), GlobalVariables.pluginURL);
				guiButton.displayString = "Done!";
				guiButton.enabled = false;
			}
			catch (ZipException e)
			{
				e.printStackTrace();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else if (guiButton.id == 2)
		{
			mc.displayGuiScreen(null);
		}
	}

	@Override
	protected void keyTyped(char par1, int par2)
	{
	}

	public void drawScreen(int par1, int par2, float par3)
	{
		drawDefaultBackground();
		drawCenteredString(fontRenderer, "BetterLAN Debug Toolkit", width / 2, 25, 0xffffff);
		super.drawScreen(par1, par2, par3);
	}
}