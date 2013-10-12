package com.ajwgeek.betterlan.server;

import org.jgroups.Channel;
import org.jgroups.JChannel;
import org.jgroups.Message;

import com.ajwgeek.betterlan.constant.GlobalVariables;
import com.ajwgeek.betterlan.src.BetterLAN;

public class BukkitActionHelper
{
	Channel channel;

	public BukkitActionHelper()
	{
		try
		{
			channel = new JChannel();
			channel.connect(GlobalVariables.modID);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void sendCommand(String command)
	{
		if (BetterLAN.instance.isServerRunning())
		{
			try
			{
				Message send_msg;
				send_msg = new Message(null, null, command);
				channel.send(send_msg);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}