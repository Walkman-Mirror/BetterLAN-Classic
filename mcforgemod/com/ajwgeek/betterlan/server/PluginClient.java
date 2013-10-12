package com.ajwgeek.betterlan.server;

import java.io.IOException;

import org.jgroups.Channel;
import org.jgroups.JChannel;
import org.jgroups.Message;

import com.ajwgeek.betterlan.src.BetterLAN;

public class PluginClient
{
	public void communicateWithServer(String command) throws IOException, ClassNotFoundException
	{
		if (BetterLAN.instance.isServerRunning())
		{
			try
			{
				Message send_msg;
				Channel channel = new JChannel();
				channel.connect("BetterLAN");
				send_msg = new Message(null, null, command);
				channel.send(send_msg);
				channel.disconnect();
				channel.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}