package com.ajwgeek.betterlan.gui.progress;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.logging.ILogAgent;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet254ServerPing;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;

import com.ajwgeek.betterlan.constant.GlobalVariables;
import com.ajwgeek.betterlan.gui.basemod.CustomGuiConnecting;
import com.ajwgeek.betterlan.src.BetterLAN;

public class GuiScreenLoading extends GuiScreenProgress
{
	private static String output;
	private BetterLAN lan;
	private ServerData data = new ServerData("BetterLAN Server", "127.0.0.1:25565");
	private boolean connected = false;
	
	public GuiScreenLoading()
	{
		super();
		this.lan = BetterLAN.instance;
	}

	public static void setOutput(String out)
	{
		output = out;
	}

	private void joinGame()
	{
		this.mc.displayGuiScreen(new CustomGuiConnecting(null, this.mc, data));
	}
	
	
	public void drawScreen(int x, int y, float f)
	{
		drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, GlobalVariables.loadingText, this.width / 2, this.height / 2, 0xFFFFFF);
		
		incrementAndDraw();
		
		new Thread()
		{
			public void run()
			{
				try
				{
					updateServerData(data);
				}
				catch (IOException e)
				{
					;
				}
			}
		}.start();
		
		while (data.serverMOTD != null && !connected)
		{
			connected = true;
			lan.setServerRunning(true);
			lan.getServerInstance().processWorldSettings();
			joinGame();	
		}
		
		super.drawScreen(x, y, f);
	}
}