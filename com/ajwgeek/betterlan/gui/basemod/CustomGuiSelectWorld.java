package com.ajwgeek.betterlan.gui.basemod;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.client.AnvilConverterException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraft.client.gui.GuiErrorScreen;
import net.minecraft.client.gui.GuiRenameWorld;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.resources.I18n;
import net.minecraft.stats.StatList;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumGameType;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.SaveFormatComparator;
import net.minecraft.world.storage.WorldInfo;

import com.ajwgeek.betterlan.gui.progress.GuiScreenLoading;
import com.ajwgeek.betterlan.src.BetterLAN;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CustomGuiSelectWorld extends GuiScreen
{
	private final DateFormat dateFormatter = new SimpleDateFormat();
	protected GuiScreen parentScreen;
	protected String screenTitle = "Select world";
	private boolean selected;
	private int selectedWorld;
	private List saveList;
	private CustomGuiWorldSlot worldSlotContainer;

	private String localizedWorldText;
	private String localizedMustConvertText;

	private String[] localizedGameModeText = new String[3];

	private boolean deleting;

	private GuiButton buttonDelete;
	private GuiButton buttonSelect;
	private GuiButton buttonRename;
	private GuiButton buttonRecreate;

	public CustomGuiSelectWorld(GuiScreen par1GuiScreen)
	{
		this.parentScreen = par1GuiScreen;
	}

	public void initGui()
	{
		this.screenTitle = I18n.func_135053_a("selectWorld.title");

		try
		{
			this.loadSaves();
		}
		catch (AnvilConverterException anvilconverterexception)
		{
			BetterLAN.instance.getExceptionHandler().handleException(anvilconverterexception);
			this.mc.displayGuiScreen(new GuiErrorScreen("Unable to load words", anvilconverterexception.getMessage()));
			return;
		}

		this.localizedWorldText = I18n.func_135053_a("selectWorld.world");
		this.localizedMustConvertText = I18n.func_135053_a("selectWorld.conversion");
		this.localizedGameModeText[EnumGameType.SURVIVAL.getID()] = I18n.func_135053_a("gameMode.survival");
		this.localizedGameModeText[EnumGameType.CREATIVE.getID()] = I18n.func_135053_a("gameMode.creative");
		this.localizedGameModeText[EnumGameType.ADVENTURE.getID()] = I18n.func_135053_a("gameMode.adventure");
		this.worldSlotContainer = new CustomGuiWorldSlot(this);
		this.worldSlotContainer.registerScrollButtons(4, 5);
		this.initButtons();
	}

	private void loadSaves() throws AnvilConverterException
	{
		ISaveFormat isaveformat = this.mc.getSaveLoader();
		this.saveList = isaveformat.getSaveList();
		List saveList2 = new ArrayList();
		
		for (Object a : isaveformat.getSaveList())
		{
			//Remove the nether/end worlds.
			SaveFormatComparator b = (SaveFormatComparator) a;
			if (!b.getFileName().contains("_nether") && !b.getFileName().contains("_the_end"))
				saveList2.add(b);
		}

		Collections.sort(saveList2);
		this.saveList = saveList2;
		this.selectedWorld = -1;
	}

	protected String getSaveFileName(int par1)
	{
		return ((SaveFormatComparator) this.saveList.get(par1)).getFileName();
	}

	protected String getSaveName(int par1)
	{
		String s = ((SaveFormatComparator) this.saveList.get(par1)).getDisplayName();

		if (s == null || MathHelper.stringNullOrLengthZero(s))
		{
			s = I18n.func_135053_a("selectWorld.world") + " " + (par1 + 1);
		}

		return s;
	}

	public void initButtons()
	{
		this.buttonList.add(this.buttonSelect = new GuiButton(1, this.width / 2 - 154, this.height - 52, 150, 20, I18n.func_135053_a("selectWorld.select")));
		this.buttonList.add(new GuiButton(3, this.width / 2 + 4, this.height - 52, 150, 20, I18n.func_135053_a("selectWorld.create")));
		this.buttonList.add(this.buttonRename = new GuiButton(6, this.width / 2 - 154, this.height - 28, 72, 20, I18n.func_135053_a("selectWorld.rename")));
		this.buttonList.add(this.buttonDelete = new GuiButton(2, this.width / 2 - 76, this.height - 28, 72, 20, I18n.func_135053_a("selectWorld.delete")));
		this.buttonList.add(this.buttonRecreate = new GuiButton(7, this.width / 2 + 4, this.height - 28, 72, 20, I18n.func_135053_a("selectWorld.recreate")));
		this.buttonList.add(new GuiButton(0, this.width / 2 + 82, this.height - 28, 72, 20, I18n.func_135053_a("gui.cancel")));
		this.buttonSelect.enabled = false;
		this.buttonDelete.enabled = false;
		this.buttonRename.enabled = false;
		this.buttonRecreate.enabled = false;
	}

	protected void actionPerformed(GuiButton par1GuiButton)
	{
		if (par1GuiButton.enabled)
		{
			if (par1GuiButton.id == 2)
			{
				String s = this.getSaveName(this.selectedWorld);

				if (s != null)
				{
					this.deleting = true;
					GuiYesNo guiyesno = getDeleteWorldScreen(this, s, this.selectedWorld);
					this.mc.displayGuiScreen(guiyesno);
				}
			}
			else if (par1GuiButton.id == 1)
			{
				this.selectWorld(this.selectedWorld);
			}
			else if (par1GuiButton.id == 3)
			{
				if (BetterLAN.instance.getModEnabled())
				{
					this.mc.displayGuiScreen(new CustomGuiCreateWorld(this));
				}
				else
				{
					this.mc.displayGuiScreen(new GuiCreateWorld(this));
				}
			}
			else if (par1GuiButton.id == 6)
			{
				this.mc.displayGuiScreen(new GuiRenameWorld(this, this.getSaveFileName(this.selectedWorld)));
			}
			else if (par1GuiButton.id == 0)
			{
				this.mc.displayGuiScreen(this.parentScreen);
			}
			else if (par1GuiButton.id == 7)
			{
				GuiCreateWorld guicreateworld = new GuiCreateWorld(this);
				ISaveHandler isavehandler = this.mc.getSaveLoader().getSaveLoader(this.getSaveFileName(this.selectedWorld), false);
				WorldInfo worldinfo = isavehandler.loadWorldInfo();
				isavehandler.flush();
				guicreateworld.func_82286_a(worldinfo);
				this.mc.displayGuiScreen(guicreateworld);
			}
			else
			{
				this.worldSlotContainer.actionPerformed(par1GuiButton);
			}
		}
	}

	public void selectWorld(final int par1)
	{
		this.mc.displayGuiScreen((GuiScreen) null);

		if (!this.selected)
		{
			this.selected = true;
			String s = this.getSaveFileName(par1);

			if (s == null)
			{
				s = "World" + par1;
			}

			String s1 = this.getSaveName(par1);

			if (s1 == null)
			{
				s1 = "World" + par1;
			}

			if (this.mc.getSaveLoader().canLoadWorld(s))
			{
				if (BetterLAN.instance.getModEnabled())
				{
					Minecraft.getMinecraft().displayGuiScreen(new GuiScreenLoading());
					String world = ((SaveFormatComparator) saveList.get(par1)).getFileName();
					ISaveHandler isavehandler = mc.getSaveLoader().getSaveLoader(world, false);
					WorldInfo wi = isavehandler.loadWorldInfo();
					WorldSettings w = new WorldSettings(wi);
					BetterLAN.instance.startServer(world, w);	
				}
				else
				{
					this.mc.launchIntegratedServer(s, s1, (WorldSettings) null);
					this.mc.statFileWriter.readStat(StatList.loadWorldStat, 1);
				}
			}
		}
	}

	public void confirmClicked(boolean par1, int par2)
	{
		if (this.deleting)
		{
			this.deleting = false;

			if (par1)
			{
				ISaveFormat isaveformat = this.mc.getSaveLoader();
				isaveformat.flushCache();
				isaveformat.deleteWorldDirectory(this.getSaveFileName(par2));

				try
				{
					this.loadSaves();
				}
				catch (AnvilConverterException anvilconverterexception)
				{
					BetterLAN.instance.getExceptionHandler().handleException(anvilconverterexception);
				}
			}

			this.mc.displayGuiScreen(this);
		}
	}

	public void drawScreen(int par1, int par2, float par3)
	{
		this.worldSlotContainer.drawScreen(par1, par2, par3);
		this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 20, 16777215);
		super.drawScreen(par1, par2, par3);
	}

	public static GuiYesNo getDeleteWorldScreen(GuiScreen par0GuiScreen, String par1Str, int par2)
	{
		String s1 = I18n.func_135053_a("selectWorld.deleteQuestion");
		String s2 = "\'" + par1Str + "\' " + I18n.func_135053_a("selectWorld.deleteWarning");
		String s3 = I18n.func_135053_a("selectWorld.deleteButton");
		String s4 = I18n.func_135053_a("gui.cancel");
		GuiYesNo guiyesno = new GuiYesNo(par0GuiScreen, s1, s2, s3, s4, par2);
		return guiyesno;
	}

	public Minecraft getMinecraft()
	{
		return mc;
	}

	public FontRenderer fontRenderer()

	{
		return fontRenderer;
	}

	static List getSize(CustomGuiSelectWorld par0GuiSelectWorld)
	{
		return par0GuiSelectWorld.saveList;
	}

	static int onElementSelected(CustomGuiSelectWorld par0GuiSelectWorld, int par1)
	{
		return par0GuiSelectWorld.selectedWorld = par1;
	}

	static int getSelectedWorld(CustomGuiSelectWorld par0GuiSelectWorld)
	{
		return par0GuiSelectWorld.selectedWorld;
	}

	static GuiButton getSelectButton(CustomGuiSelectWorld par0GuiSelectWorld)
	{
		return par0GuiSelectWorld.buttonSelect;
	}

	static GuiButton getRenameButton(CustomGuiSelectWorld par0GuiSelectWorld)
	{
		return par0GuiSelectWorld.buttonDelete;
	}

	static GuiButton getDeleteButton(CustomGuiSelectWorld par0GuiSelectWorld)
	{
		return par0GuiSelectWorld.buttonRename;
	}

	static GuiButton func_82312_f(CustomGuiSelectWorld par0GuiSelectWorld)
	{
		return par0GuiSelectWorld.buttonRecreate;
	}

	static String func_82313_g(CustomGuiSelectWorld par0GuiSelectWorld)
	{
		return par0GuiSelectWorld.localizedWorldText;
	}

	static DateFormat func_82315_h(CustomGuiSelectWorld par0GuiSelectWorld)
	{
		return par0GuiSelectWorld.dateFormatter;
	}

	static String func_82311_i(CustomGuiSelectWorld par0GuiSelectWorld)
	{
		return par0GuiSelectWorld.localizedMustConvertText;
	}

	static String[] func_82314_j(CustomGuiSelectWorld par0GuiSelectWorld)
	{
		return par0GuiSelectWorld.localizedGameModeText;
	}
}