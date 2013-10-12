package com.ajwgeek.betterlan.exception;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import net.minecraft.src.ModLoader;

import com.ajwgeek.betterlan.gui.error.GuiErrorScreen;
import com.ajwgeek.betterlan.src.BetterLAN;

@SuppressWarnings("deprecation")
public class ExceptionHandler
{	
	public void handleException(Exception e)
	{
		e.printStackTrace();
		PrintWriter out;
		try
		{
			out = new PrintWriter(new BufferedWriter(new FileWriter(BetterLAN.instance.errorLog(), true)));
		    e.printStackTrace(out);
		    out.close();
		}
		catch (IOException e1)
		{
			BetterLAN.instance.getExceptionHandler().handleException(e);
		}
		ModLoader.getMinecraftInstance().displayGuiScreen(new GuiErrorScreen(e));
	}
}