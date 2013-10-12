package com.ajwgeek.betterlan.gui.basemod;

import java.util.Date;

import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.world.storage.SaveFormatComparator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
class GuiPickWorldSlot extends GuiSlot
{
	final GuiPickWorld parentWorldGui;

	public GuiPickWorldSlot(GuiPickWorld par1GuiSelectWorld)
	{
		super(par1GuiSelectWorld.getMinecraft(), par1GuiSelectWorld.width, par1GuiSelectWorld.height, 32, par1GuiSelectWorld.height - 64, 36);
		this.parentWorldGui = par1GuiSelectWorld;
	}

	protected int getSize()
	{
		return GuiPickWorld.getSize(this.parentWorldGui).size();
	}

	protected void elementClicked(int par1, boolean par2)
	{
		GuiPickWorld.onElementSelected(this.parentWorldGui, par1);
		boolean flag1 = GuiPickWorld.getSelectedWorld(this.parentWorldGui) >= 0 && GuiPickWorld.getSelectedWorld(this.parentWorldGui) < this.getSize();
		GuiPickWorld.getSelectButton(this.parentWorldGui).enabled = flag1;
		GuiPickWorld.getRenameButton(this.parentWorldGui).enabled = flag1;
		GuiPickWorld.getDeleteButton(this.parentWorldGui).enabled = flag1;
		GuiPickWorld.func_82312_f(this.parentWorldGui).enabled = flag1;

		if (par2 && flag1)
		{
			this.parentWorldGui.selectWorld(par1);
		}
	}

	protected boolean isSelected(int par1)
	{
		return par1 == GuiPickWorld.getSelectedWorld(this.parentWorldGui);
	}

	protected int getContentHeight()
	{
		return GuiPickWorld.getSize(this.parentWorldGui).size() * 36;
	}

	protected void drawBackground()
	{
		this.parentWorldGui.drawDefaultBackground();
	}

	protected void drawSlot(int par1, int par2, int par3, int par4, Tessellator par5Tessellator)
	{
		SaveFormatComparator saveformatcomparator = (SaveFormatComparator) GuiPickWorld.getSize(this.parentWorldGui).get(par1);
		String s = saveformatcomparator.getDisplayName();

		if (s == null || MathHelper.stringNullOrLengthZero(s))
		{
			s = GuiPickWorld.func_82313_g(this.parentWorldGui) + " " + (par1 + 1);
		}

		String s1 = saveformatcomparator.getFileName();
		s1 = s1 + " (" + GuiPickWorld.func_82315_h(this.parentWorldGui).format(new Date(saveformatcomparator.getLastTimePlayed()));
		s1 = s1 + ")";
		String s2 = "";

		if (saveformatcomparator.requiresConversion())
		{
			s2 = GuiPickWorld.func_82311_i(this.parentWorldGui) + " " + s2;
		}
		else
		{
			s2 = GuiPickWorld.func_82314_j(this.parentWorldGui)[saveformatcomparator.getEnumGameType().getID()];

			if (saveformatcomparator.isHardcoreModeEnabled())
			{
				s2 = EnumChatFormatting.DARK_RED + I18n.getString("gameMode.hardcore") + EnumChatFormatting.RESET;
			}

			if (saveformatcomparator.getCheatsEnabled())
			{
				s2 = s2 + ", " + I18n.getString("selectWorld.cheats");
			}
		}

		this.parentWorldGui.drawString(this.parentWorldGui.fontRenderer(), s, par2 + 2, par3 + 1, 16777215);
		this.parentWorldGui.drawString(this.parentWorldGui.fontRenderer(), s1, par2 + 2, par3 + 12, 8421504);
		this.parentWorldGui.drawString(this.parentWorldGui.fontRenderer(), s2, par2 + 2, par3 + 12 + 10, 8421504);
	}
}