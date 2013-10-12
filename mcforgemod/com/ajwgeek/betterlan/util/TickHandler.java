package com.ajwgeek.betterlan.util;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;

import org.lwjgl.input.Keyboard;

import com.ajwgeek.betterlan.gui.debug.GuiDebugScreen;
import com.ajwgeek.betterlan.gui.progress.GuiDownloadingResources;
import com.ajwgeek.betterlan.src.BetterLAN;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class TickHandler implements ITickHandler
{
	private static boolean downloading = false;

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData)
	{
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData)
	{
		if (type.equals(EnumSet.of(TickType.CLIENT)))
		{
			Minecraft mc = Minecraft.getMinecraft();
			GuiScreen gui = mc.currentScreen;
			if (gui != null)
			{
				onTickInGui(mc, gui);
			}
			else
			{
				onTickInGame(mc);
			}
		}
	}

	@Override
	public EnumSet<TickType> ticks()
	{
		return EnumSet.of(TickType.CLIENT, TickType.RENDER);
	}

	@Override
	public String getLabel()
	{
		return "TickHandler.CLIENT";
	}

	public void displayGuiScreen(GuiScreen gs)
	{
		Minecraft.getMinecraft().displayGuiScreen(gs);
	}

	private void onTickInGame(Minecraft mc)
	{
	}

	private void onTickInGui(Minecraft mc, GuiScreen var3)
	{
		try
		{
			if (var3 instanceof GuiIngameMenu)
			{
				displayGuiScreen(new com.ajwgeek.betterlan.gui.basemod.GuiPause());
			}
			if (var3 instanceof GuiDisconnected)
			{
				BetterLAN.instance.getServerInstance().stopServer();
			}

			if (var3 instanceof GuiSelectWorld)
			{
				displayGuiScreen(new com.ajwgeek.betterlan.gui.basemod.GuiPickWorld(null));
			}

			if (var3 instanceof GuiMainMenu)
			{
				if (!downloading)
				{
					if (!BetterLAN.instance.getServerDownloaded() && !downloading)
					{
						displayGuiScreen(new GuiDownloadingResources(false));
						downloading = true;
						return;
					}
				}
				
				if (Keyboard.isKeyDown(Keyboard.KEY_D))
				{
					mc.displayGuiScreen(new GuiDebugScreen());
				}
			}
		}
		catch (Exception e)
		{
			BetterLAN.instance.getExceptionHandler().handleException(e);
		}
	}
}