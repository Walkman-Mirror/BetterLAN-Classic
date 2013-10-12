package com.ajwgeek.betterlan.src;

import java.io.File;
import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.world.WorldSettings;
import net.minecraftforge.common.Configuration;

import com.ajwgeek.betterlan.constant.Constants;
import com.ajwgeek.betterlan.exception.ExceptionHandler;
import com.ajwgeek.betterlan.server.BetterlanServer;
import com.ajwgeek.betterlan.server.BukkitActionHelper;

public class BetterlanLogic
{
	protected static BetterlanServer modServer;
	protected BukkitActionHelper serverOutputClient;
	protected ExceptionHandler exceptionHandler;

	protected boolean serverShared = false;
	protected boolean serverRunning = false;
	protected boolean cheatsEnabled = false;
	
	protected boolean modEnabled;
	protected int usableServerRAM;
	
	public void setServer(BetterlanServer s)
	{
		modServer = s;
	}

	public void startServer(String folderName, WorldSettings w)
	{
		modServer.declareIntegratedServer(folderName, w);
		getServerInstance().start();
	}

	public void loadConfig()
	{
		//Init
		Configuration config = new Configuration(this.settingsFile());
		config.load();
		
		//Set variables from the configuration
		setModEnabled(config.get(Configuration.CATEGORY_GENERAL, "mod_enabled", true).getBoolean(true), config);
		setServerRAM(config.get(Configuration.CATEGORY_GENERAL, "ram", 256).getInt(), config);
		
		//Save
		config.save();
	}
	
	public void setModEnabled(boolean var1, Configuration c)
	{
		this.modEnabled = var1;
		c.save();
	}

	public void setServerRAM(int ram, Configuration c)
	{
		this.usableServerRAM = ram;
	}
	
	public void setSharedServer(boolean var1)
	{
		this.serverShared = var1;
	}

	public void setCheatsEnabled(boolean var1)
	{
		this.cheatsEnabled = var1;
	}

	public void setExceptionHandler(ExceptionHandler e)
	{
		this.exceptionHandler = e;
	}
	
	public void setServerRunning(boolean serverStarted)
	{
		this.serverRunning = serverStarted;
	}

	public void setPluginClient(BukkitActionHelper c)
	{
		this.serverOutputClient = c;
	}

	public BukkitActionHelper getOutputClient()
	{
		return serverOutputClient;
	}

	public BetterlanServer getServerInstance()
	{
		return modServer;
	}

	public int getServerRAM()
	{
		return this.usableServerRAM;
	}

	public File getMinecraftDir()
	{
		return Minecraft.getMinecraft().mcDataDir;
	}

	public File settingsFile()
	{
		String a = null;
		try
		{
			a = getMinecraftDir().getCanonicalPath() + "/" + Constants.modFolderName + "/" + Constants.settingsFile;
		} catch (IOException e)
		{
			BetterLAN.instance.getExceptionHandler().handleException(e);
		}

		return new File(a);
	}

	public File serverFolder()
	{
		String a = null;
		try
		{
			a = getMinecraftDir().getCanonicalPath() + "/" + Constants.modFolderName + "/" + Constants.serverFilesFolder;
		} catch (IOException e)
		{
			BetterLAN.instance.getExceptionHandler().handleException(e);
		}

		return new File(a);
	}

	public boolean pluginExists()
	{
		return pluginFile().exists();
	}
	
	public File getJGLicenseFile()
	{
		String a = null;
		try
		{
			a = getMinecraftDir().getCanonicalPath() + "/" + Constants.modFolderName + "/" + Constants.jgLicenseFile;
		} catch (IOException e)
		{
			BetterLAN.instance.getExceptionHandler().handleException(e);
		}

		try
		{
			return new File(a).getCanonicalFile();
		} catch (IOException e)
		{
			BetterLAN.instance.getExceptionHandler().handleException(e);
			return new File(a);
		}
	}
	
	public File getCBLicenseFile()
	{
		String a = null;
		try
		{
			a = getMinecraftDir().getCanonicalPath() + "/" + Constants.modFolderName + "/" + Constants.cbLicenseFile;
		} catch (IOException e)
		{
			BetterLAN.instance.getExceptionHandler().handleException(e);
		}

		try
		{
			return new File(a).getCanonicalFile();
		} catch (IOException e)
		{
			BetterLAN.instance.getExceptionHandler().handleException(e);
			return new File(a);
		}
	}
	
	public File pluginFile()
	{
		String a = null;
		try
		{
			a = serverPluginsFolder().getCanonicalPath() + "/" + Constants.pName;
		} catch (IOException e)
		{
			BetterLAN.instance.getExceptionHandler().handleException(e);
		}

		try
		{
			return new File(a).getCanonicalFile();
		} catch (IOException e)
		{
			BetterLAN.instance.getExceptionHandler().handleException(e);
			return new File(a);
		}
	}
	
	public File mcSaveDir()
	{
		String a = null;
		try
		{
			a = getMinecraftDir().getCanonicalPath() + "/saves/";
		} catch (IOException e)
		{
			BetterLAN.instance.getExceptionHandler().handleException(e);
		}

		return new File(a);
	}

	public File serverPluginsFolder()
	{
		String a = null;
		try
		{
			a = getMinecraftDir().getCanonicalPath() + "/" + Constants.modFolderName + "/" + Constants.serverFilesFolder + "/" + Constants.serverPluginsFolder;
		} catch (IOException e)
		{
			BetterLAN.instance.getExceptionHandler().handleException(e);
		}

		return new File(a);
	}

	public boolean getServerDownloaded()
	{
		return serverJAR().exists() && configurationFolder().exists() && configsExist() && pluginExists();
	}

	public boolean configsExist()
	{
		try
		{
			return new File(configurationFolder().getCanonicalPath() + "/bukkit.yml").exists() && new File(configurationFolder().getCanonicalPath() + "/help.yml").exists();
		} catch (IOException e)
		{
			BetterLAN.instance.getExceptionHandler().handleException(e);
			return false;
		}
	}

	public File configurationFolder()
	{
		String a = null;
		try
		{
			a = getMinecraftDir().getCanonicalPath() + "/" + Constants.modFolderName + "/" + Constants.serverFilesFolder + "/configurations/";
		} catch (IOException e)
		{
			BetterLAN.instance.getExceptionHandler().handleException(e);
		}

		return new File(a);
	}

	public File errorLog()
	{
		String a = null;
		try
		{
			a = getMinecraftDir().getCanonicalPath() + "/" + Constants.modFolderName + "/error.log";
		} catch (IOException e)
		{
			BetterLAN.instance.getExceptionHandler().handleException(e);
		}

		return new File(a);
	}

	public File configZip()
	{
		String a = null;
		try
		{
			a = getMinecraftDir().getCanonicalPath() + "/" + Constants.modFolderName + "/" + Constants.serverFilesFolder + "/configurations.zip";
		} catch (IOException e)
		{
			BetterLAN.instance.getExceptionHandler().handleException(e);
		}

		return new File(a);
	}

	public File serverJAR()
	{
		String a = null;
		try
		{
			a = getMinecraftDir().getCanonicalPath() + "/" + Constants.modFolderName + "/" + Constants.serverFilesFolder + "/" + Constants.serverJarName;
		} catch (IOException e)
		{
			BetterLAN.instance.getExceptionHandler().handleException(e);
		}

		try
		{
			return new File(a).getCanonicalFile();
		} catch (IOException e)
		{
			BetterLAN.instance.getExceptionHandler().handleException(e);
			return new File(a);
		}
	}

	public boolean getCheatsEnabled()
	{
		return this.cheatsEnabled;
	}

	public boolean isServerRunning()
	{
		return this.serverRunning;
	}

	public boolean hasBeenShared()
	{
		return this.serverShared;
	}

	public boolean getModEnabled()
	{
		return this.modEnabled;
	}

	public ExceptionHandler getExceptionHandler()
	{
		return exceptionHandler;
	}
}