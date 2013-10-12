package com.ajwgeek.betterlan.gui.progress;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import com.ajwgeek.betterlan.constant.GlobalVariables;
import com.ajwgeek.betterlan.src.BetterLAN;

public class GuiScreenSaving extends GuiScreenProgress
{
	public GuiScreenSaving()
	{
		super();
		try
		{
			BetterLAN.instance.getServerInstance().stopServer();
		}
		catch (InterruptedException e)
		{
			BetterLAN.instance.getExceptionHandler().handleException(e);
		}
		catch (IOException e)
		{
			BetterLAN.instance.getExceptionHandler().handleException(e);
		}
	}

	@Override
	public void onGuiClosed()
	{
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	public void drawScreen(int x, int y, float f)
	{
		drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, GlobalVariables.savingChunks, this.width / 2, this.height / 2, 0xFFFFFF);
		incrementAndDraw();
		super.drawScreen(x, y, f);
	}
}