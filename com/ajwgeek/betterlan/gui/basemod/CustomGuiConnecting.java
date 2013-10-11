package com.ajwgeek.betterlan.gui.basemod;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.NetClientHandler;
import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.WorldClient;

import com.ajwgeek.betterlan.constant.GlobalVariables;
import com.ajwgeek.betterlan.gui.progress.GuiScreenProgress;
import com.ajwgeek.betterlan.threads.ThreadConnectToServer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CustomGuiConnecting extends GuiScreenProgress
{
    private NetClientHandler clientHandler;
    private boolean cancelled;
    private final GuiScreen field_98098_c;
	private ServerData data = new ServerData("BetterLAN Server", "127.0.0.1:25565");

    public CustomGuiConnecting(GuiScreen par1GuiScreen, Minecraft par2Minecraft, ServerData par3ServerData)
    {
        this.mc = par2Minecraft;
        this.field_98098_c = par1GuiScreen;
        ServerAddress serveraddress = ServerAddress.func_78860_a(par3ServerData.serverIP);
        par2Minecraft.loadWorld((WorldClient)null);
        par2Minecraft.setServerData(par3ServerData);
        this.spawnNewServerThread(serveraddress.getIP(), serveraddress.getPort());
    }

    public CustomGuiConnecting(GuiScreen par1GuiScreen, Minecraft par2Minecraft, String par3Str, int par4)
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
		this.drawCenteredString(this.fontRenderer, GlobalVariables.connectingText, this.width / 2, this.height / 2, 0xFFFFFF);
		incrementAndDraw();
    }

    public static NetClientHandler setNetClientHandler(CustomGuiConnecting par0GuiConnecting, NetClientHandler par1NetClientHandler)
    {
        return par0GuiConnecting.clientHandler = par1NetClientHandler;
    }

    public static Minecraft func_74256_a(CustomGuiConnecting par0GuiConnecting)
    {
        return par0GuiConnecting.mc;
    }

    public static boolean isCancelled(CustomGuiConnecting par0GuiConnecting)
    {
        return par0GuiConnecting.cancelled;
    }

    public static Minecraft func_74254_c(CustomGuiConnecting par0GuiConnecting)
    {
        return par0GuiConnecting.mc;
    }

    public static NetClientHandler getNetClientHandler(CustomGuiConnecting par0GuiConnecting)
    {
        return par0GuiConnecting.clientHandler;
    }

    public static GuiScreen func_98097_e(CustomGuiConnecting par0GuiConnecting)
    {
        return par0GuiConnecting.field_98098_c;
    }

    public static Minecraft func_74250_f(CustomGuiConnecting par0GuiConnecting)
    {
        return par0GuiConnecting.mc;
    }

    public static Minecraft func_74251_g(CustomGuiConnecting par0GuiConnecting)
    {
        return par0GuiConnecting.mc;
    }

    public static Minecraft func_98096_h(CustomGuiConnecting par0GuiConnecting)
    {
        return par0GuiConnecting.mc;
    }
    
    public static void forceTermination(CustomGuiConnecting gui)
    {
        gui.cancelled = true;
        gui.clientHandler = null;
    }
}