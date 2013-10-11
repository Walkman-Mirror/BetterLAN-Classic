package com.ajwgeek.betterlan.gui.progress;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet254ServerPing;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;

import com.ajwgeek.betterlan.src.BetterLAN;
import com.ajwgeek.betterlan.util.FileDownloadTools;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiScreenProgress extends GuiScreen
{
	protected long lastMills;
	protected long startMills;
	protected int currentMS = 0;
	protected String progressIndicator;
	protected BetterLAN b;
	protected FileDownloadTools fdu;
	protected int percent = 0;
	protected boolean downloading = false;

	public GuiScreenProgress()
	{
		progressIndicator = new String();
		lastMills = startMills = System.currentTimeMillis();
		b = BetterLAN.instance;
		fdu = new FileDownloadTools();
	}

	public static String readString(DataInput par0DataInput, int par1) throws IOException
	{
		short short1 = par0DataInput.readShort();

		if (short1 > par1)
		{
			throw new IOException("Received string length longer than maximum allowed (" + short1 + " > " + par1 + ")");
		}
		else if (short1 < 0)
		{
			throw new IOException("Received string length is less than zero! Weird string!");
		}
		else
		{
			StringBuilder stringbuilder = new StringBuilder();

			for (int j = 0; j < short1; ++j)
			{
				stringbuilder.append(par0DataInput.readChar());
			}

			return stringbuilder.toString();
		}
	}

	public void updateServerData(ServerData data) throws IOException
	{
		ServerData par0ServerData = data;

		ServerAddress serveraddress = ServerAddress.func_78860_a(par0ServerData.serverIP);
		Socket socket = null;
		DataInputStream datainputstream = null;
		DataOutputStream dataoutputstream = null;

		socket = new Socket();
		socket.setSoTimeout(3000);
		socket.setTcpNoDelay(true);
		socket.setTrafficClass(18);
		socket.connect(new InetSocketAddress(serveraddress.getIP(), serveraddress.getPort()), 3000);
		datainputstream = new DataInputStream(socket.getInputStream());
		dataoutputstream = new DataOutputStream(socket.getOutputStream());
		Packet254ServerPing packet254serverping = new Packet254ServerPing(74, serveraddress.getIP(), serveraddress.getPort());
		dataoutputstream.writeByte(packet254serverping.getPacketId());
		packet254serverping.writePacketData(dataoutputstream);

		if (datainputstream.read() != 255)
		{
			socket.close();
			throw new IOException("Bad message");
		}

		String s = readString(datainputstream, 256);
		char[] achar = s.toCharArray();

		for (int i = 0; i < achar.length; ++i)
		{
			if (achar[i] != 167 && achar[i] != 0 && ChatAllowedCharacters.allowedCharacters.indexOf(achar[i]) < 0)
			{
				achar[i] = 63;
			}
		}

		s = new String(achar);
		int j;
		int k;
		String[] astring;

		if (s.startsWith("\u00a7") && s.length() > 1)
		{
			astring = s.substring(1).split("\u0000");

			if (MathHelper.parseIntWithDefault(astring[0], 0) == 1)
			{
				par0ServerData.serverMOTD = astring[3];
				par0ServerData.field_82821_f = MathHelper.parseIntWithDefault(astring[1], par0ServerData.field_82821_f);
				par0ServerData.gameVersion = astring[2];
				j = MathHelper.parseIntWithDefault(astring[4], 0);
				k = MathHelper.parseIntWithDefault(astring[5], 0);

				if (j >= 0 && k >= 0)
				{
					par0ServerData.populationInfo = EnumChatFormatting.GRAY + "" + j + "" + EnumChatFormatting.DARK_GRAY + "/" + EnumChatFormatting.GRAY + k;
				}
				else
				{
					par0ServerData.populationInfo = "" + EnumChatFormatting.DARK_GRAY + "???";
				}
			}
		}
		
		socket.close();
	}

	public void setDownloading(boolean b)
	{
		downloading = b;
	}

	public void setPercent(int i)
	{
		percent = i;
	}

	protected void keyTyped(char par1, int par2)
	{
	}

	protected void incrementAndDraw()
	{
		if (System.currentTimeMillis() - lastMills > 100)
		{
			switch (currentMS)
			{
			case 0:
				progressIndicator = "ooo";
				currentMS = 1;
				break;
			case 1:
				progressIndicator = "Ooo";
				currentMS = 2;
				break;
			case 2:
				progressIndicator = "oOo";
				currentMS = 3;
				break;
			case 3:
				currentMS = 0;
				progressIndicator = "ooO";
				break;
			}
			lastMills = System.currentTimeMillis();
		}

		if (downloading)
		{
			drawCenteredString(fontRenderer, percent + "%", width / 2, height / 6 + 90, 0xffffff);
		}
		else
		{
			drawCenteredString(fontRenderer, progressIndicator, width / 2, height / 6 + 90, 0xffffff);
		}
	}
}