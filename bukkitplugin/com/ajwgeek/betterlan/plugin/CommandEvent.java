package com.ajwgeek.betterlan.plugin;

import org.bukkit.Bukkit;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;

public class CommandEvent extends ReceiverAdapter
{
	@Override
	public void receive(Message msg)
	{
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), msg.getObject().toString());	
	}
}