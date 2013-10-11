package com.ajwgeek.betterlan.server;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.storage.WorldInfo;

import com.ajwgeek.betterlan.src.BetterLAN;
import com.ajwgeek.betterlan.threads.BukkitServerStopThread;
import com.ajwgeek.betterlan.threads.BukkitServerThread;

public class BetterlanServer
{
	public String worldName;
	public BetterLAN lan;
	public WorldSettings w;

	public void sendCommand(String var1)
	{
		try
		{
			this.lan.getOutputClient().communicateWithServer("writeCommand", var1);
		}
		catch (Exception e)
		{
			BetterLAN.instance.getExceptionHandler().handleException(e);
		}
		
	}

	public void declareIntegratedServer(String worldPlaying, WorldSettings w)
	{
		this.lan = BetterLAN.instance;
		this.w = w;
		this.worldName = worldPlaying;
	}

	public void stopServer() throws InterruptedException, IOException
	{
		if (BetterLAN.instance.isServerRunning())
			new BukkitServerStopThread(this).start();
	}

	public void processWorldSettings()
	{
		sendCommand("op " + Minecraft.getMinecraft().func_110432_I().func_111285_a());
		sendCommand("defaultgamemode " + w.getGameType());
		sendCommand("difficulty " + Minecraft.getMinecraft().gameSettings.difficulty);
	}

	public void start()
	{
		new BukkitServerThread(this).start();
	}
}