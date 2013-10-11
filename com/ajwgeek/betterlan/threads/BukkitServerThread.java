package com.ajwgeek.betterlan.threads;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import com.ajwgeek.betterlan.server.BetterlanServer;
import com.ajwgeek.betterlan.src.BetterLAN;

public class BukkitServerThread extends Thread
{
	private BetterlanServer server;

	public BukkitServerThread(BetterlanServer srvr)
	{
		server = srvr;
	}

	public void run()
	{
		String pluginDir = "/plugins/";
		try
		{
			pluginDir = BetterLAN.instance.serverPluginsFolder().getCanonicalPath();
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
				;
		String[] args = { "-w" + server.worldName, "-W" + server.lan.mcSaveDir(), "server-port", "25565", "--nojline", "--noconsole", "-P", pluginDir};
		try
		{
			System.out.println("[BetterLAN] Starting server process");
			URL[] jarURLArray = { server.lan.serverJAR().toURL() };
			URLClassLoader serverClassLoader = new URLClassLoader(jarURLArray, this.getClass().getClassLoader());
			Class mainClass = Class.forName("org.bukkit.craftbukkit.Main", true, serverClassLoader);
			Class sampleArgClass[] = { (new String[1]).getClass() };
			Method mainMethod = mainClass.getDeclaredMethod("main", sampleArgClass);
			Object mainMethodInstance = mainClass.newInstance();
			Object serverArgConverted[] = { args };
			Object result = mainMethod.invoke(mainMethodInstance, serverArgConverted);
		}
		catch (Exception e)
		{
			BetterLAN.instance.getExceptionHandler().handleException(e);
		}
	}
}