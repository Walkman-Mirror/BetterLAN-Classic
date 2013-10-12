package com.ajwgeek.betterlan.threads;

import java.net.ConnectException;
import java.net.UnknownHostException;

import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.multiplayer.NetClientHandler;
import net.minecraft.network.packet.Packet2ClientProtocol;

import com.ajwgeek.betterlan.gui.basemod.GuiLoading;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ThreadConnectToServer extends Thread
{
    final String ip;
    final int port;
    final GuiLoading connectingGui;

    public ThreadConnectToServer(GuiLoading par1GuiConnecting, String par2Str, int par3)
    {
        this.connectingGui = par1GuiConnecting;
        this.ip = par2Str;
        this.port = par3;
    }

    public void run()
    {
        try
        {
            GuiLoading.setNetClientHandler(this.connectingGui, new NetClientHandler(GuiLoading.func_74256_a(this.connectingGui), this.ip, this.port));

            if (GuiLoading.isCancelled(this.connectingGui))
            {
                return;
            }

            GuiLoading.getNetClientHandler(this.connectingGui).addToSendQueue(new Packet2ClientProtocol(74, GuiLoading.func_74254_c(this.connectingGui).getSession().getUsername(), this.ip, this.port));
        }
        catch (UnknownHostException unknownhostexception)
        {
            if (GuiLoading.isCancelled(this.connectingGui))
            {
                return;
            }

            GuiLoading.func_74250_f(this.connectingGui).displayGuiScreen(new GuiDisconnected(GuiLoading.func_98097_e(this.connectingGui), "connect.failed", "disconnect.genericReason", new Object[] {"Unknown host \'" + this.ip + "\'"}));
        }
        catch (ConnectException connectexception)
        {
            if (GuiLoading.isCancelled(this.connectingGui))
            {
                return;
            }

            GuiLoading.func_74251_g(this.connectingGui).displayGuiScreen(new GuiDisconnected(GuiLoading.func_98097_e(this.connectingGui), "connect.failed", "disconnect.genericReason", new Object[] {connectexception.getMessage()}));
        }
        catch (Exception exception)
        {
            if (GuiLoading.isCancelled(this.connectingGui))
            {
                return;
            }

            GuiLoading.func_98096_h(this.connectingGui).displayGuiScreen(new GuiDisconnected(GuiLoading.func_98097_e(this.connectingGui), "connect.failed", "disconnect.genericReason", new Object[] {exception.toString()}));
        }
    }
}