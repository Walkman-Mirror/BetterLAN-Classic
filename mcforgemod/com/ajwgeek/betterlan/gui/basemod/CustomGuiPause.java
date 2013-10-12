package com.ajwgeek.betterlan.gui.basemod;

import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiShareToLan;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.stats.StatList;
import net.minecraft.util.StatCollector;

import com.ajwgeek.betterlan.src.BetterLAN;

public class CustomGuiPause extends GuiIngameMenu
{
	@SuppressWarnings("unchecked")
	@Override
	public void initGui()
	{
		this.buttonList.clear();
		byte var1 = -16;
		this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + var1, StatCollector.translateToLocal("menu.returnToMenu")));

		if (!this.mc.isIntegratedServerRunning() && !BetterLAN.instance.isServerRunning())
		{
			((GuiButton) this.buttonList.get(0)).displayString = StatCollector.translateToLocal("menu.disconnect");
		}

		this.buttonList.add(new GuiButton(4, this.width / 2 - 100, this.height / 4 + 24 + var1, StatCollector.translateToLocal("menu.returnToGame")));
		this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + var1, 98, 20, StatCollector.translateToLocal("menu.options")));
		GuiButton var3;
		this.buttonList.add(var3 = new GuiButton(7, this.width / 2 + 2, this.height / 4 + 96 + var1, 98, 20, StatCollector.translateToLocal("menu.shareToLan")));
		this.buttonList.add(new GuiButton(5, this.width / 2 - 100, this.height / 4 + 48 + var1, 98, 20, StatCollector.translateToLocal("gui.achievements")));
		this.buttonList.add(new GuiButton(6, this.width / 2 + 2, this.height / 4 + 48 + var1, 98, 20, StatCollector.translateToLocal("gui.stats")));

		if (BetterLAN.instance.isServerRunning() || (this.mc.isSingleplayer() && !this.mc.getIntegratedServer().getPublic()))
			var3.enabled = true;
	}

	@Override
	protected void actionPerformed(GuiButton par1GuiButton)
	{
		switch (par1GuiButton.id)
		{
		case 0:
			this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
			break;
		case 1:
			par1GuiButton.enabled = false;
			this.mc.statFileWriter.readStat(StatList.leaveGameStat, 1);
			this.mc.theWorld.sendQuittingDisconnectingPacket();
			this.mc.loadWorld((WorldClient) null);
			try
			{
				BetterLAN.instance.getServerInstance().stopServer();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			this.mc.displayGuiScreen(new GuiMainMenu());
		case 4:
			this.mc.displayGuiScreen((GuiScreen) null);
			this.mc.setIngameFocus();
			this.mc.sndManager.resumeAllSounds();
			break;
		case 5:
			this.mc.displayGuiScreen(new GuiAchievements(this.mc.statFileWriter));
			break;
		case 6:
			this.mc.displayGuiScreen(new GuiStats(this, this.mc.statFileWriter));
			break;
		case 7:
			if (!BetterLAN.instance.getModEnabled())
			{
				this.mc.displayGuiScreen(new GuiShareToLan(this));
				break;
			}
			else
			{
				this.mc.displayGuiScreen(new CustomGuiShareToLan(this));
				break;
			}
		}
	}
}