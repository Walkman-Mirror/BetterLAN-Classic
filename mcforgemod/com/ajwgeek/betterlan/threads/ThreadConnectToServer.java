package com.ajwgeek.betterlan.threads;

import java.net.ConnectException;
import java.net.UnknownHostException;

import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.multiplayer.NetClientHandler;
import net.minecraft.network.packet.Packet2ClientProtocol;

import com.ajwgeek.betterlan.gui.basemod.CustomGuiConnecting;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ThreadConnectToServer extends Thread
{
    final String ip;
    final int port;
    final CustomGuiConnecting connectingGui;

    public ThreadConnectToServer(CustomGuiConnecting par1GuiConnecting, String par2Str, int par3)
    {
        this.connectingGui = par1GuiConnecting;
        this.ip = par2Str;
        this.port = par3;
    }

    public void run()
    {
        try
        {
            CustomGuiConnecting.setNetClientHandler(this.connectingGui, new NetClientHandler(CustomGuiConnecting.func_74256_a(this.connectingGui), this.ip, this.port));

            if (CustomGuiConnecting.isCancelled(this.connectingGui))
            {
                return;
            }

            CustomGuiConnecting.getNetClientHandler(this.connectingGui).addToSendQueue(new Packet2ClientProtocol(74, CustomGuiConnecting.func_74254_c(this.connectingGui).getSession().getUsername(), this.ip, this.port));
        }
        catch (UnknownHostException unknownhostexception)
        {
            if (CustomGuiConnecting.isCancelled(this.connectingGui))
            {
                return;
            }

            CustomGuiConnecting.func_74250_f(this.connectingGui).displayGuiScreen(new GuiDisconnected(CustomGuiConnecting.func_98097_e(this.connectingGui), "connect.failed", "disconnect.genericReason", new Object[] {"Unknown host \'" + this.ip + "\'"}));
        }
        catch (ConnectException connectexception)
        {
            if (CustomGuiConnecting.isCancelled(this.connectingGui))
            {
                return;
            }

            CustomGuiConnecting.func_74251_g(this.connectingGui).displayGuiScreen(new GuiDisconnected(CustomGuiConnecting.func_98097_e(this.connectingGui), "connect.failed", "disconnect.genericReason", new Object[] {connectexception.getMessage()}));
        }
        catch (Exception exception)
        {
            if (CustomGuiConnecting.isCancelled(this.connectingGui))
            {
                return;
            }

            CustomGuiConnecting.func_98096_h(this.connectingGui).displayGuiScreen(new GuiDisconnected(CustomGuiConnecting.func_98097_e(this.connectingGui), "connect.failed", "disconnect.genericReason", new Object[] {exception.toString()}));
        }
    }
}