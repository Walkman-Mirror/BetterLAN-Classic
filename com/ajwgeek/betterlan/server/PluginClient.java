package com.ajwgeek.betterlan.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import com.ajwgeek.betterlan.src.BetterLAN;

public class PluginClient
{
	private InetAddress host;
	private Socket hostSocket;
	private int communicationPort;
	private ObjectOutputStream objectStream;
	private ObjectInputStream objectInStream;
	private String serverResponse;

	public void setupObjects() throws IOException
	{
		if (BetterLAN.instance.isServerRunning())
		{
			this.communicationPort = 19583;
			this.host = InetAddress.getLocalHost();
			this.hostSocket = new Socket(this.host.getHostName(), this.communicationPort);
			this.objectStream = new ObjectOutputStream(this.hostSocket.getOutputStream());
			this.objectInStream = new ObjectInputStream(this.hostSocket.getInputStream());
		}
	}

	public void communicateWithServer(String command, String typeOfCommunication) throws IOException, ClassNotFoundException
	{
		if (BetterLAN.instance.isServerRunning())
		{
			setupObjects();
			this.objectStream.writeObject(command + " " + typeOfCommunication);
			this.serverResponse = ((String) this.objectInStream.readObject());
			System.out.println(this.serverResponse);
			this.objectInStream.close();
			this.objectStream.close();
			this.hostSocket.close();
		}
	}
}