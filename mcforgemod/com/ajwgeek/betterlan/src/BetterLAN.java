package com.ajwgeek.betterlan.src;

import com.ajwgeek.betterlan.constant.GlobalVariables;
import com.ajwgeek.betterlan.exception.ExceptionHandler;
import com.ajwgeek.betterlan.server.BetterlanServer;
import com.ajwgeek.betterlan.server.BukkitActionHelper;
import com.ajwgeek.betterlan.util.TickHandler;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = GlobalVariables.modID, name = GlobalVariables.modName, version = GlobalVariables.modVersion, useMetadata = false)

public class BetterLAN extends BetterlanLogic
{
	//TODO seed, Gen Options, Bonus Chest, flat world
	
	@Instance("BetterLAN")
	public static BetterLAN instance;
	
	public BetterLAN()
	{
		setExceptionHandler(new ExceptionHandler());
		setServer(new BetterlanServer());
		setPluginClient(new BukkitActionHelper());
	}
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		loadConfig();
	}

	@EventHandler
	public void load(FMLInitializationEvent event)
	{
		TickRegistry.registerTickHandler(new TickHandler(), Side.CLIENT);
	}
}