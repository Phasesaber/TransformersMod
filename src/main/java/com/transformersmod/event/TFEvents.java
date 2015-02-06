package com.transformersmod.event;

import com.transformersmod.gui.GuiOverlay;
import com.transformersmod.tick.TickHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public class TFEvents 
{
	public static void registerEvents(Side side)
	{
		MinecraftForge.EVENT_BUS.register(new CommonEventHandler());
		FMLCommonHandler.instance().bus().register(new CommonEventHandler());
		
		if (side.isClient())
		{
			FMLCommonHandler.instance().bus().register(new TickHandler());
			MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
			FMLCommonHandler.instance().bus().register(new ClientEventHandler());
			MinecraftForge.EVENT_BUS.register(new GuiOverlay(Minecraft.getMinecraft()));
		}
	}
}
