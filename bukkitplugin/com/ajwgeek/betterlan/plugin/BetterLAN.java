package com.ajwgeek.betterlan.plugin;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jgroups.JChannel;

public final class BetterLAN extends JavaPlugin implements Listener
{
	private JChannel channel;
	
	public void onDisable()
	{
		channel.close();
	}

	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
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

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		event.getPlayer().setGameMode(Bukkit.getDefaultGameMode());
		getLogger().info("Setting gamemode of player " + event.getPlayer().getDisplayName() + " to " + Bukkit.getDefaultGameMode().name());
	}
}