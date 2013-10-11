package com.ajwgeek.betterlan.threads;

import net.minecraft.src.ModLoader;

import com.ajwgeek.betterlan.gui.progress.GuiScreenLoading;
import com.ajwgeek.betterlan.server.BetterlanServer;
import com.ajwgeek.betterlan.src.BetterLAN;

public class BukkitServerStopThread extends Thread
{
	private BetterlanServer server;

	public BukkitServerStopThread(BetterlanServer srvr)
	{
		server = srvr;
	}

	public void run()
	{
		try
		{
			server.sendCommand("stop");
			server.lan.setServerRunning(false);
			server.lan.setSharedServer(false);
			server.lan.setServer(new BetterlanServer());
			server.lan.setCheatsEnabled(false);
			GuiScreenLoading.setOutput("Preparing Server");
			//TODO Delete ops, bans
		}
		catch (Exception e)
		{
			BetterLAN.instance.getExceptionHandler().handleException(e);
		}
	}
}