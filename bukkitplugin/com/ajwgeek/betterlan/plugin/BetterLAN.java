package com.ajwgeek.betterlan.plugin;

import org.bukkit.plugin.java.JavaPlugin;
import org.jgroups.JChannel;

public final class BetterLAN extends JavaPlugin
{
	JChannel channel;

	public void onDisable()
	{
		channel.close();
	}
	
	public void onEnable()
	{
		try
		{
			channel = new JChannel();
			channel.setReceiver(new CommandEvent());
			channel.connect("BetterLAN");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}