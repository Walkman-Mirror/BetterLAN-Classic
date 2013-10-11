package com.ajwgeek.betterlan.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import net.minecraft.client.Minecraft;

import com.ajwgeek.betterlan.gui.progress.GuiScreenProgress;
import com.ajwgeek.betterlan.src.BetterLAN;

public class FileDownloadTools
{
	public void saveUrl(String filename, String urlString) throws Exception
	{
		System.out.println("[BetterLAN] Saving: " + urlString + " to: " + filename);
		URL url = new URL(urlString);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		int filesize = connection.getContentLength();
		float totalDataRead = 0;
		java.io.BufferedInputStream in = new java.io.BufferedInputStream(connection.getInputStream());
		java.io.FileOutputStream fos = new java.io.FileOutputStream(filename);
		java.io.BufferedOutputStream bout = new BufferedOutputStream(fos, 1024);
		byte[] data = new byte[1024];
		int i = 0;
		GuiScreenProgress m = (GuiScreenProgress) Minecraft.getMinecraft().currentScreen;

		while ((i = in.read(data, 0, 1024)) >= 0)
		{
			totalDataRead = totalDataRead + i;
			bout.write(data, 0, i);
			float percent = (totalDataRead * 100) / filesize;
			m.setDownloading(true);
			m.setPercent((int) percent);
		}

		m.setDownloading(false);
		bout.close();
		in.close();
	}

	@SuppressWarnings("resource")
	public static void extractFolder(String zipFile) throws ZipException, IOException
	{
		System.out.println("[BetterLAN] Extracting: " + zipFile);

		int BUFFER = 2048;
		File file = new File(zipFile);

		ZipFile zip = new ZipFile(file);
		String newPath = zipFile.substring(0, zipFile.length() - 4);

		new File(newPath).mkdir();
		Enumeration<? extends ZipEntry> zipFileEntries = zip.entries();

		while (zipFileEntries.hasMoreElements())
		{
			ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();
			String currentEntry = entry.getName();
			File destFile = new File(newPath, currentEntry);
			File destinationParent = destFile.getParentFile();
			destinationParent.mkdirs();

			if (!entry.isDirectory())
			{
				BufferedInputStream is = new BufferedInputStream(zip.getInputStream(entry));
				int currentByte;
				byte data[] = new byte[BUFFER];
				FileOutputStream fos = new FileOutputStream(destFile);
				BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);
				while ((currentByte = is.read(data, 0, BUFFER)) != -1)
				{
					dest.write(data, 0, currentByte);
				}
				dest.flush();
				dest.close();
				is.close();
			}

			if (currentEntry.endsWith(".zip"))
			{
				extractFolder(destFile.getAbsolutePath());
			}
		}
	}

	public static byte[] createChecksum(String filename) throws Exception
	{
		InputStream fis = new FileInputStream(filename);
		byte[] buffer = new byte[1024];
		MessageDigest complete = MessageDigest.getInstance("MD5");
		int numRead;

		do
		{
			numRead = fis.read(buffer);
			if (numRead > 0)
			{
				complete.update(buffer, 0, numRead);
			}
		}
		while (numRead != -1);

		fis.close();
		return complete.digest();
	}

	public static String getMD5Checksum(String filename) throws Exception
	{
		byte[] b = createChecksum(filename);
		String result = "";

		for (int i = 0; i < b.length; i++)
		{
			result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
		}
		return result;
	}

	public List<String> getIP()
	{
		List<String> outputList = new ArrayList<String>();
		try
		{
			InetAddress localIP = InetAddress.getLocalHost();
			InetAddress[] everyIPAddress = InetAddress.getAllByName(localIP.getCanonicalHostName());
			if (everyIPAddress != null && everyIPAddress.length > 0)
			{
				for (int i = 0; i < everyIPAddress.length; i++)
				{
					if (!everyIPAddress[i].toString().contains(":"))
					{
						String[] split = everyIPAddress[i].toString().split("/");
						outputList.add(split[1]);
					}
				}
			}
		}
		catch (UnknownHostException e)
		{
			BetterLAN.instance.getExceptionHandler().handleException(e);
		}
		return outputList;
	}
}