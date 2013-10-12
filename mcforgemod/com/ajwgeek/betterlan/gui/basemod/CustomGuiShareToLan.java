package com.ajwgeek.betterlan.gui.basemod;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.StringTranslate;

import com.ajwgeek.betterlan.src.BetterLAN;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CustomGuiShareToLan extends GuiScreen
{
	private final GuiScreen parentScreen;
	private GuiButton buttonGameMode;
	private GuiButton buttonAllowCommandsToggle;
	private GuiButton ipButton;
	private String gameMode = "survival";
	private boolean allowCommands = false;
	private String currentIP;
	private int num = 0;
	private List<String> ip;

	public CustomGuiShareToLan(GuiScreen par1GuiScreen)
	{
		this.parentScreen = par1GuiScreen;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui()
	{
		this.buttonList.clear();
		StringTranslate bm = new StringTranslate();

		this.buttonList.add(new GuiButton(101, this.width / 2 - 155, this.height - 28, 150, 20, bm.translateKey("lanServer.start")));

		this.buttonList.add(new GuiButton(102, this.width / 2 + 5, this.height - 28, 150, 20, bm.translateKey("gui.cancel")));

		this.buttonList.add(this.buttonGameMode = new GuiButton(104, this.width / 2 - 155, 100, 150, 20, bm.translateKey("selectWorld.gameMode")));

		this.buttonList.add(this.buttonAllowCommandsToggle = new GuiButton(103, this.width / 2 + 5, 100, 150, 20, bm.translateKey("selectWorld.allowCommands")));

		this.ip = getIP();
		this.currentIP = this.ip.get(this.num).toString();
		this.buttonList.add(this.ipButton = new GuiButton(105, this.width / 2 + 5, 125, 150, 20, bm.translateKey("IP: " + this.currentIP)));

		if (this.ip.size() == 1)
		{
			this.ipButton.enabled = false;
		}

		func_74088_g();
	}

	protected void a(GuiButton par1GuiButton)
	{
		if (par1GuiButton.id == 102)
		{
			this.mc.displayGuiScreen(this.parentScreen);
		}
		else if (par1GuiButton.id == 104)
		{
			if (this.gameMode.equals("survival"))
				this.gameMode = "creative";
			else if (this.gameMode.equals("creative"))
				this.gameMode = "adventure";
			else
			{
				this.gameMode = "survival";
			}
			func_74088_g();
		}
		else if (par1GuiButton.id == 103)
		{
			this.allowCommands = (!this.allowCommands);
			func_74088_g();
		}
		else if (par1GuiButton.id == 105)
		{
			if (this.num == this.ip.size() - 1)
				this.num = 0;
			else
			{
				this.num += 1;
			}
			this.currentIP = this.ip.get(this.num).toString();
			this.ipButton.displayString = ("IP: " + this.currentIP);
		}
		else if (par1GuiButton.id == 101)
		{
			this.mc.displayGuiScreen((GuiScreen) null);
			BetterLAN.instance.getOutputClient().sendCommand("defaultgamemode " + this.gameMode);
			BetterLAN.instance.setSharedServer(true);
			BetterLAN.instance.setCheatsEnabled(this.allowCommands);
		}
	}

	private List<String> getIP()
	{
		List<String> outputList = new ArrayList<String>();
		try
		{
			InetAddress localIP = InetAddress.getLocalHost();
			InetAddress[] everyIPAddress = InetAddress.getAllByName(localIP.getCanonicalHostName());

			if ((everyIPAddress != null) && (everyIPAddress.length > 0))
				for (int i = 0; i < everyIPAddress.length; i++)
					if (!everyIPAddress[i].toString().contains(":"))
					{
						String[] split = everyIPAddress[i].toString().split("/");

						outputList.add(split[1]);
					}
		}
		catch (UnknownHostException e)
		{
			BetterLAN.instance.getExceptionHandler().handleException(e);
		}
		return outputList;
	}

	private void func_74088_g()
	{
		this.buttonGameMode.displayString = I18n.getString("selectWorld.gameMode") + " " + I18n.getString("selectWorld.gameMode." + this.gameMode);
		this.buttonAllowCommandsToggle.displayString = I18n.getString("selectWorld.allowCommands") + " ";

		if (this.allowCommands)
		{
			this.buttonAllowCommandsToggle.displayString = this.buttonAllowCommandsToggle.displayString + I18n.getString("options.on");
		}
		else
		{
			this.buttonAllowCommandsToggle.displayString = this.buttonAllowCommandsToggle.displayString + I18n.getString("options.off");
		}
	}

	public void a(int par1, int par2, float par3)
	{
		StringTranslate bm = new StringTranslate();

		drawDefaultBackground();
		drawCenteredString(this.fontRenderer, bm.translateKey("lanServer.title"), this.width / 2, 50, 16777215);

		drawCenteredString(this.fontRenderer, bm.translateKey("lanServer.otherPlayers"), this.width / 2, 82, 16777215);

		super.drawScreen(par1, par2, par3);
	}
}