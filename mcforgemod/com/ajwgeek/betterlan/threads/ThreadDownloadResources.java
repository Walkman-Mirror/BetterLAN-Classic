package com.ajwgeek.betterlan.threads;

import net.minecraft.client.Minecraft;

import com.ajwgeek.betterlan.constant.Constants;
import com.ajwgeek.betterlan.gui.error.GuiErrorRestart;
import com.ajwgeek.betterlan.src.BetterLAN;
import com.ajwgeek.betterlan.util.FileDownloadTools;

public class ThreadDownloadResources extends Thread
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
			
			fdu.saveUrl(b.serverJAR().getCanonicalPath(), Constants.serverDownloadURL);
			fdu.saveUrl(b.configZip().getCanonicalPath(), Constants.configDownloadURL);
			FileDownloadTools.extractFolder(b.configZip().getCanonicalPath());
			b.configZip().deleteOnExit();
			fdu.saveUrl(b.pluginFile().getCanonicalPath(), Constants.pluginURL);
			fdu.saveUrl(b.getCBLicenseFile().getCanonicalPath(), Constants.cblicense);
			fdu.saveUrl(b.getJGLicenseFile().getCanonicalPath(), Constants.jglicense);
		}
		else
		{
			if (!b.serverJAR().exists())
			{
				fdu.saveUrl(b.serverJAR().getCanonicalPath(), Constants.serverDownloadURL);
				System.out.println("[BetterLAN] Server Jar MD5: " + FileDownloadTools.getMD5Checksum(b.serverJAR().getCanonicalPath()));
			}
			if (!b.configurationFolder().exists() && !b.configsExist())
			{
				fdu.saveUrl(b.configZip().getCanonicalPath(), Constants.configDownloadURL);
				FileDownloadTools.extractFolder(b.configZip().getCanonicalPath());
				b.configZip().deleteOnExit();
			}
			if (!b.pluginExists())
			{
				fdu.saveUrl(b.pluginFile().getCanonicalPath(), Constants.pluginURL);
			}
			
			fdu.saveUrl(b.getCBLicenseFile().getCanonicalPath(), Constants.cblicense);
			fdu.saveUrl(b.getJGLicenseFile().getCanonicalPath(), Constants.jglicense);
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

		Minecraft.getMinecraft().displayGuiScreen(new GuiErrorRestart());
	}
}