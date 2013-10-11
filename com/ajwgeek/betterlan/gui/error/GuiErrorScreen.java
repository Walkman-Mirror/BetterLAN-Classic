package com.ajwgeek.betterlan.gui.error;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import com.ajwgeek.betterlan.constant.GlobalVariables;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiErrorScreen extends GuiScreen
{
	private String message1;
	private String message2;
	private String message3;

	public GuiErrorScreen(Exception e)
	{
		this.message1 = GlobalVariables.severeError;
		this.message2 = e.getClass().getName();	
		this.message3 = GlobalVariables.errorSaved;
	}

	public void initGui()
	{
		super.initGui();
		this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 2 + 50, GlobalVariables.close));
	}

	public void drawScreen(int par1, int par2, float par3)
	{
		this.drawGradientRect(0, 0, this.width, this.height, -12574688, -11530224);
		this.drawCenteredString(this.fontRenderer, this.message1, this.width / 2, 90, 16777215);
		this.drawCenteredString(this.fontRenderer, this.message2, this.width / 2, 110, 16777215);
		this.drawCenteredString(this.fontRenderer, this.message3, this.width / 2, 130, 16777215);
		super.drawScreen(par1, par2, par3);
	}

	protected void keyTyped(char par1, int par2)
	{
	}
	
	protected void actionPerformed(GuiButton guiButton)
	{
		if (!guiButton.enabled)
		{
			return;
		}
		else if (guiButton.id == 0)
		{
			Minecraft.getMinecraft().shutdown();
		}
	}
}