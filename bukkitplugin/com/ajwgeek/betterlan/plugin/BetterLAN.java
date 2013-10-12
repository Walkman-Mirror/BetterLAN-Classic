package com.ajwgeek.betterlan.plugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
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
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (cmd.getName().equalsIgnoreCase("betterlan_broadcast"))
		{
			if (sender == Bukkit.getConsoleSender())
			{
				StringBuilder sb = new StringBuilder();
				
				for (String s: args)
				{
					sb.append(s).append(" ");
				}
				
				Bukkit.broadcastMessage(sb.toString());
				return true;
			}
			else
			{
				sender.sendMessage(ChatColor.RED + "You do not have permission to perform this action!");
				return false;
			}
		}
		{
			return false;
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		event.getPlayer().setGameMode(Bukkit.getDefaultGameMode());
		getLogger().info("Setting gamemode of player " + event.getPlayer().getDisplayName() + " to " + Bukkit.getDefaultGameMode().name());
	}
}