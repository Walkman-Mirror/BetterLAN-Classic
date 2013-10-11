package com.ajwgeek.betterlan.gui.progress;

import java.io.IOException;

import net.minecraft.client.multiplayer.ServerData;

import com.ajwgeek.betterlan.constant.GlobalVariables;
import com.ajwgeek.betterlan.gui.basemod.CustomGuiConnecting;
import com.ajwgeek.betterlan.src.BetterLAN;

public class GuiScreenLoading extends GuiScreenProgress
{
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