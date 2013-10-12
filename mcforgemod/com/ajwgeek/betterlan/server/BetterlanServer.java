package com.ajwgeek.betterlan.server;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ThreadLanServerPing;
import net.minecraft.world.WorldSettings;

import com.ajwgeek.betterlan.src.BetterLAN;
import com.ajwgeek.betterlan.threads.ThreadStopBukkitServer;
import com.ajwgeek.betterlan.threads.ThreadBukkitServer;

public class BetterlanServer
{
	public String worldName;
	public BetterLAN lan;
	public WorldSettings w;
	public ThreadLanServerPing lanServerPing;
	
	public void sendCommand(String var1)
	{
		try
		{
			this.lan.getOutputClient().sendCommand(var1);
		}
		catch (Exception e)
		{
			BetterLAN.instance.getExceptionHandler().handleException(e);
		}
		
	}

	public String getMOTD()
	{
		return Minecraft.getMinecraft().getSession().getUsername() + " - " + worldName;
	}
	
	public void shareToLAN()
	{
		String s = String.valueOf(25565);
        try
		{
			this.lanServerPing = new ThreadLanServerPing(this.getMOTD(), s);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
        this.lanServerPing.start();	
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
			new ThreadStopBukkitServer(this).start();
	}

	public void processWorldSettings()
	{
		sendCommand("op " + Minecraft.getMinecraft().getSession().getUsername());
		sendCommand("defaultgamemode " + w.getGameType());
		sendCommand("difficulty " + Minecraft.getMinecraft().gameSettings.difficulty);
	}

	public void start()
	{
		new ThreadBukkitServer(this).start();
	}
}