package com.ajwgeek.betterlan.gui.basemod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.NetClientHandler;
import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.WorldClient;

import com.ajwgeek.betterlan.constant.Constants;
import com.ajwgeek.betterlan.gui.progress.GuiProgress;
import com.ajwgeek.betterlan.threads.ThreadConnectToServer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiLoading extends GuiProgress
{
    private NetClientHandler clientHandler;
    private boolean cancelled;
    private final GuiScreen field_98098_c;
    
	public GuiLoading(GuiScreen par1GuiScreen, Minecraft par2Minecraft, ServerData par3ServerData)
    {
        this.mc = par2Minecraft;
        this.field_98098_c = par1GuiScreen;
        ServerAddress serveraddress = ServerAddress.func_78860_a(par3ServerData.serverIP);
        par2Minecraft.loadWorld((WorldClient)null);
        par2Minecraft.setServerData(par3ServerData);
        this.spawnNewServerThread(serveraddress.getIP(), serveraddress.getPort());
    }

    public GuiLoading(GuiScreen par1GuiScreen, Minecraft par2Minecraft, String par3Str, int par4)
    {
        this.mc = par2Minecraft;
        this.field_98098_c = par1GuiScreen;
        par2Minecraft.loadWorld((WorldClient)null);
        this.spawnNewServerThread(par3Str, par4);
    }

    private void spawnNewServerThread(String par1Str, int par2)
    {
        (new ThreadConnectToServer(this, par1Str, par2)).start();
    }

    public void updateScreen()
    {
        if (this.clientHandler != null)
        {
            this.clientHandler.processReadPackets();
        }
    }

    protected void keyTyped(char par1, int par2) {}

    public void initGui()
    {
        this.buttonList.clear();
    }

    protected void actionPerformed(GuiButton par1GuiButton)
    {
    }

    public void drawScreen(int par1, int par2, float par3)
    {
		drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, Constants.connectingText, this.width / 2, this.height / 2, 0xFFFFFF);
		incrementAndDraw();
    }

    public static NetClientHandler setNetClientHandler(GuiLoading par0GuiConnecting, NetClientHandler par1NetClientHandler)
    {
        return par0GuiConnecting.clientHandler = par1NetClientHandler;
    }

    public static Minecraft func_74256_a(GuiLoading par0GuiConnecting)
    {
        return par0GuiConnecting.mc;
    }

    public static boolean isCancelled(GuiLoading par0GuiConnecting)
    {
        return par0GuiConnecting.cancelled;
    }

    public static Minecraft func_74254_c(GuiLoading par0GuiConnecting)
    {
        return par0GuiConnecting.mc;
    }

    public static NetClientHandler getNetClientHandler(GuiLoading par0GuiConnecting)
    {
        return par0GuiConnecting.clientHandler;
    }

    public static GuiScreen func_98097_e(GuiLoading par0GuiConnecting)
    {
        return par0GuiConnecting.field_98098_c;
    }

    public static Minecraft func_74250_f(GuiLoading par0GuiConnecting)
    {
        return par0GuiConnecting.mc;
    }

    public static Minecraft func_74251_g(GuiLoading par0GuiConnecting)
    {
        return par0GuiConnecting.mc;
    }

    public static Minecraft func_98096_h(GuiLoading par0GuiConnecting)
    {
        return par0GuiConnecting.mc;
    }
    
    public static void forceTermination(GuiLoading gui)
    {
        gui.cancelled = true;
        gui.clientHandler = null;
    }
}