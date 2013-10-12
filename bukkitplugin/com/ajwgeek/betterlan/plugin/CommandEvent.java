package com.ajwgeek.betterlan.plugin;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;

public class CommandEvent extends ReceiverAdapter
{
	@Override
	public void receive(Message msg)
	{
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), msg.getObject().toString());
		Bukkit.getLogger().log(Level.INFO, "Command: " + msg.getObject().toString());
	}
}