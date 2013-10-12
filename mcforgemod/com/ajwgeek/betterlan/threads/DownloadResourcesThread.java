package com.ajwgeek.betterlan.threads;

import net.minecraft.client.Minecraft;

import com.ajwgeek.betterlan.constant.GlobalVariables;
import com.ajwgeek.betterlan.gui.error.GuiScreenRestart;
import com.ajwgeek.betterlan.src.BetterLAN;
import com.ajwgeek.betterlan.util.FileDownloadTools;

public class DownloadResourcesThread extends Thread
{
	private boolean flag = false;
	
	public void download() throws Exception
	{
		BetterLAN b = BetterLAN.instance;
		FileDownloadTools fdu = new FileDownloadTools();

		if (flag)
		{
			//Force update
			b.serverFolder().getParentFile().delete();
			
			fdu.saveUrl(b.serverJAR().getCanonicalPath(), GlobalVariables.serverDownloadURL);
			fdu.saveUrl(b.configZip().getCanonicalPath(), GlobalVariables.configDownloadURL);
			FileDownloadTools.extractFolder(b.configZip().getCanonicalPath());
			b.configZip().deleteOnExit();
			fdu.saveUrl(b.pluginFile().getCanonicalPath(), GlobalVariables.pluginURL);
			fdu.saveUrl(b.getCBLicenseFile().getCanonicalPath(), GlobalVariables.cblicense);
			fdu.saveUrl(b.getJGLicenseFile().getCanonicalPath(), GlobalVariables.jglicense);
		}
		else
		{
			if (!b.serverJAR().exists())
			{
				fdu.saveUrl(b.serverJAR().getCanonicalPath(), GlobalVariables.serverDownloadURL);
				System.out.println("[BetterLAN] Server Jar MD5: " + FileDownloadTools.getMD5Checksum(b.serverJAR().getCanonicalPath()));
			}
			if (!b.configurationFolder().exists() && !b.configsExist())
			{
				fdu.saveUrl(b.configZip().getCanonicalPath(), GlobalVariables.configDownloadURL);
				FileDownloadTools.extractFolder(b.configZip().getCanonicalPath());
				b.configZip().deleteOnExit();
			}
			if (!b.pluginExists())
			{
				fdu.saveUrl(b.pluginFile().getCanonicalPath(), GlobalVariables.pluginURL);
			}
			
			fdu.saveUrl(b.getCBLicenseFile().getCanonicalPath(), GlobalVariables.cblicense);
			fdu.saveUrl(b.getJGLicenseFile().getCanonicalPath(), GlobalVariables.jglicense);
		}
	}

	public void dl(boolean flag)
	{
		this.flag = flag;
		this.start();
	}
	
	public void run()
	{
		try
		{
			download();
		}
		catch (Exception e)
		{
			BetterLAN.instance.getExceptionHandler().handleException(e);
			this.interrupt();
			return;
		}

		Minecraft.getMinecraft().displayGuiScreen(new GuiScreenRestart());
	}
}