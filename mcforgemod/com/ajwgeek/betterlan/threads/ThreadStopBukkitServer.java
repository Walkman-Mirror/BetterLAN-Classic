package com.ajwgeek.betterlan.threads;

import com.ajwgeek.betterlan.gui.progress.GuiLoadingScreen;
import com.ajwgeek.betterlan.server.BetterlanServer;
import com.ajwgeek.betterlan.src.BetterLAN;

public class ThreadStopBukkitServer extends Thread
{
	private BetterlanServer server;

	public ThreadStopBukkitServer(BetterlanServer srvr)
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
			GuiLoadingScreen.setOutput("Preparing Server");
			//TODO Delete ops, bans
		}
		catch (Exception e)
		{
			BetterLAN.instance.getExceptionHandler().handleException(e);
		}
	}
}