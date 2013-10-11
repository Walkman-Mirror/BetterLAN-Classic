package com.ajwgeek.betterlan.gui.basemod;

import java.util.Random;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.stats.StatList;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumGameType;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;

import com.ajwgeek.betterlan.src.BetterLAN;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CustomGuiCreateWorld extends GuiCreateWorld
{
	protected GuiScreen parentScreen;
	private boolean createClicked;
	private boolean isHardcore;
	private int worldTypeId;
	private GuiTextField textboxSeed;
	private String folderName;
	private boolean bonusItems;
	private boolean commandsAllowed;
	private String gameMode = "survival";
	private boolean generateStructures = true;
	public CustomGuiCreateWorld(GuiScreen par1GuiScreen)
	{
		super(par1GuiScreen);
		parentScreen = par1GuiScreen;
	}

	@Override
	protected void actionPerformed(GuiButton par1GuiButton)
	{
		if (par1GuiButton.id == 0)
		{
			if (this.createClicked)
			{
				return;
			}

			this.createClicked = true;
			long i = (new Random()).nextLong();
			String s = this.textboxSeed.getText();

			if (!MathHelper.stringNullOrLengthZero(s))
			{
				try
				{
					long j = Long.parseLong(s);

					if (j != 0L)
					{
						i = j;
					}
				}
				catch (NumberFormatException numberformatexception)
				{
					i = (long) s.hashCode();
				}
			}

			WorldType.worldTypes[this.worldTypeId].onGUICreateWorldPress();

			EnumGameType enumgametype = EnumGameType.getByName(this.gameMode);
			WorldSettings worldsettings = new WorldSettings(i, enumgametype, this.generateStructures, this.isHardcore, WorldType.worldTypes[this.worldTypeId]);
			worldsettings.func_82750_a(this.generatorOptionsToUse);

			if (this.bonusItems && !this.isHardcore)
			{
				worldsettings.enableBonusChest();
			}

			if (this.commandsAllowed && !this.isHardcore)
			{
				worldsettings.enableCommands();
			}

			BetterLAN.instance.startServer(this.folderName, worldsettings);
			this.mc.statFileWriter.readStat(StatList.createWorldStat, 1);

			return;
		}

		super.actionPerformed(par1GuiButton);
	}

	@Override
	public void drawScreen(int par1, int par2, float par3)
	{
		super.drawScreen(par1, par2, par3);
	}
}